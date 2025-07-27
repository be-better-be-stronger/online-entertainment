package com.poly.dao;

import com.poly.entity.Favorite;

public interface FavoriteDAO {

	Favorite findByUserAndVideo(String userId, String videoId);

	void delete(Long id);

	void insert(Favorite newFav);

}
