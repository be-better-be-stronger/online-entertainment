package com.poly.exception;

import java.util.logging.*;
import jakarta.servlet.http.*;

public class AppExceptionHandler {
    private static final Logger logger = Logger.getLogger(AppExceptionHandler.class.getName());

    public static void handle(HttpServletRequest req, HttpServletResponse resp, Exception ex, String action) {
        try {
            // Ghi log chi tiết
            logger.log(Level.SEVERE, "Lỗi khi thực hiện: " + action, ex);

            // Truyền thông báo lỗi xuống JSP
            req.setAttribute("errorMessage", "Đã xảy ra lỗi khi " + action + ": " + ex.getMessage());
            req.setAttribute("view", "/WEB-INF/views/error.jsp");
            req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
        } catch (Exception handlerEx) {
            // Nếu cả xử lý lỗi cũng lỗi 😅
            logger.log(Level.SEVERE, "Lỗi khi xử lý lỗi!", handlerEx);
        }
    }

    // Dùng mặc định nếu không truyền action
    public static void handle(HttpServletRequest req, HttpServletResponse resp, Exception ex) {
        handle(req, resp, ex, "thực hiện thao tác");
    }
}
