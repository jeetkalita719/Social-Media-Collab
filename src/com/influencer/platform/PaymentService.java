package com.influencer.platform;


public class PaymentService implements PaymentProcessor, Runnable {
    private double amount;

    public PaymentService(double amount) {
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            processPayment(amount);
            System.out.println("Payment thread complete.");
        } catch (InterruptedException e) {
            System.err.println("Payment interrupted.");
        }
    }

    @Override
    public void processPayment(double amt) throws InterruptedException {
        System.out.printf("Processing payment of $%.2f…%n", amt);
        Thread.sleep(500);  
        System.out.println("Payment successful.");
    }
}
