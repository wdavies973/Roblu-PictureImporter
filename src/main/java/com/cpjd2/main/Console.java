package com.cpjd2.main;


import com.cpjd.http.Request;
import com.cpjd.models.*;

import com.cpjd.requests.CloudCheckoutRequest;
import com.cpjd.requests.CloudTeamRequest;
import com.cpjd2.models.RCheckout;
import com.cpjd2.models.metrics.RGallery;
import com.cpjd2.models.metrics.RMetric;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * This will merge a bunch of images into an active Roblu cloud event
 *
 * @version 1
 * @author Will Davies
 */
public class Console {

    public static void main(String[] args) {

        // Setup input
        Scanner console = new Scanner(System.in);

        /*
         * Things that need to be obtained from the user:
         * -Server IP
         * -Team code
         * -Directory of pictures to import
         * -Compression amount
         * -Pit or Match tab
         * -Name of gallery
         *
         * Things that should probably be handled automatically:
         * -Checking for pictures already existing on the server
         * -Handle errors
         * -Check to make sure the server is online
         */

        System.out.println("Welcome to the Roblu Mass Picture Importer. You can use this utility to automatically import pictures directly into a Roblu event running on Roblu Cloud." +
                " Note: Assuming an average of 60 teams per event, with 1 picture per team, you'll be using about 1.5 MB per picture, resulting in an additional 90 MB that needs to be synced." +
                " It's highly recommended that you enable some picture compression (a question will come up when you start) and make sure the initial sync happens on WiFi to save data.");

        System.out.print("\nEnter the server IP (or press enter if using official server): ");
        String serverIP = console.nextLine();
        if(serverIP == null || serverIP.replaceAll(" ", "").equals("")) serverIP = "ec2-13-59-164-241.us-east-2.compute.amazonaws.com";

        System.out.print("\nEnter your team code: ");
        String teamCode = console.next();

        System.out.print("\nEnter the directory where your pictures are located (make sure picture names match the team numbers they correspond to, aka \"4859.jpg\"): ");
        File picturesDir = new File(console.next());
        if(!picturesDir.exists()) {
            System.out.println("Error: The specified directory "+picturesDir.getAbsolutePath()+" does not exist on the system.");
            return;
        }

        System.out.print("\nEnter the scaling factor to compress images by (100 for no compression, 10 to compress to 10 percent of the original size, etc.): ");
        double compressionAmount = console.nextDouble();
        if(compressionAmount < 0 || compressionAmount > 100) {
            System.out.println("Error: Compression amount must be between 0 and 100.");
            return;
        }

        /*
         * Setup dependencies
         */
        Request r = new Request(serverIP);

        if(r.ping()) {
            System.out.println("Server is online.");
        } else {
            System.out.println("Server is offline. Aborting...");
            return;
        }

        CloudTeamRequest ctr = new CloudTeamRequest(r, teamCode);
        CloudTeam ct;

        if((ct = ctr.getTeam(-1)) != null) {
            System.out.println("Team code "+teamCode+" is valid. Proceeding...");
        } else {
            System.out.println("Team code "+teamCode+" was not found on the server. Aborting...");
            return;
        }

        if(!ct.isActive()) {
            System.out.println("No active event was found on the server. Aborting...");
            return;
        }

        CloudCheckout[] checkouts = new CloudCheckoutRequest(r, teamCode).pullCheckouts(null, true);

        if(checkouts == null || checkouts.length == 0) {
            System.out.println("No active event was found on the server. Aborting...");
            return;
        }

        // Load pictures
        File[] files = picturesDir.listFiles();
        if(files == null || files.length == 0) {
            System.out.println("No images were found in directory "+picturesDir.getAbsolutePath()+". Aborting...");
            return;
        }

        LinkedHashMap<Integer, byte[]> pictures = new LinkedHashMap<>();
        for(File f : files) {
            try {
                System.out.println("Processing file "+f.getAbsolutePath());
                BufferedImage bi = ImageIO.read(f);
                if(compressionAmount != 0) resize(bi, (int)(bi.getWidth() * (compressionAmount / 100)), (int)(bi.getHeight() * (compressionAmount / 100)));

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bi, "jpg", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();

                int key = Integer.parseInt(f.getName().replace(".jpg", "").replace(".jpeg", "").replace(".png", ""));
                System.out.println("Key: "+key);

                pictures.put(key, imageInByte);
            } catch(Exception e) {
                System.out.println("An error occurred while trying to load images. Aborting..."+e.getMessage());
            }
        }

        System.out.println("Loaded: "+pictures.size()+" pictures");

        // Start processing data
        ArrayList<RCheckout> toUpload = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        for(Object key : pictures.keySet()) {
            loop : for(CloudCheckout cc : checkouts) {
                try {
                    RCheckout checkout = mapper.readValue(cc.getContent(), RCheckout.class);

                    if(checkout.getTeam().getTabs().get(0).getTitle().equalsIgnoreCase("PIT") && checkout.getTeam().getNumber() == (int)key) {

                        // Just find the first gallery metric
                        for(RMetric metric : checkout.getTeam().getTabs().get(0).getMetrics()) {
                            //if(metricName != null && !metricName.equalsIgnoreCase("") && !metricName.equals(metric.getTitle())) continue;

                            if(metric instanceof RGallery) {
                                ((RGallery) metric).setPictureIDs(null);
                                ((RGallery) metric).setImages(new ArrayList<>());
                                ((RGallery) metric).getImages().add(pictures.get(checkout.getTeam().getNumber()));
                                System.out.println("Processed team: "+checkout.getTeam().getName());
                                // Add any upload flags
                                toUpload.add(checkout);
                                checkout.setNameTag("Roblu Mass Picture Importer");
                                checkout.setTime(System.currentTimeMillis());
                                checkout.setStatus(2);
                                break loop;
                            }
                        }
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    System.out.println("An error occurred while processing match data. Aborting..."+e.getMessage());
                }
            }

        }

        // Uploading data
        System.out.println("Successfully inserted pictures into "+toUpload.size()+" teams. Uploading to server...");

        try {
            boolean success = new CloudCheckoutRequest(r, teamCode).pushCheckouts(mapper.writeValueAsString(toUpload));
            if(success) {
                System.out.println("Successfully uploaded teams to server.");
            } else {
                System.out.println("Failed to upload teams to server. Aborting...");
                return;
            }
        } catch(Exception e) {
            System.out.println("Failed to upload teams to server. Aborting...");
            return;
        }

        try {
            System.out.println("Sleeping for 60 seconds while all Roblu apps sync. DO NOT CLOSE THIS APP. In 60 seconds, all checkout statuses will be corrected to Available.");

            toUpload.clear();

            Thread.sleep(60000);
            for(CloudCheckout cc : checkouts) {
                RCheckout checkout = mapper.readValue(cc.getContent(), RCheckout.class);
                if(checkout.getTeam().getTabs().get(0).getTitle().equalsIgnoreCase("PIT")) {
                    checkout.setStatus(0);
                    checkout.setNameTag("");
                    checkout.setTime(0);
                    toUpload.add(checkout);
                }
            }

            boolean success = new CloudCheckoutRequest(r, teamCode).pushCheckouts(mapper.writeValueAsString(toUpload));

            if(success) System.out.println("Successfully corrected checkouts status.");
        } catch(Exception e) {
            //
        }


        System.out.println("Thank you for using the Roblu Mass Picture uploader.");
    }

    private static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
