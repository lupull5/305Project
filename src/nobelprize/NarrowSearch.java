/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package nobelprize;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
/**
 *
 * @author lukelupul
 */


/*
There are for the most part two kinds of methods in this class: methods that build
dictionaries (hashMaps) along fairly general lines, and methods which narrow the 
focus of the data these functions return.

In practice, the dictionary-builders usually construct hashMaps with strings as
keys and ArrayLists of lauriets grouped under the key-term as values. 
The methods that narrow the searches go through these arraylists and prune out
entries deemed spurious inview of user input. 
*/

public class NarrowSearch
{
    Collection<Laureate> laureates;
    //for all hashmaps below, the values are arraylists of laureates
    //keyed to relavent categories they can by broken up into.
    private HashMap nameSearch;// keys: Laureate first and surnames. 
    private HashMap CountrySearch;//keys: countrys laureates were born or died in
    private HashMap DateSearch;//keys: Strings of decades laureates were born/died in
    private HashMap byCategory;//keys: the category of prize won
    public NarrowSearch(Collection<Laureate> winners)
    {
        this.laureates = winners; 
        nameSearch = names();
        CountrySearch = BirthDeathCountry();
        DateSearch = BirthDeathDates();
        byCategory = WonPrize();


      
    }        
    //boolean method returns true if given laureate object contains the given field.
    public boolean containsField(Laureate winner, String field)
    {
   return winner.getFirstName().equals(field)       || winner.getSurName().equals(field)         || 
          winner.getBorn().equals(field)            || winner.getDied().equals(field)            || 
          winner.getBornCountry().equals(field)     || winner.getBornCountryCode().equals(field) || 
          winner.getDiedCountryCode().equals(field) || winner.getDiedCity().equals(field)        ||
          winner.getGender().equals(field)          || winner.getPrizes().equals(field);
                       
    }
    //this method receives an array which has keyed strings to laureates, and 
    //returns the array of all such laureates. If the user has specified, for instance, 
    //a decade but no other information, the dates function will include
    //every laureate for which that decade is signifigant.
    public ArrayList<Laureate> retrieveFromHash(HashMap<String,Laureate> search)
    {
        Set<String> keyRing = search.keySet();
        ArrayList<Laureate> winners = new ArrayList();
        for (String  key : keyRing)
        {//some other display method may be more appropriate later. 
            winners.add(search.get(key));
        } 
        return winners;
    }        
    //this function returns a hashmap where the names of Laureates are keys, and
    //Arraylists of Laureates are values. If we end up using a text search and
    //the user knows who they;re aftr by their first or last name, this could
    //end up being useful. 
    public HashMap names()
    {//
        HashMap Names = new HashMap<String,ArrayList<Laureate>>();
        String first;
        String last;
        ArrayList<Laureate> value;
        for(Laureate winner : this.laureates) 
        {//case where str is is already in the dictionary.
            first = winner.getFirstName();
            last  = winner.getSurName();
            if (Names.containsKey(first) )
            {   //case where winner's first name is already in the dictionary.
                value = (ArrayList<Laureate>) Names.get(first);
                value.add(winner);
            }
            else
            {//case where name is new.
                value = new ArrayList<Laureate>();
                value.add(winner);
                Names.put(first,value);
            }
         if (Names.containsKey(last) )
            {   //case where winner's last name is already in the dictionary.
                value = (ArrayList<Laureate>) Names.get(last);
                value.add(winner);
            }
            else
            {//case where name is new.
                value = new ArrayList<Laureate>();
                value.add(winner);
                Names.put(last,value);
            }      
        
        }      
        return Names;
    
    
    
    }
    //this function returns a hashmap containing Strings of All contries some 
    //nobel laureate was either born or died in as keys, and ArrayLists containing 
    //all nobel laureates which were either born or died in that country as values. 
    
