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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


/**
 *
 * @author psyco
 */
public class NobelPrize {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, ProtocolException, IOException {

        URL laureateSite = new URL("http://api.nobelprize.org/v1/laureate.json");
        HttpURLConnection con = (HttpURLConnection) laureateSite.openConnection();
        con.setRequestMethod("GET");
        

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));        
        String inputLine;
        StringBuffer content = new StringBuffer();
       
        while ((inputLine = in.readLine()) != null) {
            if(inputLine.contains("[[]]")){
                
            }
            content.append(inputLine.replaceAll("/[/[/]/]", "{[]}"));
        }
        in.close();
        con.disconnect();      
        System.out.println(content);  
        
        Gson gson = new Gson();                
        JsonParser parse = new JsonParser();
        parse.parse(content.toString());
        JsonObject jj = (JsonObject) parse.parse(content.toString());
        
        
        Laureate[] peopleList = gson.fromJson(jj.get("laureates"), Laureate[].class);
        
        
        System.out.println(peopleList[1]);
        
        //Laureate[] obj = gson.fromJson(laureateList.get("laureates").toString(), Laureate[].class);
        
        
                                                  
        }
        
}       
    
