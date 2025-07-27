package com.poly.service.impl;

import java.util.List;

import com.poly.dao.VideoDAO;
import com.poly.dao.impl.VideoDAOImpl;
import com.poly.entity.Video;
import com.poly.exception.AppException;
import com.poly.service.VideoService;

public class VideoServiceImpl implements VideoService{
	private VideoDAO videoDAO = new VideoDAOImpl();

	@Override
	public List<Video> getTop6PopularVideos() {
		return videoDAO.findTop6ByViews();
	}

	@Override
	public List<Video> getTop6LatestVideos() {
		return videoDAO.findTop6ByLatest();
	}

	@Override
	public long getTotalVideos() {
		return videoDAO.count();
	}

	@Override
	public List<Video> getPage(int page, int size) {
		return videoDAO.findAll(page, size);
	}

	@Override
	public Video getVideoById(String id) {
		Video video = videoDAO.findById(id);
		if (video == null) {
			throw new AppException("Không tìm thấy video với ID: " + id);
		}
		return video;
	}

}
