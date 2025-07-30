package com.poly.dao;

import java.util.List;

import com.poly.entity.Favorite;
import com.poly.entity.Video;

public interface FavoriteDAO {

	Favorite findByUserAndVideo(String userId, String videoId);

	void delete(Long id);

	void insert(Favorite newFav);

	List<Video> findFavoriteVideosByUser(String userId, int page, int size);

	long countFavoritesByUser(String userId);

}
