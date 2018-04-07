package com.cpjd2.models.metrics;

import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.ArrayList;

@JsonTypeName("RGallery")
public class RGallery extends RMetric {

    /**
     * Changing this versionUID will render this class incompatible with older versions.
     */
    public static final long serialVersionUID = 1L;
    /**
     * This array stores the local ID of the pictures in this gallery, upon a
     * a sync, local pictures should be loaded into the byte[] images array below. This should IMMEDIATELY be declared
     * merged into local files, and then set to null.
     */
    private ArrayList<Integer> pictureIDs;

    /**
     * An ArrayList is used here because it's a bit easier to manage.
     * Each byte[] represents one image.
     */
    private ArrayList<byte[]> images;

    /**
     * The empty constructor is required for de-serialization
     */
    @SuppressWarnings("unused")
    public RGallery() {}

    /**
     * Instantiates a gallery element
     * @param ID the unique identifier for this object
     * @param title object title
     */
    private RGallery(int ID, String title) {
        super(ID, title);
    }

    /**
     * Adds a new image to the image array
     * @param image a byte[] representing the image, format must match that UI requirements
     */
    public void addImage(byte[] image) {
        if(images == null) images = new ArrayList<>();

        images.add(image);
    }

    /**
     * Removes an image from the image array
     * @param position the index position of the image to be removed
     */
    public void removeImage(int position) {
        if(images != null) images.remove(position);
    }

    /**
     * Returns the image array, ensuring that it's not null
     * @return array containing all images
     */
    public ArrayList<byte[]> getImages() {
        if(images == null) images = new ArrayList<>();
        return images;
    }

    @Override
    public String getFormDescriptor() {
        return "Type: Gallery";
    }

    @Override
    public RMetric clone() {
        RGallery gallery = new RGallery(ID, title);
        return gallery;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ArrayList<Integer> getPictureIDs() {
        return pictureIDs;
    }

    public void setPictureIDs(ArrayList<Integer> pictureIDs) {
        this.pictureIDs = pictureIDs;
    }

    public void setImages(ArrayList<byte[]> images) {
        this.images = images;
    }
}
