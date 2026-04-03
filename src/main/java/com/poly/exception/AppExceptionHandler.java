package com.poly.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poly.entity.User;

import jakarta.servlet.http.*;

public class AppExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class.getName());

    public static void handle(HttpServletRequest req, HttpServletResponse resp, Exception ex) {
        try {
        	// Lấy thông tin request để log
            String url = req.getRequestURI();
            String query = req.getQueryString();
            String ip = req.getRemoteAddr();
            User currentUser = (User) req.getSession().getAttribute("currentUser");

            logger.error("❌ Lỗi khi xử lý request: {}?{}, IP={}, User={}", 
                    url, query, ip, 
                    (currentUser != null ? currentUser.getId() : "anonymous"), ex);
            
            // Thông báo an toàn cho người dùng
            req.setAttribute("errorMessage", "Đã xảy ra lỗi hệ thống. Vui lòng thử lại sau.");
            req.setAttribute("view", "/WEB-INF/views/error.jsp");
            req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
        } catch (Exception handlerEx) {
        	logger.error("‼️ Lỗi khi xử lý ngoại lệ!", handlerEx); // vẫn cho phép trace nếu xử lý lỗi cũng lỗi 
        }
    }


}
