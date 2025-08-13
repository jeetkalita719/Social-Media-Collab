package com.influencer.platform;

import java.util.concurrent.atomic.AtomicInteger;


public abstract class User implements Authenticatable {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);
    protected final int id;       
    protected String name;
    protected String role;

    public User() {
        this.id = NEXT_ID.getAndIncrement();
    }
    public User(String name, String role) {
        this();
        this.name = name;
        this.role = role;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }

    public abstract void showProfile();

 
    @Override
    public boolean login(String username, String password) {

        return username != null && password != null;
    }
    @Override
    public void logout() {
        System.out.println(name + " logged out.");
    }
}
