# Roblu-PictureImporter
The Roblu picture importer is a utility for teams that want to import a lot of pictures at once into a gallery metric on the Roblu apps.
This will consume a lot more data when syncing.
# Tutorial
1) Download [Roblu-PictureImporter.jar](https://github.com/wdavies973/Roblu-PictureImporter/releases/download/2/Roblu-PictureImporter.jar) from the releases section of this repository.
2) In the directory of the jar, run ```java -jar Roblu-PictureImporter.jar``` in command prompt.
3) You will need to specify several options, make sure you enter commands exactly, because the utility expects very specific input.
4) Enter your server IP, or press enter to set the target server.
5) Enter your team code, this should match the code you have entered in Roblu Master and Roblu Scouter apps.
6) Enter the directory on your computer containing all the pictures you'd like to upload like so: ```C:\\roblupics```, within this folder, each 
picture should be either ```.png``` or ```.jpg``` and it's name should be the number of the team it corresponds to.
7) Enter compression, it's recommended to use the lowest number you can with acceptable quality. Most teams using the picture importer likely
will use a high quality camera to take pictures, which take A LOT of space. Compression is highly recommended.
8) The utility now has everything it needs. Several things will happen:  
* Roblu will insert pictures into the FIRST gallery metric within each pit tab.
* Roblu will upload the pictures with status "COMPLETED", the reason for this is that the Roblu Master apps only download completed checkouts,
and this was easier rather than redesigning the system. The utility will sleep for 60 seconds, this should give enough time for several
apps to automatically download and sync. To avoid problems, do this at the beginning of an event before scouters have pulled the event. Make sure
all target devices have an active internet connection.
* The app will then auto correct all the statuses to available.

If you run into problems, contact me: wdavies973@gmail.com.
