package com.influencer.platform;

import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class BrandManager extends User {
    private String company;
    private double budget;

    public BrandManager(String name, String company, double budget) {
        super(name, "BrandManager");
        this.company = company;
        this.budget = budget;
    }

    @Override
    public void showProfile() {
        System.out.printf("BrandManager[%d]: %s | Company: %s | Budget: $%.2f%n",
            id, name, company, budget);
    }

  
    public void loadInfluencers(File csv, List<Influencer> list) {
        try (Scanner sc = new Scanner(csv)) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
             
                Influencer inf = new Influencer(
                    parts[0],
                    parts[1],
                    Double.parseDouble(parts[2]),
                    Integer.parseInt(parts[3])
                );
                list.add(inf);
            }
        } catch (FileNotFoundException e) {
            System.err.println("CSV not found: " + e.getMessage());
        } catch (NumberFormatException ex) { 
            System.err.println("Bad number format in CSV: " + ex.getMessage());
        }
    }

   
    public void offerSponsorship(Influencer inf, double amount) {
        System.out.printf("Offering $%.2f to %s%n", amount, inf.getName());
    }
    public void offerSponsorship(Influencer inf, double amount, String campaignName)
            throws InvalidCampaignException { 
        if (campaignName == null || campaignName.isEmpty())
            throw new InvalidCampaignException("Campaign name required");
        System.out.printf("Offering $%.2f to %s for %s%n",
            amount, inf.getName(), campaignName);
    }
}
