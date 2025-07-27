package com.poly.service;

import java.util.List;

import com.poly.entity.Video;

public interface VideoService {	
    List<Video> getTop6PopularVideos();
    List<Video> getTop6LatestVideos();
	long getTotalVideos();
	List<Video> getPage(int page, int size);
	Video getVideoById(String id);
}
