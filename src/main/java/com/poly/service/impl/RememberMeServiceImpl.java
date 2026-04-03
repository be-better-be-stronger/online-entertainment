package com.poly.service.impl;

import java.util.Date;

import com.poly.dao.RememberMeTokenDAO;
import com.poly.dao.impl.RememberMeTokenDAOImpl;
import com.poly.entity.RememberMeToken;
import com.poly.entity.User;
import com.poly.service.RememberMeService;
import com.poly.utils.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RememberMeServiceImpl implements RememberMeService{
	
	private final RememberMeTokenDAO rememberMeTokenDAO;
	
	public RememberMeServiceImpl() {
		this.rememberMeTokenDAO = new RememberMeTokenDAOImpl();
	}

	@Override
	public void handleRememberMe(HttpServletRequest req, HttpServletResponse resp, boolean remember, User user) {
		if (remember) {
			String token = generateAndStoreToken(user.getId());
			CookieUtil.setRememberCookies(req, resp, user.getEmail(), token);
		} else {
			clearToken(user.getId());
			CookieUtil.clearRememberCookies(req, resp);
		}
		
	}
	
	private String generateAndStoreToken(String userId) {
	    // Tạo token ngẫu nhiên
	    String token = java.util.UUID.randomUUID().toString();
	    Date expiry = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000); // 7 ngày

	    RememberMeToken rememberToken = new RememberMeToken();
	    rememberToken.setUserId(userId);
	    rememberToken.setToken(token);
	    rememberToken.setExpiryDate(expiry);

	    rememberMeTokenDAO.deleteTokenByUserId(userId); // Xóa token cũ
	    rememberMeTokenDAO.save(rememberToken);

	    return token;
	}

	@Override
	public boolean validateToken(String userId, String token) {
		RememberMeToken stored = rememberMeTokenDAO.findByUserId(userId);
        return stored != null &&
               stored.getToken().equals(token) &&
               stored.getExpiryDate().after(new Date());
	}

	@Override
	public void clearToken(String userId) {
		rememberMeTokenDAO.deleteTokenByUserId(userId);		
	}

	
	@Override
	public boolean hasToken(String userId) {
		if(rememberMeTokenDAO.findByUserId(userId) != null) return true;
		return false;
	}

	

}
