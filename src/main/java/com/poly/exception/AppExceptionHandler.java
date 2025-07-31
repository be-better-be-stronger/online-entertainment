package com.poly.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.*;

public class AppExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class.getName());

    public static void handle(HttpServletRequest req, HttpServletResponse resp, Exception ex) {
        try {
            // Truyền thông báo lỗi xuống JSP
            req.setAttribute("errorMessage", "Đã xảy ra lỗi: " + ex.getMessage());
            req.setAttribute("view", "/WEB-INF/views/error.jsp");
            req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
        } catch (Exception handlerEx) {
        	logger.error("‼️ Lỗi khi xử lý ngoại lệ!", handlerEx); // vẫn cho phép trace nếu xử lý lỗi cũng lỗi 
        }
    }


}
