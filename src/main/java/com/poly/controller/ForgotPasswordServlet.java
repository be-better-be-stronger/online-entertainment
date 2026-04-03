package com.poly.controller;

import java.io.IOException;

import com.poly.entity.User;
import com.poly.service.MailService;
import com.poly.service.UserService;
import com.poly.service.impl.MailServiceImpl;
import com.poly.service.impl.UserServiceImpl;
import com.poly.utils.VerificationCodeUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.*;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final UserService userService;
	private final MailService mailService;
	
	public ForgotPasswordServlet() {
		this.userService = new UserServiceImpl();
		this.mailService = new MailServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("view", "/WEB-INF/views/user/forgot-password.jsp");
		req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		User user = userService.findByEmail(email);

		if (user == null) {
			req.setAttribute("error", "Không tìm thấy tài khoản với email này.");
			doGet(req, resp);
			return;
		}

		String code = VerificationCodeUtil.generateCode(6);
		req.getSession().setAttribute("reset_email", email);
		req.getSession().setAttribute("reset_code", code);
		req.getSession().setMaxInactiveInterval(5 * 60); // 5 phút

		// Gửi email
		mailService.sendVerifycationCode(email, user.getFullname(), code);

		resp.sendRedirect(req.getContextPath() + "/verify-reset-code");
	}
}