    //there are also methods below which, receiving such an array, can return
    //only those who were born or only those who died. 
    public HashMap BirthDeathCountry()
    {//
        HashMap Locations = new HashMap<String,ArrayList<Laureate>>();
        String born;
        String died;
        ArrayList<Laureate> value;
        for(Laureate winner : this.laureates) 
        {//case where str is is already in the dictionary.
            born = winner.getBornCountry();
            died  = winner.getDiedCountry();
            if (Locations.containsKey(born) )
            {   //case where Location' is already in the dictionary.
                value = (ArrayList<Laureate>) Locations.get(born);
                value.add(winner);
            }
            else
            {//case where Location is new
                value = new ArrayList<Laureate>();
                value.add(winner);
                Locations.put(born,value);
            }
         if (Locations.containsKey(died) )
            {   //case where winner's last name is already in the dictionary.
                value = (ArrayList<Laureate>) Locations.get(died);
                value.add(winner);
            }
            else
            {//case where name is new.
                value = new ArrayList<Laureate>();
                value.add(winner);
                Locations.put(died,value);
            }
         
        
        }      
        return Locations;
    
    
    
    }//terminates locations
//receives a lauriete hashmap and a string, country. 
//returns the Laureate arraylist that that country has keyed to it, supposing
//any laureates are associated with that country.     
public ArrayList<Laureate> getCountry(HashMap<String,ArrayList<Laureate>> LocDict,String Country)
{
    return LocDict.get(Country);
} 
//receives an arraylist of Laureates and a String, which is a country as written 
//in the nobel laureate profiles. Returns the list containing only those
//Laureates in the given list, if any. Would likely be used in a situation where
//the user wants to qualify their search.
public ArrayList<Laureate> ClarifyCountryBorn(ArrayList<Laureate> BornAndDied, String Country) 
{
    ArrayList<Laureate> BornCitizen = new ArrayList<Laureate>();
     for(Laureate winner: BornAndDied)
     {
         if (winner.getBornCountry() == Country)
             BornCitizen.add(winner);
     }
     return BornCitizen;
}       
//bararo-wold cousin for clarifyCountryBorn, listed just above.
public ArrayList<Laureate> ClarifyCountryDied(ArrayList<Laureate> BornAndDied, String Country) 
{
    ArrayList<Laureate> DiedIn = new ArrayList<Laureate>();
     for(Laureate winner: BornAndDied)
     {
         if (winner.getDiedCountry() == Country)
             DiedIn.add(winner);
     }
     return DiedIn;
}       
//isomorphic to the BornDiedcountry method above, except with respect to cities.
//I havn't yet included disambuguating methods for this, query by city may be
//so specific that it would be superflorous and unnecessary. This'll likely come
//out in testing.

//calledat initialization, keys cities as strings to Arraylists of Laureates as values.
public HashMap City()
    {//
        HashMap Locations = new HashMap<String,ArrayList<Laureate>>();
        String born;
        String died;
        ArrayList<Laureate> value;
        for(Laureate winner : this.laureates) 
        {//case where str is is already in the dictionary.
            born = winner.getBornCity();
            died  = winner.getDiedCity();
            if (Locations.containsKey(born) )
            {   //case where Location' is already in the dictionary.
                value = (ArrayList<Laureate>) Locations.get(born);
                value.add(winner);
            }
            else
            {//case where Location is new
                value = new ArrayList<Laureate>();
                value.add(winner);
                Locations.put(born,value);
            }
         if (Locations.containsKey(died) )
            {   //case where winner's last name is already in the dictionary.
                value = (ArrayList<Laureate>) Locations.get(died);
                value.add(winner);
            }
            else
            {//case where name is new.
                value = new ArrayList<Laureate>();
                value.add(winner);
                Locations.put(died,value);
            }      
        
        }      
        return Locations;
    
    
    
    }//terminates city
  
//BirthDeathDates is called at initialization, it constructs as one of the class
//fields a dictionary which maps as Strings the decades, eg. "1890", "1900", "1910",
//etc. to all Laurietes who were either born or died in that decade. There are methods
//below which disambiguate which of the two. this allows the user to be very general
// at first, and more specific as they put together what they're looking for.    
  public HashMap BirthDeathDates()
    {//
        HashMap<String,ArrayList<Laureate>> Dates = new HashMap<String,ArrayList<Laureate>>();
        String born;
        String died;
        ArrayList<Laureate> value;
        for(Laureate winner : this.laureates) 
        {//case where str is is already in the dictionary.
            born = chopDateBorn(winner);
            died  = chopDateDied(winner);
            
            if (Dates.containsKey(born) )
            {   //case where Location' is already in the dictionary.
                value = (ArrayList<Laureate>) Dates.get(born);
                value.add(winner);
            }
            else
            {//case where Location is new
                value = new ArrayList<Laureate>();
                value.add(winner);
                Dates.put(born,value);
            }
         if (Dates.containsKey(died) )
            {   //case where winner's last name is already in the dictionary.
                value =  Dates.get(died);
                value.add(winner);
            }
            else
            {//case where name is new.
                value = new ArrayList<Laureate>();
                value.add(winner);
                Dates.put(died,value);
            }      
        
        }      
        return Dates;
    
    
    
    }//terminates dates  
 //accesor method, presently kind of redundant.
 public ArrayList<Laureate> getBornDiedDecade( String Decade) 
 {//cast to arrayList of Laureates.
     return (ArrayList<Laureate>) getDateSearch().get(Decade);
 }        
  //the intention behind this function is that it takes the hashmap
  //returned by the dates function and a decade, specified in the form of a string.
  //it returns an arraylist containing all Laureates that were born in that Year.
  //I mean also to have a twin function for those who died. 
  public ArrayList<Laureate> ClarifyYearBorn(ArrayList<Laureate> BornAndDied,String Decade)
  {
     ArrayList<Laureate> BornSet = new ArrayList<Laureate>();
     for(Laureate winner: BornAndDied)
     {
         if ( BornInDecade(winner, Decade))
             BornSet.add(winner);
     }
     return BornSet;
  }
  //just like clarify year born, only sadder.
  public ArrayList<Laureate> ClarifyYearDied(ArrayList<Laureate> BornAndDied,String Decade)
  {
     ArrayList<Laureate> DiedSet = new ArrayList<Laureate>();
     for(Laureate winner: BornAndDied)
     {
         if (DiedInDecade(winner, Decade))
             DiedSet.add(winner);
     }
     return DiedSet;
     
  }
  //chopDateBorn removes the one's place of The Laureate's birth date stored as a string.
    //returns this as a string. 
  //I.E, gives the decade of birth for the Laureate as a String.
    public String chopDateBorn(Laureate winner) {
        String year = winner.getBorn().substring(0,4);
         if(year.equals("Not "))
        {
            return "Not Available";
        }    
        int date = Integer.parseInt(year);
        //discard the one's place. 
        date = date /10;
        date *= 10;
        String retVal = Integer.toString(date);
        return retVal;
    }
    //similar to chopDateBorn, except with the laureate's death date.
     public String chopDateDied(Laureate winner) {
        String year = winner.getDied().substring(0,4);
          if(year.equals("Not "))
        {
            return "Not Available";
        }    
         int date = Integer.parseInt(year);
        //discard the one's place. 
        date = date /10;
        date *= 10;
        String retVal = Integer.toString(date);
        return retVal;
    }
    //BornInDecade is a boolean method that determines if a Lauriate was born
     //in a particular decade given to the function as a String.
     //Eg:
     
