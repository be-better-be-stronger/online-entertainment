package com.poly.controller.auth;

import java.io.IOException;

import com.poly.entity.User;
import com.poly.exception.AppExceptionHandler;
import com.poly.service.RememberMeService;
import com.poly.service.UserService;
import com.poly.service.impl.RememberMeServiceImpl;
import com.poly.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;
	private RememberMeService rememberMeService;
	
	public LoginServlet() {
		this.userService = new UserServiceImpl();
		this.rememberMeService = new RememberMeServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String error = (String) session.getAttribute("error");
		if (error != null) {
	        req.setAttribute("message", error);
	        session.removeAttribute("error");
	    }
		req.setAttribute("view", "/WEB-INF/views/user/login.jsp");
		req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		boolean remember = req.getParameter("remember") != null;

		try {
			User user = userService.login(email, password);

			if (user == null) {
				redirectToLoginWithError(req, resp, "Sai tài khoản hoặc mật khẩu");
				return;
			}			
			
			// Đăng nhập thành công
			req.getSession().setAttribute("currentUser", user);

			// Xử lý Remember Me
			rememberMeService.handleRememberMe(req, resp, remember, user);

			//Xử lý sau khi login thành công
			redirectAfterLogin(req, resp, user);

		} catch (Exception e) {
			AppExceptionHandler.handle(req, resp, e);
			return;
		}
	}
	
	private void redirectToLoginWithError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.getSession().setAttribute("message", message);
		resp.sendRedirect(req.getContextPath() + "/login");
	}


	private void redirectAfterLogin(HttpServletRequest req, HttpServletResponse resp, User user)
			throws IOException {
		HttpSession session = req.getSession();
		String redirectUrl = (String) session.getAttribute("redirectBack");
		session.removeAttribute("redirectBack");

		if (redirectUrl != null) {
			// Nếu redirect đến admin mà user không phải admin → chặn lại
			if (redirectUrl.startsWith(req.getContextPath() + "/admin") && !user.getAdmin()) {
				resp.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			resp.sendRedirect(redirectUrl);
		} else 
			resp.sendRedirect(req.getContextPath() + "/home");
		
	}
	
}
