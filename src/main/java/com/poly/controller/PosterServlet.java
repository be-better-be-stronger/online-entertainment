package com.poly.controller;

import java.io.IOException;
import java.io.OutputStream;

import com.poly.entity.Video;
import com.poly.service.VideoService;
import com.poly.service.impl.VideoServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class PosterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final VideoService videoService = new VideoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String videoId = req.getParameter("id");
        if (videoId == null || videoId.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID video.");
            return;
        }

        try {
            Video video = videoService.findById(videoId);
            if (video == null || video.getPoster() == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy ảnh poster.");
                return;
            }

            // Thiết lập header cho ảnh (tùy theo định dạng, ở đây mặc định là JPEG)
            resp.setContentType("image/jpeg");
            resp.setContentLength(video.getPoster().length);

            try (OutputStream os = resp.getOutputStream()) {
                os.write(video.getPoster());
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải ảnh.");
        }
    }
}
