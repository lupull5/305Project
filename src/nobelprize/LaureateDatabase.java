/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nobelprize;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Darren
 */
public class LaureateDatabase {
    StringBuffer laureateJSON = new StringBuffer();
    private Collection<Laureate> laureateList = null;
    private String affiliateTemplate = "\"affiliations\": [{\n\"name\": \"Not Available\",\n\"city\": \"Not Available\",\n\"country\": \"Not Available\"\n}\n]"; 

    /**
     * 
     * @throws MalformedURLException
     * @throws IOException 
     */
    public LaureateDatabase() throws MalformedURLException, IOException{
        this.getLaureteJSON("http://api.nobelprize.org/v1/laureate.json");
        this.buildLaureateList();
        this.laureateListCleaner();
    } 
    /**
     * 
     * @param laureateWebsite
     * @throws MalformedURLException
     * @throws IOException 
     */
    private void getLaureteJSON(String laureateWebsite) throws MalformedURLException, IOException{
        URL laureateSite = new URL(laureateWebsite);
        HttpURLConnection connection = (HttpURLConnection) laureateSite.openConnection();
        String dataLine;
        
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException ex) {
            Logger.getLogger(LaureateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }        
        BufferedReader JSONfromWeb = new BufferedReader(new InputStreamReader(connection.getInputStream()));                        
        laureateJSON.append(JSONCleaner(JSONfromWeb.readLine()));

        JSONfromWeb.close();
        connection.disconnect();
    }
    /**
     * 
     */
    private void buildLaureateList(){
        Gson gson = new Gson();                
        JsonParser parser = new JsonParser();
        
        parser.parse(laureateJSON.toString());
        JsonObject laureates = (JsonObject) parser.parse(laureateJSON.toString());                
        laureateList = gson.fromJson(laureates.get("laureates"), new TypeToken<List<Laureate>>(){}.getType());         
    }
    
    private void laureateListCleaner(){
        List<Laureate> incorrectLaureates = new ArrayList();
        
        for(Laureate laureate: this.laureateList){
            if(laureate.getBorn().equals("0000-00-00") && laureate.getDied().equals("0000-00-00") && laureate.getGender().equals("male")){
                incorrectLaureates.add(laureate);
            }            
        }    
        laureateList.removeAll(incorrectLaureates);
    }
    
    private String JSONCleaner(String jsonData){
        String cleanedJson = jsonData.replace("\"affiliations\":[[]]", affiliateTemplate);
        cleanedJson = cleanedJson.replace("null", "Not Available");
        return cleanedJson;
        
        
        
        
    }
   /**
    * 
    * @param searchParameters
    * @return 
    */
    public ArrayList<Laureate> searchForLaureate(HashMap<String, String> searchParameters){
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
    }
}
