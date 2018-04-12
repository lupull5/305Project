/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nobelprize;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

/**
 * The image handler is a class that deals with collecting and modifying the laureate images.
 * We are getting the images from wikipedia as the laureate database said the images were not
 * allowed to be shared without permission.
 * @author Darren
 */
public class ImageHandler {
    private String imageFolder = null;
    
    /**
     * This is the constructor for the image handler. It takes a folder directory, and uses that directory to store data.
     * @param imageFolder 
     */
    public ImageHandler(String imageFolder){
        this.imageFolder = imageFolder;         
    }
  
   /**
    * This method downloads an image from a given URL (it builds the URL with a given laureate name).
    * @param imageURL - The URL for the image to be downloaded
    * @param laureateName - The name of the laureate that we are looking for
    * @return 
    */
    public String downloadImage(String imageURL, String laureateName){
        StringBuilder imageLocation = new StringBuilder(this.imageFolder);
        imageLocation.append(laureateName);
        File Image = new File(imageLocation.toString());        
        if(!Image.exists() && !Image.isDirectory()){           
            try{              
                URL url = new URL(imageURL); 
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream out = new BufferedOutputStream(new FileOutputStream(imageLocation.toString()));
                for (int i; (i=input.read()) != -1;){
                    out.write(i);
                }                
                input.close();
                out.close();
            } catch (IOException e){ 
                System.out.println("Error");
            }        
        }
        
        return imageLocation.toString();
    }
    /**
     * This compresses an image down to a smaller size to save space
     * @param image 
     */
    public void compressImage(File image){
        if (image.exists()){
            try{
            BufferedImage img = ImageIO.read(image);
            BufferedImage scaledImg = Scalr.resize(img, 150, 120);
            ImageIO.write(scaledImg, "png", image);  
            } catch(IOException e){                
            }
        }
        
    }
    /**
    * This compresses all of the images in the imagehandlers image folder.
    * @throws IOException 
    */
    public void ImageCompressor() throws IOException{

      try{
          File folder = new File(this.imageFolder);
          for (File image: folder.listFiles()){
              compressImage(image);
          }                
      } catch(IllegalArgumentException r){               
      }                                                                  
    }
    
    /**
     * This method makes a text document of all the images in the imagehandlers image folder.
     * This reference text is used in case someone goes in and deletes images for fun
     * we will be able to use the list to see whats missing.
     * @throws IOException 
     */
    public void collectImagesAvailable()throws IOException{
        File availableImageList = new File("AvailableWikiImages.txt");
        availableImageList.createNewFile();
        try{           
            PrintWriter fileOut = new PrintWriter(availableImageList);
            File folder = new File(this.imageFolder);            
            for (File image: folder.listFiles()){
                fileOut.println(image.getName());
            }
            fileOut.close();
            
        } catch (IOException e) {            
        }
    
    }
    
    /**
     * This method checks if a given laureate has an image in our folder, and if so
     * updates the laureates image location to the new image location. 
     * @param laureate 
     */
    public void checkImageExistence(Laureate laureate){
    try{
        for(String name : laureate.getFirstNameList()){
            if(name != null){                
               StringBuilder imageLocation = new StringBuilder("Images/");
                imageLocation.append(name);
                imageLocation.append(".jpg"); 
                File checkForImage = new File(imageLocation.toString());
                if(checkForImage.exists()){
                    laureate.setLaureateImage(imageLocation.toString());
                    break;
                }         
                laureate.setLaureateImage("Images/Nobel_Prize.png");
            }                        
        }
    } catch (NullPointerException e){
        laureate.setLaureateImage("Images/Nobel_Prize.png");
    }
    }
}

