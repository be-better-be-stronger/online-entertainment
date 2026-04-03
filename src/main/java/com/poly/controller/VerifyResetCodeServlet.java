package com.poly.controller;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/verify-reset-code")
public class VerifyResetCodeServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, 
		IOException {
		req.setAttribute("view", "/WEB-INF/views/user/verify-reset-code.jsp");
		req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String inputCode = req.getParameter("code");
		String sessionCode = (String) req.getSession().getAttribute("reset_code");

		if (inputCode == null || !inputCode.equals(sessionCode)) {
			req.setAttribute("error", "Mã xác nhận không đúng.");
			doGet(req, resp);
			return;
		}

		
        resp.sendRedirect(req.getContextPath() + "/reset-password");
	}
}

