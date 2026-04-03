package com.poly.controller;

import java.io.IOException;
import java.net.URLEncoder;

import org.mindrot.jbcrypt.BCrypt;

import com.poly.entity.User;
import com.poly.exception.AppExceptionHandler;
import com.poly.service.UserService;
import com.poly.service.impl.UserServiceImpl;
import com.poly.utils.HashUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
@WebServlet("/change-password")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final UserService userService;
	
	public ChangePasswordServlet() {
		this.userService = new UserServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.setAttribute("view", "/WEB-INF/views/user/change-password.jsp");
        req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);
		User currentUser = (User) session.getAttribute("currentUser");
		
		if(session == null || currentUser == null) {
			String msg = URLEncoder.encode("Chưa đăng nhập! ", "UTF-8");
            resp.sendRedirect(req.getContextPath() + "/login?error=" + msg);
            return;
		}
		
		String oldPassword = req.getParameter("oldPassword");
		String newPassword = req.getParameter("newPassword");
		String confirmPassword = req.getParameter("confirmPassword");
		
		if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("error", "Xác nhận mật khẩu không khớp.");
        } else if (!BCrypt.checkpw(oldPassword, currentUser.getPassword())) {
            req.setAttribute("error", "Mật khẩu cũ không đúng.");
        } else {
            try {
                currentUser.setPassword(HashUtil.hash(newPassword));
                userService.update(currentUser);
                String msg = URLEncoder.encode("Đổi mật khẩu thành công! ", "UTF-8");
                resp.sendRedirect(req.getContextPath() + "/login?success=" + msg);
                return;
            } catch (Exception e) {
                AppExceptionHandler.handle(req, resp, e);
                return;
            }
        }

        req.setAttribute("view", "/WEB-INF/views/user/change-password.jsp");
        req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
	}
}

