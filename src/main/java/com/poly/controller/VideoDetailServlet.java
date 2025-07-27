package com.poly.controller;

import java.io.IOException;
import com.poly.entity.Video;
import com.poly.exception.AppExceptionHandler;
import com.poly.service.VideoService;
import com.poly.service.impl.VideoServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/video-detail")
public class VideoDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final VideoService videoService = new VideoServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String id = req.getParameter("id");
		if (id == null || id.isEmpty()) {
			resp.sendRedirect("home");
			return;
		}
		
		try {
		    Video video = videoService.getVideoById(id);
		    req.setAttribute("video", video);
		    req.setAttribute("view", "/WEB-INF/views/user/video-detail.jsp");
		    req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
		} catch (Exception ex) {
		    AppExceptionHandler.handle(req, resp, ex, "xem chi tiết video");
		}

	}
}
