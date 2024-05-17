package org.example.utils;

import java.security.SecureRandom;

public class OTPGenerator {
    private SecureRandom random = new SecureRandom();

    public int generateOTP() {
        return random.nextInt(1000000,9000000);
    }
}
