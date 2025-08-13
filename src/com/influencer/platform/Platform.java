//package com.influencer.platform;
//
//import com.influencer.platform.ui.AnalyticsDashboard;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.*;
//
//public class Platform {
//    public static void main(String[] args) {
//        Scanner console = new Scanner(System.in);
//        AuthService auth;
//
//        // 1) Initialize AuthService
//        try {
//            auth = new AuthService("credentials.properties");
//        } catch (IOException e) {
//            System.err.println("❌ Cannot load credentials: " + e.getMessage());
//            return;
//        }
//
//        // 2) Signup/Login loop
//        String user = null;
//        while (user == null) {
//            System.out.println("1) Login");
//            System.out.println("2) Signup");
//            System.out.print("Choose [1-2]: ");
//            String choice = console.nextLine().trim();
//
//            System.out.print("Username: ");
//            String uname = console.nextLine().trim();
//            System.out.print("Password: ");
//            String pwd = console.nextLine().trim();
//
//            if ("1".equals(choice)) {
//                // —— LOGIN ——
//                if (auth.login(uname, pwd)) {
//                    user = uname;
//                    System.out.println("✅ Welcome back, " + user + "!\n");
//                } else {
//                    System.err.println("❌ Invalid credentials.\n");
//                }
//
//            } else if ("2".equals(choice)) {
//                // —— SIGNUP ——
//                try {
//                    if (auth.register(uname, pwd)) {
//                        System.out.println("✅ Signup successful—please login now.\n");
//                    } else {
//                        System.err.println("❌ Username already exists.\n");
//                    }
//                } catch (IOException ioe) {
//                    System.err.println("❌ Could not write credentials: " + ioe.getMessage());
//                    return;
//                }
//
//            } else {
//                System.err.println("❌ Invalid choice.\n");
//            }
//        }
//        // At this point, `user` is the logged-in username
//
//        // 3) Load influencers
//        BrandManager bm = new BrandManager(user, "AcmeCo", 50_000);
//        List<Influencer> influencers = new ArrayList<>();
//        bm.loadInfluencers(new File("influencers.csv"), influencers);
//
//        System.out.println("Loaded influencers:");
//        for (Influencer inf : influencers) {
//            inf.showProfile();
//        }
//        System.out.println();
//
//        // 4) Demo sponsorship (exception + overload)
//        if (!influencers.isEmpty()) {
//            try {
//                bm.offerSponsorship(influencers.get(0), 1_000, "SpringSale");
//            } catch (InvalidCampaignException e) {
//                System.err.println("⚠️ " + e.getMessage());
//            }
//        }
//        System.out.println();
//
//        // 5) Write a simple report
//        try (FileWriter fw = new FileWriter("report.txt")) {
//            fw.write("=== Campaign Report for " + user + " ===\n");
//            for (Influencer inf : influencers) {
//                fw.write(inf.getName() + "\n");
//            }
//            System.out.println("Report written to report.txt\n");
//        } catch (IOException ioe) {
//            System.err.println("❌ Could not write report: " + ioe.getMessage());
//        }
//
//        // 6) Start payment thread
//        PaymentService ps = new PaymentService(1_500);
//        new Thread(ps, "Payment-Thread").start();
//
//        // 7) Recommendations demo
//        RecommendationEngine re = new RecommendationEngine();
//        re.recommend(bm);
//        re.recommend(bm, 3);
//        re.recommend(bm, "fashion", "travel");
//        System.out.println();
//
//        // 8) Create a per-user campaign (you could load these from disk)
//        Campaign myCampaign = new Campaign("Launch-" + user,
//                                          influencers.toArray(new Influencer[0]));
//        myCampaign.track();
//        System.out.println();
//
//        // 9) Fire up the real-time dashboard with *this user’s* campaigns
//        List<Campaign> myCampaigns = Collections.singletonList(myCampaign);
//        AnalyticsDashboard dashboard = new AnalyticsDashboard(myCampaigns);
//        new Thread(dashboard, "Dashboard-Thread").start();
//    }
//}

package com.influencer.platform;

