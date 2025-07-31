package com.poly.controller;

import java.io.IOException;
import java.util.Date;

import com.poly.dao.impl.ShareDAOImpl;
import com.poly.dao.impl.VideoDAOImpl;
import com.poly.entity.Share;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.exception.AppExceptionHandler;
import com.poly.service.MailService;
import com.poly.service.ShareService;
import com.poly.service.VideoService;
import com.poly.service.impl.MailServiceImpl;
import com.poly.service.impl.ShareServiceImpl;
import com.poly.service.impl.VideoServiceImpl;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/share")
public class ShareVideoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private final ShareService shareService;
	private final VideoService videoService;
	private final MailService mailService;
	
	public ShareVideoServlet() {
		this(
				new ShareServiceImpl(new ShareDAOImpl()),
				new VideoServiceImpl(new VideoDAOImpl()),
				new MailServiceImpl()
		);
	}
	
	public ShareVideoServlet(ShareService shareService, VideoService videoService, MailService mailService) {
		super();
		this.shareService = shareService;
		this.videoService = videoService;
		this.mailService = mailService;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String videoId = req.getParameter("videoId");
		try {
			Video video = videoService.getVideoById(videoId);
			req.setAttribute("video", video);
			req.setAttribute("view", "/WEB-INF/views/user/share-video.jsp");
	        req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);	        
		} catch (Exception e) {
			AppExceptionHandler.handle(req, resp, e);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User currentUser = (User) req.getSession().getAttribute("currentUser");
		if (currentUser == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		
		String to = req.getParameter("to").trim();
		String videoId = req.getParameter("videoId");
		String message = req.getParameter("message");
		
		try {
			Video video = videoService.getVideoById(videoId);
			String videoUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + 
					req.getContextPath() + "/video-detail?id=" + videoId;
			String subject = "Video được chia sẻ từ Online Entertaiment";
			String content = "<h3>" + video.getTitle() + "</h3>"
                    + "<p>" + message + "</p>"
                    + "<p><a href='" + videoUrl + "'>Xem video</a></p>";
			mailService.send(to, subject, content);
			
			Share share = new Share();
			share.setUser(currentUser);
			share.setVideo(video);
			share.setEmail(to);
			share.setShareDate(new Date());
			
			shareService.create(share);
			
			req.setAttribute("message", "Chia sẻ thành công!");
			
		}catch (MessagingException e) {
			AppExceptionHandler.handle(req, resp, e);
		} catch (Exception e) {
			AppExceptionHandler.handle(req, resp, e);
		}
		
		req.setAttribute("view", "/WEB-INF/views/user/share-video.jsp");
        req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
	}

}
