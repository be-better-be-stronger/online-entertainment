package com.poly.controller;

import java.io.IOException;

import com.poly.entity.User;
import com.poly.service.UserService;
import com.poly.service.impl.UserServiceImpl;
import com.poly.utils.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/delete-account")
public class DeleteAccountServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final UserService userService;
    public DeleteAccountServlet() {
    	this.userService = new UserServiceImpl();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.setAttribute("view", "/WEB-INF/views/user/confirm-delete.jsp");
        req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
	}
    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null) {
            User currentUser = (User) session.getAttribute("currentUser");
            if (currentUser != null) {
                // 1. Xóa tài khoản
                userService.delete(currentUser.getId());

                // 2. Hủy session
                session.invalidate();

                // 3. Xóa cookie "remember"
               
                CookieUtil.clearRememberCookies(req, resp);              
               

                // 4. Chuyển về trang chủ
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }
        }

        // Nếu không đăng nhập → về login
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}

