package com.influencer.platform;

import java.util.Arrays;


public class Influencer extends User implements Searchable {
    private String niche;
    private double engagementRate;
    private Integer followers;          


    public Influencer() {
        super("Unknown", "Influencer");
    }
    public Influencer(String name, String niche, double rate, int followers) {
        super(name, "Influencer");
        this.niche = niche;
        this.engagementRate = rate;
        this.followers = followers;
    }

    @Override
    public void showProfile() {
        System.out.printf("Influencer[%d]: %s | Niche: %s | ER: %.2f%% | Followers: %d%n",
            id, name, niche, engagementRate, followers);
    }


    public void showcaseStats() {
        System.out.printf("%s has %d followers and ER %.2f%%%n", name, followers, engagementRate);
    }
    public void showcaseStats(String platform) {
        System.out.printf("%s on %s: %d followers, ER %.2f%%%n",
            name, platform, followers, engagementRate);
    }
    public String getNiche() {
        return niche;
    }

    /** Expose the engagement rate as a percentage (e.g. 4.8) */
    public double getEngagementRate() {
        return engagementRate;
    }

    /** Expose the number of followers */
    public Integer getFollowers() {
        return followers;
    }

    @Override
    public void searchByNiche(String niche) {
        System.out.println("Searching influencers with niche: " + niche);
    }
    @Override
    public void searchByDemographics(String... demographics) {
        System.out.println("Filtering by demographics: " + Arrays.toString(demographics));
    }
}
