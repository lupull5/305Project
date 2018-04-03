/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nobelprize;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

/**
 *
 * @author Darren
 */
public class LaureateDatabase {    
    private Collection<Laureate> laureateList = null;
    
    
    /**
     * 
     * @throws MalformedURLException
     * @throws IOException 
     */
    public LaureateDatabase() throws MalformedURLException, IOException{  
        this.buildLaureateList(this.getJSON("http://api.nobelprize.org/v1/laureate.json", ""));        
        this.laureateListCleaner();
        //this.collectLaureateImages();
        //this.ImageCompressor();   
        System.out.print("c");
    } 
    /**
     * 
     * @param website
     * @throws MalformedURLException
     * @throws IOException 
     */
    private String getJSON(String website, String additionalURLInfo) throws MalformedURLException, IOException{
        StringBuilder JSONData = new StringBuilder();
        StringBuilder websiteURL = new StringBuilder(website);
        String currentLine  = null;
        websiteURL.append(additionalURLInfo);
        try{
            URL laureateSite = new URL(websiteURL.toString());
            HttpURLConnection connection = (HttpURLConnection) laureateSite.openConnection();

            try {
                connection.setRequestMethod("GET");
            } catch (ProtocolException ex) {
                Logger.getLogger(LaureateDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }        
            BufferedReader JSONfromWeb = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((currentLine = JSONfromWeb.readLine()) != null){
                JSONData.append(JSONCleaner(currentLine));
            }

            JSONfromWeb.close();
            connection.disconnect();
            return JSONData.toString();
        }catch(IOException e){           
        }
        return null;
    }
    /**
     * 
     */
    private void buildLaureateList(String laureateJSON){
        Gson gson = new Gson();                
        JsonParser parser = new JsonParser();
        JsonObject laureates = (JsonObject) parser.parse(laureateJSON);                
        laureateList = gson.fromJson(laureates.get("laureates"), new TypeToken<List<Laureate>>(){}.getType());
        for (Laureate laureate : getLaureateList()){ 
            laureate.makeFullNames();            
            checkImageExistence(laureate);
            
        }
    }
    
    private void checkImageExistence(Laureate laureate){
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
                    laureate.setLaureateImage("Images/no_face.png");
                }                        
            }
        } catch (NullPointerException e){
            laureate.setLaureateImage("Images/no_face.png");
        }
        
    }
    /**
     * 
     */
    private void laureateListCleaner(){
        List<Laureate> incorrectLaureates = new ArrayList();
        
        for(Laureate laureate: this.getLaureateList()){            
            if(laureate.getBorn().equals("0000-00-00") && laureate.getDied().equals("0000-00-00") && laureate.getGender().equals("male")){
                incorrectLaureates.add(laureate);
            }            
        }    
        getLaureateList().removeAll(incorrectLaureates);
    }
    /**
     * 
     * @throws IOException 
     */
    private void collectLaureateImages() throws IOException{
        JsonParser parser = new JsonParser();
        String laureateImageJSON;
        String newData = null;
        for (Laureate laureate : getLaureateList()){
            for (String nameType : laureate.getFirstNameList()){
                if (laureate.getLaureateImage().equals("Images/no_face.png") && !laureate.getBorn().equals("0000-00-00")){
                    if ((laureateImageJSON = this.getJSON("https://en.wikipedia.org/w/api.php?action=query&prop=pageimages&format=json&piprop=original&titles=", nameType)) != null){                    
                        JsonObject peter = (JsonObject) parser.parse(laureateImageJSON);
                        try{
                            JsonObject jsonData = peter.getAsJsonObject("query").getAsJsonObject("pages");
                            Iterator<Entry<String, JsonElement>> jsonIterable = jsonData.entrySet().iterator();
                            while(jsonIterable.hasNext()){
                                newData = jsonData.getAsJsonObject(jsonIterable.next().getKey()).getAsJsonObject("original").get("source").getAsString();
                            }            
                        laureate.setLaureateImage(downloadImage(newData, nameType));                                
                        } catch (NullPointerException e){                             
                            System.out.println(laureate.getFirstName());
                        }
                    }
                }
            }
        }           
    }
    /**
     * 
     * @param imageURL
     * @param laureateName
     * @return 
     */
    private String downloadImage(String imageURL, String laureateName){
        StringBuilder imageLocation = new StringBuilder("Images/");
        imageLocation.append(laureateName);
        imageLocation.append(".jpg");
        File checkForImage = new File(imageLocation.toString());
        if(!checkForImage.exists() && !checkForImage.isDirectory()){
            try{
                URL url = new URL(imageURL); 
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream out = new BufferedOutputStream(new FileOutputStream(imageLocation.toString()));
                for (int i; (i=input.read()) != -1;){
                    out.write(i);
                }
                
                input.close();
                out.close();
                //Thumbnails.of(imageLocation.toString()).size(100, 100).toFile(imageLocation.toString());
            } catch (IOException e){            
            }        
        }
        return imageLocation.toString();
    }
    /**
     * 
     * @param jsonData
     * @return 
     */
    private String JSONCleaner(String jsonData){
        String affiliateTemplate = "\"affiliations\": [{\n\"name\": \"Not Available\",\n\"city\": \"Not Available\",\n\"country\": \"Not Available\"\n}\n]"; 
        String cleanedJson = jsonData.replace("\"affiliations\":[[]]", affiliateTemplate).replace("null", "Not Available");
        return cleanedJson;                                
    }
   /**
    * 
    * @param searchParameters
    * @return 
    */
  /*  public ArrayList<Laureate> searchForLaureate(HashMap<String, String> searchParameters){
        ArrayList<Laureate> searchedLaureates = new ArrayList();
        Boolean search = true;
        for(Laureate laureate : laureateList){
            search = true;
            if(laureate.getId() == 897){
                System.out.print("c");
            }
            for(Entry<String, String> entry : searchParameters.entrySet()){
               
                if(!laureate.getData().containsValue(entry.getValue())){
                    search = false;                    
                }                                
            }
            if(search){
                searchedLaureates.add(laureate);
            }
        }
        return searchedLaureates;
    }*/
    /**
     * 
     * @throws IOException 
     */
    public void ImageCompressor() throws IOException{
        ArrayList<String> images = new ArrayList();
        ArrayList<File> files = new ArrayList();
        
        for(Laureate laur : getLaureateList()){
            try{
                File currentImage = new File(laur.getLaureateImage());
                BufferedImage img = ImageIO.read(currentImage);
                BufferedImage scaledImg = Scalr.resize(img, 100);
                ImageIO.write(scaledImg, "png", currentImage);
            }catch(IllegalArgumentException r){               
            }
        }                              
    }

    /**
     * @return the laureateList
     */
    public Collection<Laureate> getLaureateList() {
        return laureateList;
    }
}


