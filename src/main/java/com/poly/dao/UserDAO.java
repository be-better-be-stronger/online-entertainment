package com.poly.dao;

import java.util.List;

import com.poly.entity.User;

public interface UserDAO {
	User findById(String userId);

	List<User> findAll();

	void update(User u);
	
	User findByEmail(String email);

	void insert(User user);

	boolean delete(String userId);

	List<User> findPageByRole(Boolean isadmin, int page, int size);

	int countByRole(Boolean isAdmin);

	List<User> findPageByNameOrEmailAndRole(Boolean isAdmin, String keyword, int page, int size);

	int countByNameOrEmailAndRole(Boolean isAdmin, String keyword);

}
