package com.abhi.smergersclone.AUTHENTICATION;

import java.util.Random;

public class OtpGenerator {
    public static String generate() {
        Random rand = new Random();
        int otp = 100000 + rand.nextInt(900000);
        return String.valueOf(otp);
    }
}
