package com.poly.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/error")
public class ErrorHandlerServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        Integer statusCode = (Integer) req.getAttribute("jakarta.servlet.error.status_code");
        String requestUri = (String) req.getAttribute("jakarta.servlet.error.request_uri");

        if (statusCode == null) {
            statusCode = 500; // fallback nếu lỗi không rõ
        }

        // Tùy chỉnh thông báo lỗi
        String message;
        switch (statusCode) {
            case 403:
                message = "Bạn không có quyền truy cập tài nguyên này.";
                break;
            case 404:
                message = "Không tìm thấy tài nguyên bạn yêu cầu.";
                break;
            default:
                message = "Đã xảy ra lỗi không xác định.";
                break;
        }

        req.setAttribute("statusCode", statusCode);
        req.setAttribute("message", message);
        req.setAttribute("requestUri", requestUri);

        req.setAttribute("view", "/WEB-INF/views/error/general-error.jsp");
        req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
    }
}

