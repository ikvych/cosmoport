package com.space.exeption;

public class ShipNotFoundException extends RuntimeException {
    public ShipNotFoundException(String message) {
        super(message);
    }
}