     //  BornInDecade(Einstein,"1970")
     //        -->returns false.
     //has a twin function Died in Decade.
    public boolean BornInDecade(Laureate winner, String decade)
    {
        String chop = chopDateBorn(winner);
        return chop.equals(decade);
    }
    public boolean DiedInDecade(Laureate winner, String decade)
    {
        String chop = chopDateDied(winner);
        return chop.equals(decade);
    }
  //Here I will include some methods that reduce a lauriate array such that it contains 
  //only male, female, on ungendered laureates.
  
    public ArrayList<Laureate> clarifyMale(ArrayList<Laureate> coEdLaureates)
  {
      ArrayList<Laureate> males = new ArrayList();
      for(Laureate winner : coEdLaureates)
      {
          if (winner.getGender().equals("male") )
              males.add(winner);
      }
      return males;
      
  }
  public ArrayList<Laureate> clarifyFemale(ArrayList<Laureate> coEdLaureates)
  {
      ArrayList<Laureate> females = new ArrayList();
      for(Laureate winner : coEdLaureates)
      {
          if (winner.getGender().equals("female") )
              females.add(winner);
      }
      return females;
      
  }  
  public ArrayList<Laureate> clarifyUngendered(ArrayList<Laureate> coEdLaureates)
  {
      ArrayList<Laureate> unGen = new ArrayList();
      for(Laureate winner : coEdLaureates)
      {
          if (! winner.getGender().equals("female") && ! winner.getGender().equals("male"))
              unGen.add(winner);
      }
      return unGen;
      
  }   
//I need methods which relate to the prize objects themselves, and which can access 
//prizes based on criteria the user is like to know.  
  
