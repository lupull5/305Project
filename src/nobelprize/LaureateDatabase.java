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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *This class is the laureate database, it collects all of the laureates from the Nobel Prize database (via JSON),
 * then uses GSON to convert all of the laureates into objects.
 * @author Darren
 */
public class LaureateDatabase {    
    private Collection<Laureate> laureateList = null;
    private ImageHandler imageHandler = new ImageHandler("Images/");
    private String imageFolder = "Images/";
    File imageCollectionList;
    
    /**
     * The constructor for the laureate database, downloads the laureate JSON from the Nobel Prize API, converts
     * it to a list of laureate objects, and gets the images for the objects if needed.
     * @throws MalformedURLException
     * @throws IOException 
     */
    public LaureateDatabase() throws MalformedURLException, IOException{  
        this.buildLaureateList(this.getJSON("http://api.nobelprize.org/v1/laureate.json", ""));        
        this.laureateListCleaner();     
        imageCollectionList = new File("AvailableWikiImages.txt");  
        verifyImages(imageCollectionList);
        imageHandler.ImageCompressor();                
    } 
    
      /**
     * @return the laureateList
     */
    public Collection<Laureate> getLaureateList() {
        return laureateList;
    }
    
    /**
     * This method downloads a JSON file from a given URL, it can take additional info in case the URL has something like a 
     * laureate name at the end. Then returns the JSON as a string
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
     * This method is used to clean the JSON data particularly for the LaureateJSON
     * @param jsonData
     * @return 
     */
    private String JSONCleaner(String jsonData){
        String affiliateTemplate = "\"affiliations\": [{\n\"name\": \"Not Available\",\n\"city\": \"Not Available\",\n\"country\": \"Not Available\"\n}\n]"; 
        String cleanedJson = jsonData.replace("\"affiliations\":[[]]", affiliateTemplate).replace("null", "Not Available").replace("0000-00-00", "Not Available");
        return cleanedJson;                                
    }
    
    /**
     * This converts the laureate JSON into Laureate objects and store them in a list.
     */
    private void buildLaureateList(String laureateJSON){
        Gson gson = new Gson();                
        JsonParser parser = new JsonParser();
        JsonObject laureates = (JsonObject) parser.parse(laureateJSON);                
        laureateList = gson.fromJson(laureates.get("laureates"), new TypeToken<List<Laureate>>(){}.getType());
        for (Laureate laureate : getLaureateList()){ 
            laureate.makeFullNames();            
            imageHandler.checkImageExistence(laureate);
            
        }
    }
    
    /**
     * This method removes some unnamed laureates from the laureate list.
     */
    private void laureateListCleaner(){
        List<Laureate> incorrectLaureates = new ArrayList();
        
        for(Laureate laureate: this.getLaureateList()){            
            if(laureate.getBorn().equals("Not Available") && laureate.getDied().equals("Not Available") && laureate.getGender().equals("male")){
                incorrectLaureates.add(laureate);
            }            
        }    
        getLaureateList().removeAll(incorrectLaureates);
    }
    
    /**
     * This method is used to parse the JSON from wikipedia to collect the image URL
     * @param wikiJSON
     * @return 
     */
    public String parseWikiJSON(String wikiJSON){
        JsonParser parser = new JsonParser();
        String imageURL = null;
        JsonObject peter = (JsonObject) parser.parse(wikiJSON);
        try{
            JsonObject jsonData = peter.getAsJsonObject("query").getAsJsonObject("pages");
            Iterator<Entry<String, JsonElement>> jsonIterable = jsonData.entrySet().iterator();
            while(jsonIterable.hasNext()){
                imageURL = jsonData.getAsJsonObject(jsonIterable.next().getKey()).getAsJsonObject("original").get("source").getAsString();                
            }
            return imageURL;
        } catch (NullPointerException n){            
        }
        return null;
    }
    /**
     * This method is used to verify that we have all of the images a *full* database would have
     * and collects the missing images.
     * @param imageList
     * @throws IOException 
     */
    public void verifyImages(File imageList) throws IOException{
          String imageURL = null;
          try{
            BufferedReader buffRead = new BufferedReader(new FileReader(imageList));
            String fileName;
            while ((fileName = buffRead.readLine()) != null){
                StringBuilder fileLocation = new StringBuilder(this.imageFolder);
                fileLocation.append(fileName);
                try{
                    File imageFile = new File(fileLocation.toString());
                    if (!imageFile.exists()){
                        if((imageURL = parseWikiJSON(getJSON("https://en.wikipedia.org/w/api.php?action=query&prop=pageimages&format=json&piprop=original&titles=", fileName.replace(".jpg", "")))) != null){
                            imageHandler.downloadImage(imageURL, fileName);
                        }
                    }                    
                } catch(IOException e){                    
                }
            }
        } catch(IOException e){            
        }        
    }  
}


