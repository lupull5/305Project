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
public class Prize {
    
    final private Integer year;
    final private String category;
    final private Integer share;
    final private String motivation;
    final private Affiliate[] affiliations;

    /**
     *
     * @param year
     * @param category
     * @param share
     * @param motivation
     * @param affiliate
     */
    public Prize(int year, String category, int share, String motivation, Affiliate[] affiliations) {
        this.year = year;
        this.category = category;
        this.share = share;
        this.motivation = motivation;
        this.affiliations = affiliations;        
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the share
     */
    public int getShare() {
        return share;
    }

    /**
     * @return the motivation
     */
    public String getMotivation() {
        return motivation;
    }

    /**
     * @return the affiliates
     */
    public String getAffiliate() {
        ArrayList<String> affiliateList = new ArrayList();
        for (Affiliate affiliate: affiliations){
            affiliateList.add(affiliate.getName());
        }        
        return affiliateList.toString();
    }
    
    
    
    
    
    
    
}
