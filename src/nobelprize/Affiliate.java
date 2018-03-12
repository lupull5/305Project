/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nobelprize;

/**
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
    
    public String getName(){
        return this.name;        
    }
    
    public String getCity(){
        return this.city;
    }
    
    public String getCountry(){
        return this.country;
    }
}
