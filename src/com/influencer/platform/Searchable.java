package com.influencer.platform;


public interface Searchable {
    void searchByNiche(String niche);
    void searchByDemographics(String... demographics); 
}
