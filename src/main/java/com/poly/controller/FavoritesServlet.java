package com.poly.controller;

import java.io.IOException;

import com.poly.dto.response.FavoritesResponse;
import com.poly.entity.User;
import com.poly.service.FavoriteService;
import com.poly.service.impl.FavoriteServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/favorites")
public class FavoritesServlet extends HttpServlet {
   
	private static final long serialVersionUID = 1L;
	private final FavoriteService favoriteService = new FavoriteServiceImpl();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
		User currentUser = (User) req.getSession().getAttribute("currentUser");
		if(currentUser == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		
		int page = 0, size = 6;
		try {
			String pageParam = req.getParameter("page");
			if(pageParam != null) {
				page = Integer.parseInt(pageParam);
				if (page < 0) page = 0;
			}
		} catch (NumberFormatException e) {
			page = 0;
		}
		
		FavoritesResponse favorites = favoriteService.findFavoritesByUser(currentUser, page, size);
		
		req.setAttribute("data", favorites);
		
        req.setAttribute("view", "/WEB-INF/views/user/favorites.jsp");
        req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
    }
}
