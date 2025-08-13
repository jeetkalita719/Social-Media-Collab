package com.influencer.platform;


public class RecommendationEngine {

    public void recommend(BrandManager bm) {
        System.out.println("Default recommendations for " + bm.getName());
    }
    public void recommend(BrandManager bm, int topN) {
        System.out.printf("Top %d recommendations for %s%n", topN, bm.getName());
    }

    public void recommend(BrandManager bm, String... tags) {
        System.out.printf("Recommendations for %s in categories %s%n",
            bm.getName(), java.util.Arrays.toString(tags));
    }
}
