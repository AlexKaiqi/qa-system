package com.alex.qasystem.util;

import java.security.SecureRandom;

public class SecurityUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    public static String generateToken(String prefix) {
        return prefix + Long.toString(Math.abs(RANDOM.nextLong()), 25);
    }

    public static String generateToken() {
        return generateToken("");
    }

    public static String hashpw(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkpw(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }

}
