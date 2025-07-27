package com.poly.controller;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import com.poly.entity.User;
import com.poly.exception.AppException;
import com.poly.service.UserService;
import com.poly.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Tự động đăng nhập nếu có cookie
		String username = null;
		String token = null;

		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if ("remember_username".equals(c.getName())) {
					username = c.getValue();
				}
				if ("remember_token".equals(c.getName())) {
					token = c.getValue();
				}
			}
		}

		if (username != null && token != null) {
			User user = userService.getByUserName(username);
			if (user != null && userService.validateToken(user.getId(), token)) {
				req.getSession().setAttribute("currentUser", user);
				resp.sendRedirect(req.getContextPath() + "/home");
				return;
			}
		}

		req.getRequestDispatcher("/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		boolean remember = req.getParameter("remember") != null;

		try {
			User user = userService.getByUserName(username);

			if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
				req.setAttribute("message", "Sai tài khoản hoặc mật khẩu");
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
				return;
			}

			// ✅ Đăng nhập thành công
			req.getSession().setAttribute("currentUser", user);

			// ✅ Xử lý Remember Me
			if (remember) {
				String token = userService.generateAndStoreToken(user.getId()); // Lưu vào DB

				Cookie cUser = new Cookie("remember_username", username);
				Cookie cToken = new Cookie("remember_token", token);

				int expiry = 7 * 24 * 60 * 60; // 7 ngày
				cUser.setMaxAge(expiry);
				cToken.setMaxAge(expiry);

				resp.addCookie(cUser);
				resp.addCookie(cToken);
			}

			resp.sendRedirect(req.getContextPath() + "/home");

		} catch (Exception e) {
			throw new AppException("Lỗi khi đăng nhập", e);
		}
	}
}
