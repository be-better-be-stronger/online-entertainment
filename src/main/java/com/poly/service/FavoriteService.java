package com.poly.service;

public interface FavoriteService {
	
	boolean isVideoLikedByUser(String userId, String videoId);
	boolean toggleLike(String userId, String videoId);

}
