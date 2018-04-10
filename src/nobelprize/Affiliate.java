/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nobelprize;

/**
 *This is a class representing an affiliate for the laureate
 * 
 * @author Darren
 */
class Affiliate {
    
    final private String name;
    final private String city;
    final private String country;
    
    public Affiliate(String name, String city, String country){
        if(name == null){
            this.name = "<Empty>";
        }else{
            this.name = name;
        }
        if(city == null){
            this.city = "<Empty>";
        }else{
            this.city = city;
        }
        if(country == null){
            this.country = "<Empty>";
        }else{
            this.country = country;
        }                        
    }
    /**
     * the Getter for AffiliateName
     * @return 
     */
    public String getName(){
        return this.name;        
    }
    /**
     * The getter for the city the affiliate is from
     * @return 
     */
    public String getCity(){
        return this.city;
    }
    /**
     * The getter for the affiliates country
     * @return 
     */
    public String getCountry(){
        return this.country;
    }
}
