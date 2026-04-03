package com.poly.utils;

import java.security.SecureRandom;

public class VerificationCodeUtil {
		 private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		    private static final SecureRandom random = new SecureRandom();
	
		    public static String generateCode(int length) {
		        StringBuilder sb = new StringBuilder(length);
		        for (int i = 0; i < length; i++) {
		            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
		        }
		        return sb.toString();
	    }
}
