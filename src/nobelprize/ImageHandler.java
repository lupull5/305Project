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
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

/**
 *
 * @author Darren
 */
public class ImageHandler {
    private String imageFolder = null;
    
    
    public ImageHandler(String imageFolder){
        this.imageFolder = imageFolder;              
    }
   
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
            }        
        }
        
        return imageLocation.toString();
    }
    
    public void compressImage(File image){
        if (image.exists()){
            try{
            BufferedImage img = ImageIO.read(image);
            BufferedImage scaledImg = Scalr.resize(img, 100);
            ImageIO.write(scaledImg, "png", image);  
            } catch(IOException e){                
            }
        }
        
    }
    /**
    * 
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
                laureate.setLaureateImage("Images/Nobel_Prize.jpg");
            }                        
        }
    } catch (NullPointerException e){
        laureate.setLaureateImage("Images/Nobel_Prize.png");
    }
    }
}

