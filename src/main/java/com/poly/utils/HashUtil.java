package com.poly.utils;

import org.mindrot.jbcrypt.BCrypt;


public class HashUtil {
		private static final int LOG_ROUNDS = 10; // Số vòng băm (10–12 là ổn)		
		
		/**
	     * Mã hóa mật khẩu thuần bằng thuật toán BCrypt
	     * 
	     * @param plainPassword Mật khẩu chưa mã hóa
	     * @return Mật khẩu đã được hash
	     */
	    public static String hash(String plainPassword) {
	        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(LOG_ROUNDS));
	    }
}