import com.influencer.platform.ui.AnalyticsDashboard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Platform {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        AuthService auth;

        // 1) Initialize AuthService
        try {
            auth = new AuthService("credentials.properties");
        } catch (IOException e) {
            System.err.println("❌ Cannot load credentials: " + e.getMessage());
            return;
        }

        // 2) Signup/Login loop
        String user = null;
        while (user == null) {
            System.out.println("1) Login");
            System.out.println("2) Signup");
            System.out.print("Choose [1-2]: ");
            String choice = console.nextLine().trim();

            System.out.print("Username: ");
            String uname = console.nextLine().trim();
            System.out.print("Password: ");
            String pwd = console.nextLine().trim();

            if ("1".equals(choice)) {
                // —— LOGIN ——
                if (auth.login(uname, pwd)) {
                    user = uname;
                    System.out.println("✅ Welcome back, " + user + "!\n");
                } else {
                    System.err.println("❌ Invalid credentials.\n");
                }

            } else if ("2".equals(choice)) {
                // —— SIGNUP ——
                try {
                    if (auth.register(uname, pwd)) {
                        System.out.println("✅ Signup successful—please login now.\n");
                    } else {
                        System.err.println("❌ Username already exists.\n");
                    }
                } catch (IOException ioe) {
                    System.err.println("❌ Could not write credentials: " + ioe.getMessage());
                    return;
                }

            } else {
                System.err.println("❌ Invalid choice.\n");
            }
        }
        // At this point, `user` is the logged-in username

        // 3) Load influencers
        BrandManager bm = new BrandManager(user, "AcmeCo", 50_000);
        List<Influencer> influencers = new ArrayList<>();
        bm.loadInfluencers(new File("influencers.csv"), influencers);

        System.out.println("Loaded influencers:");
        for (Influencer inf : influencers) {
            inf.showProfile();
        }
        System.out.println();

        // 4) Demo: filtering by demographics
        System.out.println("Demo: filtering by demographics [18-25, US]:");
        if (!influencers.isEmpty()) {
            influencers.get(0).searchByDemographics("18-25", "US");
        }
        System.out.println();

        // 5) Demo sponsorship (exception + overload)
        if (!influencers.isEmpty()) {
            try {
                bm.offerSponsorship(influencers.get(0), 1_000, "SpringSale");
            } catch (InvalidCampaignException e) {
                System.err.println("⚠️ " + e.getMessage());
            }
        }
        System.out.println();

        // 6) Write a simple report
        try (FileWriter fw = new FileWriter("report.txt")) {
            fw.write("=== Campaign Report for " + user + " ===\n");
            for (Influencer inf : influencers) {
                fw.write(inf.getName() + "\n");
            }
            System.out.println("Report written to report.txt\n");
        } catch (IOException ioe) {
            System.err.println("❌ Could not write report: " + ioe.getMessage());
        }

        // 7) Start payment thread and wait for it to finish
        PaymentService ps = new PaymentService(1_500);
        Thread paymentThread = new Thread(ps, "Payment-Thread");
        paymentThread.start();
        try {
            paymentThread.join();
            System.out.println("✅ Payment completed successfully!\n");
        } catch (InterruptedException e) {
            System.err.println("❌ Payment thread interrupted.");
        }

        // 8) Recommendations demo (real logic)
        System.out.println("Default top 5 influencers by engagement rate:");
        influencers.stream()
            .sorted(Comparator.comparing(Influencer::getEngagementRate).reversed())
            .limit(5)
            .forEach(i -> System.out.printf("  %s (%.2f%%)%n",
                              i.getName(), i.getEngagementRate()));
        System.out.println();

        System.out.println("Top 3 influencers by follower count:");
        influencers.stream()
            .sorted(Comparator.comparing(Influencer::getFollowers).reversed())
            .limit(3)
            .forEach(i -> System.out.printf("  %s (%d followers)%n",
                              i.getName(), i.getFollowers()));
        System.out.println();

        System.out.println("Filter by tags [fashion, travel]:");
        List<String> tags = Arrays.asList("fashion","travel");
        influencers.stream()
            .filter(i -> tags.contains(i.getNiche().toLowerCase()))
            .forEach(i -> System.out.printf("  %s [%s]%n",
                              i.getName(), i.getNiche()));
        System.out.println();

        // 9) Create a per-user campaign
        Campaign myCampaign = new Campaign("Launch-" + user,
                                           influencers.toArray(new Influencer[0]));
        myCampaign.track();
        System.out.println();

        // 10) Fire up the real-time dashboard
        List<Campaign> myCampaigns = Collections.singletonList(myCampaign);
        AnalyticsDashboard dashboard = new AnalyticsDashboard(myCampaigns);
        new Thread(dashboard, "Dashboard-Thread").start();
    }
}


