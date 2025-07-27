package com.poly.dao;

import java.util.List;

import com.poly.entity.User;

public interface UserDAO {
	User findById(String userId);

	List<User> findAll();

	void update(User u);

}
