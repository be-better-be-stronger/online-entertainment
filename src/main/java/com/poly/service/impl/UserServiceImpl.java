package com.poly.service.impl;


import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poly.dao.RememberMeTokenDAO;
import com.poly.dao.UserDAO;
import com.poly.dao.impl.RememberMeTokenDAOImpl;
import com.poly.dao.impl.UserDAOImpl;
import com.poly.entity.User;
import com.poly.exception.AppException;
import com.poly.service.UserService;


public class UserServiceImpl implements UserService{
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private final UserDAO userDAO;
	private final RememberMeTokenDAO rememberMeTokenDAO;
	
	public UserServiceImpl() {
		this.userDAO = new UserDAOImpl();
		this.rememberMeTokenDAO = new RememberMeTokenDAOImpl();
	}
	
	
	@Override
	public User login(String email, String password) {
		User user = userDAO.findByEmail(email);

	    if (user == null) {
	    	log.warn("Login failed: invalid credentials for email={}", email);
	    	return null;
	    }

	    if (!BCrypt.checkpw(password, user.getPassword())) {
	    	log.warn("Login failed: invalid credentials for email={}", email);
	        return null;
	    }
	    
	    log.info("Login success for email={}", email);
	    return user;
	}
	
	@Override
	public User getByUserName(String username) {
		return userDAO.findById(username);
	}
	
	@Override
	public User findByEmail(String email) {
		return userDAO.findByEmail(email);
	}
	@Override
	public void create(User user) {
		userDAO.insert(user);		
	}
	@Override
	public List<User> findAll() {
		return userDAO.findAll();
	}
	@Override
	public void update(User user) {
		userDAO.update(user);
		
	}
	@Override
	public void delete(String userId) {
		rememberMeTokenDAO.deleteTokenByUserId(userId);
		userDAO.delete(userId);
	}


	@Override
	public List<User> findPageByRole(Boolean isAdmin, int page, int size) {
		return userDAO.findPageByRole(isAdmin, page, size);
	}


	@Override
	public int countByRole(Boolean isAdmin) {
		return userDAO.countByRole(isAdmin);
	}


	@Override
	public void deleteUser(User currentUser, String targetUserId) {
	    if (currentUser == null || targetUserId == null || targetUserId.isBlank()) {
	        throw new AppException("Thông tin không hợp lệ để xóa người dùng");
	    }

	    boolean isAdmin = Boolean.TRUE.equals(currentUser.getAdmin());

	    if (!isAdmin && !currentUser.getId().equals(targetUserId)) {
	        throw new AppException("Bạn không có quyền xóa người dùng khác");
	    }

	    if (isAdmin && currentUser.getId().equals(targetUserId)) {
	        throw new AppException("Admin không thể tự xóa tài khoản của chính mình");
	    }

	    try {
	        // Nếu DAO delete trả boolean thì có thể check tồn tại tại đây
	        rememberMeTokenDAO.deleteTokenByUserId(targetUserId);
	        boolean deleted = userDAO.delete(targetUserId);

	        if (!deleted) {
	            throw new AppException("Người dùng cần xóa không tồn tại");
	        }

	        log.info("User {} deleted user {}", currentUser.getId(), targetUserId);
	    } catch (Exception e) {
	        log.error("Error deleting user {} by {}", targetUserId, currentUser.getId(), e);
	        throw e; // hoặc wrap lại
	    }
	}


	@Override
	public List<User> findPageByNameOrEmailAndRole(Boolean isAdmin, String keyword, int page, int size) {
		try {
			return userDAO.findPageByNameOrEmailAndRole(isAdmin, keyword, page, size);
		} catch (AppException e) {
			log.error("Search user failed: {}", e.getMessage(), e);
			throw e;
		}
	}


	@Override
	public int countByNameOrEmailAndRole(Boolean isAdmin, String keyword) {
		log.debug("Service: counting users with keyword='{}'", keyword);
        try {
            int count = userDAO.countByNameOrEmailAndRole(isAdmin, keyword);
            log.debug("Service: found {} users matching keyword='{}'", count, keyword);
            return count;
        } catch (AppException e) {
            log.error("Service: error while counting users by keyword='{}'", keyword, e);
            throw e;
        }
	}

	
}
