package com.influencer.platform;

public interface PaymentProcessor {
    void processPayment(double amount) throws InterruptedException;
}
