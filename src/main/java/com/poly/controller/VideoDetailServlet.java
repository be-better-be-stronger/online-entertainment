package com.poly.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poly.dao.VideoDAO;
import com.poly.dao.impl.VideoDAOImpl;
import com.poly.entity.Video;
import com.poly.exception.AppException;
import com.poly.exception.AppExceptionHandler;
import com.poly.service.VideoService;
import com.poly.service.impl.VideoServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/video-detail")
public class VideoDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(VideoDetailServlet.class);
	
	private final VideoService videoService;

	public VideoDetailServlet() {
		// 👇 Inject VideoDAO vào Service theo đúng constructor
		VideoDAO videoDAO = new VideoDAOImpl();
		this.videoService = new VideoServiceImpl(videoDAO);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String id = req.getParameter("id");
		log.debug("[REQUEST] Nhận yêu cầu xem video với id = {}", id);
		if (id == null || id.isBlank()) {
		    log.warn("[INVALID REQUEST] Thiếu hoặc rỗng id video, chuyển hướng về /home");
		    resp.sendRedirect(req.getContextPath() + "/home");
		    return;
		}
			
		try {
			log.debug("[PROCESS] Tìm video và tăng lượt xem...");
		    Video video = videoService.getVideoById(id);
		    
		    videoService.increaseViews(id); 
			log.info("[VIEW] +1 view cho video '{}'", video.getTitle());
			
		    req.setAttribute("video", video);
		    req.setAttribute("view", "/WEB-INF/views/user/video-detail.jsp");
		    req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
		} catch (AppException ex) {
		    AppExceptionHandler.handle(req, resp, ex);
		}catch (Exception e) {
            AppExceptionHandler.handle(req, resp, e);
        }

	}
}
