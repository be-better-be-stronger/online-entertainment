package com.poly.controller.admin;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poly.entity.User;
import com.poly.exception.AppExceptionHandler;
import com.poly.service.UserService;
import com.poly.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminUserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(AdminUserServlet.class);

	private final UserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("[GET] /admin/users - Bắt đầu xử lý");
		try {
			String keyword = req.getParameter("keyword");
			String pageParam = req.getParameter("page");
			String roleParam = req.getParameter("role");
			log.debug("[GET] Tham số nhận được: keyword={}, pageParam={}, roleParam={}", keyword, pageParam, roleParam);

			int page = 0, size = 10;
			Boolean isAdmin = null;

			log.debug("Phân tích role");
			if ("admin".equalsIgnoreCase(roleParam)) isAdmin = true;
			else if ("user".equalsIgnoreCase(roleParam)) isAdmin = false;

			try {
				if (pageParam != null) {
					page = Integer.parseInt(pageParam);
					if (page < 0) {
						log.warn("[GET] Page < 0. Reset về 0");
						page = 0;
					}
				}
			} catch (NumberFormatException e) {
				log.warn("[GET] Tham số page không hợp lệ: {}. Reset về 0", pageParam);
				page = 0; 
			}

			boolean hasKeyword = keyword != null && !keyword.isBlank();
			if(hasKeyword) keyword = keyword.trim();

			int totalUsers = hasKeyword ? userService.countByNameOrEmailAndRole(isAdmin, keyword) : userService.countByRole(isAdmin);
			int totalPages = (int) Math.ceil(totalUsers/(double) size);
			
			if (totalPages == 0) page = 0;
	        else if (page >= totalPages) page = totalPages - 1; 
			
			List<User> users = hasKeyword ? userService.findPageByNameOrEmailAndRole(isAdmin, keyword, page, size)
																		: userService.findPageByRole(isAdmin, page, size);
			log.debug("[GET] role={}, keyword='{}', page={}/{}, size={}, totalUsers={}",
	                isAdmin, keyword, page, page, size, totalUsers);
			

			req.setAttribute("users", users);
			req.setAttribute("keyword", keyword);
			req.setAttribute("currentPage", page);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("view", "/WEB-INF/views/admin/user-management.jsp");
			req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);

			log.debug("[GET] /admin/users done, returned {} users", users.size());
		} catch (Exception e) {
			log.error("[GET] Lỗi xử lý /admin/users", e);
			AppExceptionHandler.handle(req, resp, e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("[POST] /admin/users - Bắt đầu xử lý");

		String action = req.getParameter("action");
		User currentUser = (User) req.getSession().getAttribute("currentUser");
		
		log.debug("[POST] Tham số nhận được: action={}, userId={}", action, req.getParameter("id"));

		if ("delete".equals(action)) {
			String targetUserId = req.getParameter("id");
			try {
				userService.deleteUser(currentUser, targetUserId);
				log.info("[POST] Xóa thành công user id={}", targetUserId);
				req.getSession().setAttribute("message", "Đã xoá user: " + targetUserId);
				resp.sendRedirect(req.getContextPath() + "/admin/users");
			} catch (SecurityException e) {
				log.warn("[POST] Không đủ quyền để xóa user id={}", req.getParameter("id"), e);
				resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (Exception e) {
				log.error("[POST] Lỗi xử lý action={}", action, e);
				AppExceptionHandler.handle(req, resp, e);
			}
		} else {
			log.warn("[POST] Action không hợp lệ: {}", action);
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
