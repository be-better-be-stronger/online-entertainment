package com.poly.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poly.dao.VideoDAO;
import com.poly.dao.impl.VideoDAOImpl;
import com.poly.entity.Video;
import com.poly.exception.AppException;
import com.poly.service.VideoService;


public class VideoServiceImpl implements VideoService{
	
	private static final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);
	private final VideoDAO videoDAO;
	
	public VideoServiceImpl () {
		this.videoDAO = new VideoDAOImpl();
	}

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
	public List<Video> findAllActiveVideosByPage(int page, int size) {
		return videoDAO.findAllActiveVideosByPage(page, size);
	}

	@Override
	public Video findById(String id) {
		Video video = videoDAO.findById(id);
		if (video == null) {
			log.warn("Không tìm thấy video với ID: {}", id);
			throw new AppException("Không tìm thấy video với ID: " + id);
		}
		return video;
	}

	@Override
	public void update(Video video) {
		videoDAO.update(video);		
	}
	
	@Override
	public void increaseViews(String videoId) {
		log.debug("[SERVICE] Tăng lượt xem cho video ID = {}", videoId);
	    videoDAO.increaseViews(videoId);
	}

	@Override
	public void create(Video video) {
		videoDAO.create(video);		
	}

	@Override
	public void delete(String id) {
		videoDAO.delete(id);		
	}

	@Override
	public List<Video> findAllByPage(int page, int size) {
		return videoDAO.findAllByPage(page, size);
	}



}
