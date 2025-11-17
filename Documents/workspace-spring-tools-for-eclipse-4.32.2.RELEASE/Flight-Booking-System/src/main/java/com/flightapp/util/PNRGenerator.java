package com.flightapp.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PNRGenerator {
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();
    
    /**
     * Generates a unique PNR (Passenger Name Record)
     * Format: XXYYDDD (XX = random letters, YY = year, DDD = day of year + random)
     */
    public static String generatePNR() {
        StringBuilder pnr = new StringBuilder();
        

        for (int i = 0; i < 2; i++) {
            pnr.append(CHARACTERS.charAt(random.nextInt(26)));
        }
        

        LocalDateTime now = LocalDateTime.now();
        pnr.append(now.format(DateTimeFormatter.ofPattern("yy")));
        

        int dayOfYear = now.getDayOfYear();
        pnr.append(String.format("%03d", dayOfYear % 1000));
        

        for (int i = 0; i < 3; i++) {
            pnr.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        
        return pnr.toString();
    }
    
    /**
     * Validates PNR format
     */
    public static boolean isValidPNR(String pnr) {
        if (pnr == null || pnr.length() != 10) {
            return false;
        }
        return pnr.matches("^[A-Z0-9]{10}$");
    }
}
