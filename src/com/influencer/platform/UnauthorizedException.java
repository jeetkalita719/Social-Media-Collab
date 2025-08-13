package com.influencer.platform;

/** (New) Thrown when a user tries an operation their role doesn't allow */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }
}
