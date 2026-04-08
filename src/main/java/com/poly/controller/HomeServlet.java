package com.poly.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poly.dto.response.HomeResponse;
import com.poly.entity.User;
import com.poly.service.VideoService;
import com.poly.service.impl.VideoServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HomeServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(HomeServlet.class);

	private final VideoService videoService = new VideoServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int size = 6;
		int page = 0;

		try {
			String pageParam = req.getParameter("page");
			if (pageParam != null) {
				page = Integer.parseInt(pageParam);
				if (page < 0)
					page = 0;
			}
			log.info("Home request - page={}", page);
		} catch (NumberFormatException e) {
			page = 0;
			log.warn("Invalid page param, fallback to 0", e);
		}

		User currentUser = (User) req.getSession().getAttribute("currentUser");
		log.debug("Current user={}", currentUser != null ? currentUser.getEmail() : "anonymous");

		// truyền user xuống service
		try {
			 HomeResponse data = videoService.getHomeData(page, size, currentUser);
		     log.info("Load home data success - page={}", page);
		     req.setAttribute("data", data);
		} catch (Exception e) {
			log.error("Error while loading home data", e);
            throw e;
		}
		// View layout
		req.setAttribute("view", "/WEB-INF/views/home.jsp");
		req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
	}

}
