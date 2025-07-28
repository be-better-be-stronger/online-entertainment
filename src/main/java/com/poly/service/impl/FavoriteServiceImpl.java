package com.poly.service.impl;

import java.util.Date;

import com.poly.dao.FavoriteDAO;
import com.poly.dao.UserDAO;
import com.poly.dao.VideoDAO;
import com.poly.dao.impl.FavoriteDAOImpl;
import com.poly.dao.impl.UserDAOImpl;
import com.poly.dao.impl.VideoDAOImpl;
import com.poly.entity.Favorite;
import com.poly.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService{
	private final FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
	private final UserDAO userDAO = new UserDAOImpl();
	private final VideoDAO videoDAO = new VideoDAOImpl();
	
	@Override
	public boolean toggleLike(String userId, String videoId) {
		System.out.println("[TOGGLE] Đang kiểm tra trạng thái yêu thích: user=" + userId + ", video=" + videoId);
		Favorite fav = favoriteDAO.findByUserAndVideo(userId, videoId);
	    if (fav != null) {
	    	 System.out.println("[TOGGLE] Đã yêu thích rồi, đang thực hiện dislike.");
	        favoriteDAO.delete(fav.getId());
	        return false;
	    } else {
	    	System.out.println("[TOGGLE] Chưa yêu thích, đang thực hiện insert.");
	        Favorite newFav = new Favorite();
	        newFav.setUser(userDAO.findById(userId));
	        newFav.setVideo(videoDAO.findById(videoId));
	        newFav.setLikeDate(new Date());
	        favoriteDAO.insert(newFav);
	        System.out.println("[TOGGLE] Xử lý xong.");
	        return true;
	    }
	}

	@Override
	public boolean isVideoLikedByUser(String userId, String videoId) {
		return favoriteDAO.findByUserAndVideo(userId, videoId) != null;
	}

}