    //this receives an arraylist of Laureates, and a string, year,which is a decade.
  //it returns an arraylist containing all Laureates who won in that decade. 
  
  public ArrayList<Laureate> prizeByYear(ArrayList<Laureate> winners, int year)
   {
       
       year /= 10;
       year *= 10;
       int truncYear;
       
       Prize[] prizes;
       
       ArrayList<Laureate> wonThatDecade = new ArrayList<>();
       for (Laureate winner: winners)
       {
           prizes = winner.getPrizes();
           for (Prize award : prizes)
           {
               truncYear = award.getYear();
               truncYear /= 10;
               truncYear *= 10;
               if (truncYear == year)
                   wonThatDecade.add(winner);
           }
           
       }
       return wonThatDecade;
   }        
  
    
//this function initializes a hashmap containing Strings of the prize categories as 
// keys, and arrays of the Laureates who won those prizes as values.   

public HashMap WonPrize()
    {//
        HashMap<String,ArrayList<Laureate>> ByCategory = new HashMap<>();
        String SpecificPrize;
       
        ArrayList<Laureate> value;
        for(Laureate winner : this.laureates) 
        {
            for (Prize award : winner.getPrizes() )
            {//case where award is already in the map
                SpecificPrize = award.getCategory(); 
                if (ByCategory.containsKey(SpecificPrize))
                {
                    value = ByCategory.get(SpecificPrize);
                    value.add(winner);
                }
                else
                {//case where award isnot yet a recognized key
                    ArrayList<Laureate> prizeWinners = new ArrayList<>();
                    prizeWinners.add(winner);
                    ByCategory.put(SpecificPrize, prizeWinners);
                }
            }//nested for
        
        }//for
        return ByCategory;
    }//terminates Won Prize   

/*
as on now inclomplete pruner for arraylists of laureates that reduces to only
those laureates who won categories.
*/
public ArrayList<Laureate> clarifyCategory(ArrayList<Laureate> possibilities, String Category )
    {
        ArrayList<Laureate> reducedLaList = new ArrayList<Laureate>(); 
        for (Laureate winner : possibilities)
        {
            
            if (HasPrize(winner,Category))
            {//case where laureate has a prize of the specified category.
                reducedLaList.add(winner);
            }
            
        }
        return reducedLaList;
    }


/*
this is a boolean method, returns true if the given Laureate winner
has a Prize award of the specified category, false otherwise.
*/

    public boolean HasPrize(Laureate winner, String category)
    {
        for (Prize thePrizeTheLaureateWon: winner.getPrizes())
        {
            if (thePrizeTheLaureateWon.getCategory().equals(category)  )
                return true;
        }
        return false;
    }        

    public ArrayList<Laureate> getWinners() {
        return (ArrayList<Laureate>) this.laureates;
    }
    /**
     * @return the nameSearch
     */
    public HashMap getNameSearch() {
        return nameSearch;
    }

    /**
     * @return the CountrySearch
     */
    public HashMap getCountrySearch() {
        return CountrySearch;
    }

    /**
     * @return the DateSearch
     */
    public HashMap getDateSearch() {
        return DateSearch;
    }

    /**
     * @return the byCategory
     */
    public HashMap getByCategory() {
        return byCategory;
    }
}

