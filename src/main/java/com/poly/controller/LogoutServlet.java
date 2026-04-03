package com.poly.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.poly.entity.User;
import com.poly.service.RememberMeService;
import com.poly.service.impl.RememberMeServiceImpl;
import com.poly.utils.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RememberMeService rememberMeService;
	
	public LogoutServlet() {
		this.rememberMeService = new RememberMeServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if(session != null) {
			User currentUser = (User) session.getAttribute("currentUser");
			if(currentUser != null) {
				String userId = currentUser.getId();
				if(rememberMeService.hasToken(userId)) rememberMeService.clearToken(userId);
				session.invalidate();
			}
		}
		CookieUtil.clearRememberCookies(req, resp);
		
		// Chuyển hướng sau khi logout
		String message = URLEncoder.encode("Đăng xuất thành công", StandardCharsets.UTF_8);
		resp.sendRedirect(req.getContextPath() + "/login?success=" + message);

	}
}

