package com.poly.controller;

import java.io.IOException;

import java.util.List;

import com.poly.dao.VideoDAO;
import com.poly.dao.impl.VideoDAOImpl;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.FavoriteService;
import com.poly.service.VideoService;
import com.poly.service.impl.FavoriteServiceImpl;
import com.poly.service.impl.VideoServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class HomeServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final FavoriteService favoriteService = new FavoriteServiceImpl();
	
	private final VideoService videoService;

	public HomeServlet() {
		// 👇 Inject VideoDAO vào Service theo đúng constructor
		VideoDAO videoDAO = new VideoDAOImpl();
		this.videoService = new VideoServiceImpl(videoDAO);
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int size = 6;
	    int page = 0;

	    try {
	        String pageParam = req.getParameter("page");
	        if(pageParam != null) {
	        	page = Integer.parseInt(pageParam);
	        	if (page < 0) {
					page = 0;
				}
	        }
	        System.out.println("page = " + (page + 1));
	    } catch (NumberFormatException e) {
	        page = 0;
	        System.out.println("lỗi phân trang");
	    }
	    
	    
	            

	    // Dữ liệu phân trang
	    
	    long totalItems = videoService.getTotalVideos();
	    int totalPages = (int) Math.ceil((double) totalItems / size);
	    
	    int visiblePages = 9;
	    int startPage = Math.max(0, page - visiblePages / 2);
	    int endPage = Math.min(totalPages - 1, startPage + visiblePages - 1);

	    // Nếu bị hụt thì lùi startPage lại cho đủ visiblePages
	    if (endPage - startPage < visiblePages - 1) {
	        startPage = Math.max(0, endPage - visiblePages + 1);
	    }

	    
		
		// Gọi từ Service
		List<Video> popularVideos = videoService.getTop6PopularVideos();
		List<Video> newVideos = videoService.getTop6LatestVideos();
		List<Video> pagedVideos = videoService.getPage(page, size);
		
		User currentUser = (User) req.getSession().getAttribute("currentUser");
		if(currentUser != null) {
			for(Video v : popularVideos) {
				boolean liked = favoriteService.isVideoLikedByUser(currentUser.getId(), v.getId());
				v.setLiked(liked);
			}
			
			for(Video v : newVideos) {
				boolean liked = favoriteService.isVideoLikedByUser(currentUser.getId(), v.getId());
				v.setLiked(liked);
			}
			
			for(Video v : pagedVideos) {
				boolean liked = favoriteService.isVideoLikedByUser(currentUser.getId(), v.getId());
				v.setLiked(liked);
			}
		}
		
		System.out.println("catalina.base = " + System.getProperty("catalina.base"));

		
		// Gửi dữ liệu xuống view
		req.setAttribute("popularVideos", popularVideos);
		req.setAttribute("newVideos", newVideos);
		req.setAttribute("videos", pagedVideos);
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPages", totalPages);	
		req.setAttribute("startPage", startPage);
	    req.setAttribute("endPage", endPage);
	    
		// View layout
		req.setAttribute("view", "/WEB-INF/views/home.jsp");
		req.getRequestDispatcher("/WEB-INF/layout.jsp")
			.forward(req, resp);
	}
}
