package com.poly.controller;

import java.io.IOException;
import java.net.URLEncoder;

import com.poly.entity.User;
import com.poly.exception.AppExceptionHandler;
import com.poly.service.MailService;
import com.poly.service.UserService;
import com.poly.service.impl.MailServiceImpl;
import com.poly.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/verify-code")
public class VerifyCodeServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final UserService userService;
	private final MailService mailService;
	
	public VerifyCodeServlet() {
		this.userService = new UserServiceImpl();
		this.mailService = new MailServiceImpl();
	}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("view", "/WEB-INF/views/user/verify-code.jsp");
        req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
        	String inputCode = req.getParameter("code").trim();
            HttpSession session = req.getSession(false);

            String expectedCode = (String) session.getAttribute("verificationCode");
            System.out.println(expectedCode);
            if (expectedCode == null || !expectedCode.equalsIgnoreCase(inputCode)) {
                req.setAttribute("error", "Mã xác thực không đúng.");
                req.setAttribute("view", "/WEB-INF/views/user/verify-code.jsp");
                req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
                return;
            }

            // ✅ Lấy thông tin từ session và tạo user
            User user = (User)session.getAttribute("pendingUser");

            userService.create(user);

            // ✅ Xóa session tạm
            session.removeAttribute("pendingUser");
            session.removeAttribute("verificationCode");
            
            mailService.sendWelcomeMail(user.getEmail(), user.getFullname());

            // ✅ Chuyển hướng đến login
            String msg = URLEncoder.encode("Đăng ký thành công! Mời bạn đăng nhập.", "UTF-8");
            resp.sendRedirect(req.getContextPath() + "/login?success=" + msg);
		} catch (Exception e) {
			AppExceptionHandler.handle(req, resp, e);
		}
    }

	
}