package com.poly.controller;

import java.io.IOException;
import java.net.URLEncoder;

import com.poly.entity.User;
import com.poly.service.UserService;
import com.poly.service.impl.UserServiceImpl;
import com.poly.utils.HashUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {
	private final UserService userService;
	
	public ResetPasswordServlet() {
		this.userService = new UserServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("view", "/WEB-INF/views/user/reset-password.jsp");
		req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String newPassword = req.getParameter("password");
		String email = (String) req.getSession().getAttribute("reset_email");

		if (email == null) {
			req.setAttribute("error", "Phiên xác thực không hợp lệ. Vui lòng thử lại.");
			doGet(req, resp);
			return;
		}

		User user = userService.findByEmail(email);
		user.setPassword(HashUtil.hash(newPassword));
		userService.update(user);

		req.getSession().removeAttribute("reset_email");
		req.getSession().removeAttribute("reset_code");
		String msg = URLEncoder.encode("Đặt lại mật khẩu thành công", "UTF-8");
		resp.sendRedirect(req.getContextPath() + "/login?success=" + msg);
	}
}
