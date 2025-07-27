package com.poly.service;

import com.poly.entity.User;

public interface UserService {

	User login(String username, String password);

	User getByUserName(String username);

	String generateAndStoreToken(String userId);

	boolean validateToken(String userId, String token);

}
