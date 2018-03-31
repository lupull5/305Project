/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nobelprize;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Darren
 */
public class LaureateDatabase {
    StringBuffer laureateJSON = new StringBuffer();
    private Laureate[] laureateList;
    private String affiliateTemplate = "\"affiliations\": [{\n\"name\": \"Not Available\",\n\"city\": \"Not Available\",\n\"country\": \"Not Available\"\n}\n]"; 

    
    public LaureateDatabase() throws MalformedURLException, IOException{
        this.getLaureteJSON("http://api.nobelprize.org/v1/laureate.json");
        this.buildLaureateList();
    } 
    
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
        while ((dataLine = JSONfromWeb.readLine()) != null) {           
            laureateJSON.append(dataLine.replace("\"affiliations\":[[]]", affiliateTemplate));
        }
        JSONfromWeb.close();
        connection.disconnect();
    }
    
    private void buildLaureateList(){
        Gson gson = new Gson();                
        JsonParser parser = new JsonParser();
        
        parser.parse(laureateJSON.toString());
        JsonObject laureates = (JsonObject) parser.parse(laureateJSON.toString());                
        laureateList = gson.fromJson(laureates.get("laureates"), Laureate[].class);        
    }
   
}
