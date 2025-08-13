package com.influencer.platform;


public interface Authenticatable {
    boolean login(String username, String password);
    void logout();
}
