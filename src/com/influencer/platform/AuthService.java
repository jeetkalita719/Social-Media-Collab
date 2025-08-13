//package com.influencer.platform;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.security.MessageDigest;
//import java.util.Base64;
//import java.util.Properties;
//
//// Simple file-backed SHA-256 login
//public class AuthService {
//    private final Properties creds = new Properties();
//
//    public AuthService(String propertiesPath) throws IOException {
//        try (FileInputStream in = new FileInputStream(propertiesPath)) {
//            creds.load(in);
//        }
//    }
//
//    /** Returns true if username exists and password hash matches. */
//    public boolean login(String username, String password) {
//        String storedHash = creds.getProperty(username);
//        if (storedHash == null) return false;
//        return hash(password).equals(storedHash);
//    }
//
//    /** No-op for now, but could clear session, tokens, etc. */
//    public void logout(String username) {
//        System.out.println(username + " logged out.");
//    }
//
//    private String hash(String password) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] digest = md.digest(password.getBytes("UTF-8"));
//            StringBuilder sb = new StringBuilder();
//            for (byte b : digest) sb.append(String.format("%02x", b));
//            return sb.toString();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}



package com.influencer.platform;

import java.io.*;
import java.security.MessageDigest;
import java.util.Properties;

public class AuthService {
    private final String propsPath;
    private final Properties creds = new Properties();

    /** Load existing usernames & hashed passwords **/
    public AuthService(String propertiesPath) throws IOException {
        this.propsPath = propertiesPath;
        try (FileInputStream in = new FileInputStream(propertiesPath)) {
            creds.load(in);
        }
    }

    /** True if username exists and password’s SHA-256 hash matches */
    public boolean login(String username, String password) {
        String storedHash = creds.getProperty(username);
        return storedHash != null && storedHash.equals(hash(password));
    }

    /** Register a new user: returns false if username already taken */
    public boolean register(String username, String password) throws IOException {
        if (creds.containsKey(username)) return false;
        String h = hash(password);
        creds.setProperty(username, h);
        // write back to disk immediately
        try (FileOutputStream out = new FileOutputStream(propsPath)) {
            creds.store(out, "Updated on signup");
        }
        return true;
    }

    /** Stub—could clear session data, tokens, etc. */
    public void logout(String username) {
        System.out.println(username + " logged out.");
    }

    /** SHA-256 → hex */
    private String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Could not hash password", e);
        }
    }
}

