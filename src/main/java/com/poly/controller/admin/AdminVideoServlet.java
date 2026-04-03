package com.poly.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;

import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.exception.AppExceptionHandler;
import com.poly.service.VideoService;
import com.poly.service.impl.VideoServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5MB
public class AdminVideoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final VideoService videoService = new VideoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!isAdmin(req)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            int page = 0;
            int pageSize = 10;
            
            String pageParam = req.getParameter("page");
            if (pageParam != null && pageParam.matches("\\d+")) {
                page = Integer.parseInt(pageParam);
                if(page < 0) page = 0;
            }

            String editId = req.getParameter("editId");
            if (editId != null) {
                Video videoToEdit = videoService.findById(editId);
                req.setAttribute("videoEdit", videoToEdit);
            }

            List<Video> videos = videoService.findAllByPage(page, pageSize);
            long totalItems = videoService.getTotalVideos();
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);

            req.setAttribute("videos", videos);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("view", "/WEB-INF/views/admin/video-management.jsp");
            req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);

        } catch (Exception e) {
            AppExceptionHandler.handle(req, resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!isAdmin(req)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            String action = req.getParameter("action");

            if ("create".equals(action)) {
                Video video = new Video();
                BeanUtils.populate(video, req.getParameterMap());
                video.setId(UUID.randomUUID().toString());
                video.setCreatedDate(new Date());
                video.setPoster(getPosterBytes(req.getPart("poster")));
                video.setActive(req.getParameter("active") != null);
                videoService.create(video);

            } else if ("update".equals(action)) {
                String id = req.getParameter("id");
                Video video = videoService.findById(id);
                if (video != null) {
                    BeanUtils.populate(video, req.getParameterMap());
                    video.setActive(req.getParameter("active") != null);
                    byte[] newPoster = getPosterBytes(req.getPart("poster"));
                    if (newPoster.length > 0) {
                        video.setPoster(newPoster);
                    }
                    videoService.update(video);
                }

            } else if ("delete".equals(action)) {
                String id = req.getParameter("id");
                videoService.delete(id);
            }

            resp.sendRedirect(req.getContextPath() + "/admin/videos");

        } catch (Exception e) {
            AppExceptionHandler.handle(req, resp, e);
        }
    }

    private byte[] getPosterBytes(Part part) throws IOException {
        try (InputStream is = part.getInputStream()) {
            return is.readAllBytes();
        }
    }

    private boolean isAdmin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;
        return currentUser != null && currentUser.getAdmin();
    }
}
