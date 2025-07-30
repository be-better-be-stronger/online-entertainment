package com.poly.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import jakarta.servlet.http.*;

public class AppExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class.getName());

    public static void handle(HttpServletRequest req, HttpServletResponse resp, Exception ex, String action) {
        try {
        	// Ghi log gọn gàng
            logger.error("❌ Lỗi khi: {}", action);
            logger.error("📄 Chi tiết lỗi: {}", ex.getMessage());

            // Truyền thông báo lỗi xuống JSP
            req.setAttribute("errorMessage", "Đã xảy ra lỗi khi " + action + ": " + ex.getMessage());
            req.setAttribute("view", "/WEB-INF/views/error.jsp");
            req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
        } catch (Exception handlerEx) {
        	logger.error("‼️ Lỗi khi xử lý ngoại lệ!", handlerEx); // vẫn cho phép trace nếu xử lý lỗi cũng lỗi 
        }
    }

    // Dùng mặc định nếu không truyền action
    public static void handle(HttpServletRequest req, HttpServletResponse resp, Exception ex) {
        handle(req, resp, ex, "thực hiện thao tác");
    }
}
