package com.poly.controller;

import java.io.IOException;

import com.poly.entity.User;
import com.poly.exception.AppException;
import com.poly.exception.AppExceptionHandler;
import com.poly.service.FavoriteService;
import com.poly.service.impl.FavoriteServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/video/like")
public class LikeServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final FavoriteService favoriteService = new FavoriteServiceImpl();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String videoId = req.getParameter("videoId");
	    User user = (User) req.getSession().getAttribute("currentUser");
	    System.out.println(">>> Current user: " + user);

	    if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
	    try {
	        favoriteService.toggleLike(user.getId(), videoId);
	        // Quay về trang trước đó
	        String referer = req.getHeader("referer");
	        resp.sendRedirect(referer != null ? referer : req.getContextPath() + "/home");
	    } catch (AppException ex) {
	        AppExceptionHandler.handle(req, resp, ex);
	        return;
	    }

	    
	}


}
