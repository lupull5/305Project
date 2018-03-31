/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nobelprize;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author psyco
 */
public class NobelPrize {

    /**
     * @param args the command line arguments
     * @throws java.net.MalformedURLException
     * @throws java.net.ProtocolException
     */
    public static void main(String[] args) throws MalformedURLException, ProtocolException, IOException {
        LaureateDatabase MrDataBase = new LaureateDatabase();
        HashMap<String, String> template = new HashMap();
        template.put("bornCity", "Paris");
        ArrayList<Laureate> jimbo = new ArrayList();
        jimbo = MrDataBase.searchForLaureate(template);
        System.out.println("Howdy!");
    }       
}
