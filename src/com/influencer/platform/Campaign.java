package com.influencer.platform;

import java.util.ArrayList;
import java.util.List;


public class Campaign {
    private String title;
    private List<Influencer> influencers = new ArrayList<>();
    private Metrics metrics;       


    public Campaign(String title) {
        this.title = title;
        this.metrics = new Metrics();
    }
    public Campaign(String title, Influencer... infs) { 
        this(title);
        addInfluencers(infs);
    }

    public void addInfluencers(Influencer... infs) {  
        for (Influencer i : infs) influencers.add(i);
    }

    public void track() {
        System.out.printf("Tracking campaign '%s'%n", title);
        metrics.compute(influencers.size(), influencers);
        System.out.println("  Impressions: " + metrics.getImpressions());
        System.out.println("  Clicks:      " + metrics.getClicks());
    }
    
    public String getTitle() {
        return title;
    }
    public Metrics getMetrics() {
        return metrics;
    }
    public List<Influencer> getInfluencers() {
        return influencers;
    }



 
    public static class Metrics {
        private Integer impressions; 
        private Integer clicks;

   
        public Metrics() {
            this(0, 0);
        }
        public Metrics(int imp, int clk) {
            this.impressions = imp;
            this.clicks = clk;
        }

      
//        public void compute(int influencersCount, List<Influencer> infs) {
//            this.impressions = influencersCount * 1000;
//            this.clicks = influencersCount * 100;
//        }
        public void compute(int influencersCount, List<Influencer> infs) {
        	 int baseImp = influencersCount * 1000;
        	    int baseClk = influencersCount * 100;
        	    int deltaImp = (int)(Math.random() * baseImp * 0.05);
        	    int deltaClk = (int)(Math.random() * baseClk * 0.05);
        	    this.impressions = baseImp + deltaImp;
        	    this.clicks      = baseClk + deltaClk;
        	
//        	 this.impressions += influencersCount * (10 + (int)(Math.random()*5));
//        	    this.clicks      += influencersCount * ( 1 + (int)(Math.random()*3));
        }

        public Integer getImpressions() { return impressions; }
        public Integer getClicks() { return clicks; }
    }
}
