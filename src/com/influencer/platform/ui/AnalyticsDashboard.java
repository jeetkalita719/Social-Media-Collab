
//
//
//package com.influencer.platform.ui;
//
//import com.influencer.platform.Campaign;
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//public class AnalyticsDashboard extends JFrame implements Runnable {
//    private final DefaultTableModel tableModel;
//    private final JLabel totalImpLabel = new JLabel();
//    private final JLabel totalClkLabel = new JLabel();
//    private final JLabel lastRefLabel = new JLabel();
//    private final List<Campaign> campaigns;
//
//    public AnalyticsDashboard(List<Campaign> campaigns) {
//        super("Real-Time Analytics Dashboard");
//        this.campaigns = campaigns;
//
//        // Top summary panel
//        JPanel summary = new JPanel(new GridLayout(1, 3, 10, 0));
//        summary.add(totalImpLabel);
//        summary.add(totalClkLabel);
//        summary.add(lastRefLabel);
//
//        // Table
//        tableModel = new DefaultTableModel(new String[]{"Campaign","Impressions","Clicks"}, 0);
//        JTable table = new JTable(tableModel);
//
//        // Manual refresh button
//        JButton refreshBtn = new JButton("Refresh Now");
//        refreshBtn.addActionListener(e -> updateData());
//
//        // Layout
//        setLayout(new BorderLayout(5,5));
//        add(summary, BorderLayout.NORTH);
//        add(new JScrollPane(table), BorderLayout.CENTER);
//        add(refreshBtn, BorderLayout.SOUTH);
//
//        setSize(500, 350);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setVisible(true);
//    }
//
//    /** Pull fresh data into labels & table */
//    private void updateData() {
//        // compute totals
//        int totImp = 0, totClk = 0;
//        tableModel.setRowCount(0);
//        for (Campaign c : campaigns) {
//            var m = c.getMetrics();
//            tableModel.addRow(new Object[]{ c.getTitle(), m.getImpressions(), m.getClicks() });
//            totImp += m.getImpressions();
//            totClk += m.getClicks();
//        }
//        totalImpLabel.setText("Total Impressions: " + totImp);
//        totalClkLabel.setText("Total Clicks:       " + totClk);
//        lastRefLabel.setText("Last refresh: " +
//            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//    }
//
//    @Override
//    public void run() {
//        while (!Thread.currentThread().isInterrupted()) {
//            SwingUtilities.invokeLater(this::updateData);
//            try { Thread.sleep(2_000); }
//            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
//        }
//    }
//}
//


package com.influencer.platform.ui;

import com.influencer.platform.Campaign;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AnalyticsDashboard extends JFrame implements Runnable {
    private final DefaultTableModel tableModel;
    private final JLabel totalImpLabel  = new JLabel("Total Impressions: 0");
    private final JLabel totalClkLabel  = new JLabel("Total Clicks:       0");
    private final JLabel lastRefLabel   = new JLabel("Last refresh: --:--:--");
    private final List<Campaign> campaigns;

    public AnalyticsDashboard(List<Campaign> campaigns) {
        super("Real-Time Analytics Dashboard");
        this.campaigns = campaigns;

        // --- Summary panel ---
        JPanel summary = new JPanel(new GridLayout(1, 3, 10, 0));
        summary.add(totalImpLabel);
        summary.add(totalClkLabel);
        summary.add(lastRefLabel);

        // --- Campaign table ---
        tableModel = new DefaultTableModel(new String[]{"Campaign","Impressions","Clicks"}, 0);
        JTable table = new JTable(tableModel);

        // --- Manual refresh button ---
        JButton refreshBtn = new JButton("Refresh Now");
        refreshBtn.addActionListener(e -> updateData());

        // --- Layout everything ---
        setLayout(new BorderLayout(5,5));
        add(summary, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(refreshBtn, BorderLayout.SOUTH);

        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /** Pull fresh metrics and update the labels/table */
//    private void updateData() {
//        int totImp = 0, totClk = 0;
//        tableModel.setRowCount(0);
//
//        for (Campaign c : campaigns) {
//            // RECOMPUTE on every refresh
//            c.getMetrics().compute(
//                c.getInfluencers().size(),
//                c.getInfluencers()
//            );
//
//            int imp = c.getMetrics().getImpressions();
//            int clk = c.getMetrics().getClicks();
//            tableModel.addRow(new Object[]{ c.getTitle(), imp, clk });
//
//            totImp += imp;
//            totClk += clk;
//        }
//
//        totalImpLabel.setText("Total Impressions: " + totImp);
//        totalClkLabel.setText("Total Clicks:       " + totClk);
//        lastRefLabel.setText("Last refresh: " +
//            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//    }
    
    
    private void updateData() {
        int totImp = 0, totClk = 0;
        tableModel.setRowCount(0);

        for (Campaign c : campaigns) {
            // 1) Recompute metrics afresh
            c.getMetrics().compute(
                c.getInfluencers().size(),
                c.getInfluencers()
            );
            // 2) Pull the newly randomized metrics
            int imp = c.getMetrics().getImpressions();
            int clk = c.getMetrics().getClicks();
            tableModel.addRow(new Object[]{ c.getTitle(), imp, clk });

            totImp += imp;
            totClk += clk;
        }

        totalImpLabel.setText("Total Impressions: " + totImp);
        totalClkLabel.setText("Total Clicks:       " + totClk);
        lastRefLabel.setText("Last refresh: " +
            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            SwingUtilities.invokeLater(this::updateData);
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

