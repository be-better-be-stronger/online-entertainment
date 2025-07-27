package com.poly.service.impl;

import java.util.Date;

import com.poly.dao.RememberMeTokenDAO;
import com.poly.dao.UserDAO;
import com.poly.dao.impl.RememberMeTokenDAOImpl;
import com.poly.dao.impl.UserDAOImpl;
import com.poly.entity.RememberMeToken;
import com.poly.entity.User;
import com.poly.service.UserService;

public class UserServiceImpl implements UserService{
	private final UserDAO userDAO = new UserDAOImpl();
	private final RememberMeTokenDAO rememberMeTokenDAO = new RememberMeTokenDAOImpl();
	
	
	@Override
	public User login(String username, String password) {
	    User user = userDAO.findById(username);
	    if (user != null && user.getPassword().equals(password)) {
	        return user;
	    }
	    return null;
	}
	@Override
	public User getByUserName(String username) {
		return userDAO.findById(username);
	}
	
	@Override
	public String generateAndStoreToken(String userId) {
	    // Tạo token ngẫu nhiên
	    String token = java.util.UUID.randomUUID().toString();
	    Date expiry = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000); // 7 ngày

	    RememberMeToken rememberToken = new RememberMeToken();
	    rememberToken.setUserId(userId);
	    rememberToken.setToken(token);
	    rememberToken.setExpiryDate(expiry);

	    rememberMeTokenDAO.deleteByUserId(userId); // Xóa token cũ
	    rememberMeTokenDAO.save(rememberToken);

	    return token;
	}

	@Override
	public boolean validateToken(String userId, String token) {
	    return rememberMeTokenDAO.isValid(userId, token);
	}



}
