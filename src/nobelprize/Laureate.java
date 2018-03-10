/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nobelprize;

import java.util.ArrayList;

/**
 *
 * @author Darren
 */
public class Laureate {
    
    private final int id;
    private final String firstName;
    private final String surName;
    private final String born;
    private final String died;
    private final String bornCountry;
    private final String bornCountryCode;
    private final String bornCity;
    private final String diedCountry;
    private final String diedCountryCode;
    private final String diedCity;
    private final String gender;
    private final Prize[] prizes;
    
    public Laureate(int id, String firstName, String surName, String born, String died, String bornCountry, String bornCountryCode, String bornCity, String diedCountry, String diedCountryCode, String diedCity, String gender, Prize[] prizes){
        this.id = id;
        this.firstName = firstName;
        this.surName = surName;
        this.born = born;
        this.died = died;
        this.bornCountry = bornCountry;
        this.bornCountryCode = bornCountryCode;
        this.bornCity = bornCity;
        this.diedCountry = diedCountry;
        this.diedCountryCode = diedCountryCode;
        this.diedCity = diedCity;
        this.gender = gender;
        this.prizes = prizes;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the surName
     */
    public String getSurName() {
        return surName;
    }

    /**
     * @return the born
     */
    public String getBorn() {
        return born;
    }

    /**
     * @return the died
     */
    public String getDied() {
        return died;
    }

    /**
     * @return the bornCountry
     */
    public String getBornCountry() {
        return bornCountry;
    }

    /**
     * @return the bornCountryCode
     */
    public String getBornCountryCode() {
        return bornCountryCode;
    }

    /**
     * @return the bornCity
     */
    public String getBornCity() {
        return bornCity;
    }

    /**
     * @return the diedCountry
     */
    public String getDiedCountry() {
        return diedCountry;
    }

    /**
     * @return the diedCountryCode
     */
    public String getDiedCountryCode() {
        return diedCountryCode;
    }

    /**
     * @return the diedCity
     */
    public String getDiedCity() {
        return diedCity;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return the prizes
     */
    public Prize[] getPrizes() {
        return prizes;
    }
    
    
}
