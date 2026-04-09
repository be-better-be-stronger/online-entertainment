package com.poly.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger log = LoggerFactory.getLogger(FavoritesServlet.class);
	private final FavoriteService favoriteService = new FavoriteServiceImpl();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
		log.info("[FavoritesServlet] GET /favorites - start");
		User currentUser = (User) req.getSession().getAttribute("currentUser");
		if(currentUser == null) {
			log.warn("User not logged in -> redirect to /login");
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		log.debug("Current user: id={}, email={}", currentUser.getId(), currentUser.getEmail());
		int page = 0, size = 6;
		try {
			String pageParam = req.getParameter("page");
			if(pageParam != null) {
				page = Integer.parseInt(pageParam);
				if (page < 0) {
					log.warn("Invalid page < 0 -> reset to 0");
					page = 0;
				}
			}
		} catch (NumberFormatException e) {
			log.warn("Invalid page format -> fallback to 0, value={}", req.getParameter("page"));
			page = 0;
		}
		
		log.info("Fetch favorites: page={}, size={}", page, size);
		FavoritesResponse favorites = favoriteService.findFavoritesByUser(currentUser, page, size);
		
		req.setAttribute("data", favorites);		
        req.setAttribute("view", "/WEB-INF/views/user/favorites.jsp");
        
        log.info("Forward to layout.jsp (favorites view)");
        req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
        
        log.info("[FavoritesServlet] GET /favorites - end");
    }
}
