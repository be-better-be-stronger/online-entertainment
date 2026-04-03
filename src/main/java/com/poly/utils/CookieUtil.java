package com.poly.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
		private static final int DEFAULT_EXPIRY = 7 * 24 * 60 * 60; // 7 ngày
	
		public static void setRememberCookies(HttpServletRequest req, HttpServletResponse resp, String email, String token) {
	        createCookie("remember_email", email, DEFAULT_EXPIRY, req, resp);
	        createCookie("remember_token", token, DEFAULT_EXPIRY, req, resp);
	    }
		
	 	public static void clearRememberCookies(HttpServletRequest req, HttpServletResponse resp) {
	        deleteCookie("remember_email", req, resp);
	        deleteCookie("remember_token", req, resp);
	    }
	 	
	 	private static void createCookie(String name, String value, int expiry, HttpServletRequest req, HttpServletResponse resp) {
	        Cookie cookie = new Cookie(name, value);
	        cookie.setMaxAge(expiry);
	        cookie.setHttpOnly(true);
	        cookie.setPath(req.getContextPath());
	        resp.addCookie(cookie);
	    }

	    private static void deleteCookie(String name, HttpServletRequest req, HttpServletResponse resp) {
	        Cookie cookie = new Cookie(name, "");
	        cookie.setMaxAge(0); // xóa ngay
	        cookie.setPath(req.getContextPath()); // quan trọng: phải trùng path lúc tạo
	        resp.addCookie(cookie);
	    }

}
