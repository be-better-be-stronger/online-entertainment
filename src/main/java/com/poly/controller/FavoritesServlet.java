package com.poly.controller;

import java.io.IOException;
import java.util.List;

import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.FavoriteService;
import com.poly.service.impl.FavoriteServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.*;

@WebServlet("/favorites")
public class FavoritesServlet extends HttpServlet {
    /**
	 * 
	 */
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
		
		List<Video> favorites = favoriteService.findFavoritesByUser(currentUser.getId(), page, size);
		long totalItems = favoriteService.countFavoritesByUser(currentUser.getId());
		int totalPages = (int) Math.ceil((double) totalItems/size);
		
		req.setAttribute("favorites", favorites);
		req.setAttribute("page", page);
		req.setAttribute("totalPages", totalPages);
		
        req.setAttribute("view", "/WEB-INF/views/user/favorites.jsp");
        req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
    }
}
