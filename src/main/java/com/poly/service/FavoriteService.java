package com.poly.service;

import java.util.List;

import com.poly.entity.Video;

public interface FavoriteService {
	
	boolean isVideoLikedByUser(String userId, String videoId);
	boolean toggleLike(String userId, String videoId);
	// FavoriteService.java
	List<Video> findFavoritesByUser(String userId, int page, int size);
	long countFavoritesByUser(String userId);

}
