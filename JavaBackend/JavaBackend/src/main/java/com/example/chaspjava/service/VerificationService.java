package com.example.chaspjava.service;

public interface VerificationService {
    /**
     * Generate a code for the given email, store it and return the code.
     */
    String generateAndStore(String email);

    boolean verify(String email, String code);
}
