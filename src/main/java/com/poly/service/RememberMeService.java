/**
 * Giao diện dịch vụ quản lý token "Remember Me".
 * Cung cấp các phương thức để tạo, xác thực, xóa và kiểm tra sự tồn tại của token
 * liên kết với danh tính người dùng.
 */
package com.poly.service;

import com.poly.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RememberMeService {
	
	void handleRememberMe(HttpServletRequest req, HttpServletResponse resp, boolean remember, User user);
	
	boolean validateToken(String userId, String token);
	
	void clearToken(String userId);
	
	boolean hasToken(String userId);

}
