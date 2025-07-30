package com.poly.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poly.dao.FavoriteDAO;
import com.poly.dao.UserDAO;
import com.poly.dao.VideoDAO;
import com.poly.dao.impl.FavoriteDAOImpl;
import com.poly.dao.impl.UserDAOImpl;
import com.poly.dao.impl.VideoDAOImpl;
import com.poly.entity.Favorite;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.exception.AppException;
import com.poly.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService{
	
	private static final Logger log = LoggerFactory.getLogger(FavoriteServiceImpl.class);

	private final FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
	private final UserDAO userDAO = new UserDAOImpl();
	private final VideoDAO videoDAO = new VideoDAOImpl();
	
	
	
	
	/**
	 * Thay đổi trạng thái yêu thích của video cho người dùng. Nếu đã like thì bỏ like, nếu chưa thì thêm like.
	 *
	 * @param userId ID của người dùng
	 * @param videoId ID của video
	 * @return true nếu sau khi thực hiện thì video được like, false nếu bị unlike
	 * @throws AppException nếu user hoặc video không tồn tại, hoặc lỗi hệ thống khi insert
	 */
	@Override
	public boolean toggleLike(String userId, String videoId) {
		log.debug("[TOGGLE] Đang kiểm tra trạng thái yêu thích: user={}, video={}", userId, videoId);
//		System.out.println("[TOGGLE] Đang kiểm tra trạng thái yêu thích: user=" + userId + ", video=" + videoId);
		Favorite fav = favoriteDAO.findByUserAndVideo(userId, videoId);
	    if (fav != null) {
	    	log.debug("[TOGGLE] Đã yêu thích rồi, đang thực hiện dislike.");
//	    	 System.out.println("[TOGGLE] Đã yêu thích rồi, đang thực hiện dislike.");
	        favoriteDAO.delete(fav.getId());
	        return false;
	    } else {
	    	try {
	    		log.debug("[TOGGLE] Chưa yêu thích, đang thực hiện insert.");
//		    	System.out.println("[TOGGLE] Chưa yêu thích, đang thực hiện insert.");
		        Favorite newFav = new Favorite();
		        User user = userDAO.findById(userId);
		        Video video = videoDAO.findById(videoId);
		        validateEntities(user, video);
		        newFav.setUser(user);
		        newFav.setVideo(video);
		        newFav.setLikeDate(new Date());
		        favoriteDAO.insert(newFav);
		        log.debug("[TOGGLE] Xử lý xong.");
//		        System.out.println("[TOGGLE] Xử lý xong.");
		        return true;
			} catch (AppException e) {
			    logInsertError(userId, videoId, e);
			    throw e;
			} catch (Exception e) {
			    logInsertError(userId, videoId, e);
			    throw new AppException("Lỗi hệ thống khi insert Favorite", e);
			}

	    }
	}

	@Override
	public boolean isVideoLikedByUser(String userId, String videoId) {
		return favoriteDAO.findByUserAndVideo(userId, videoId) != null;
	}
	
	private void validateEntities(User user, Video video) {
		if(user == null) throw new AppException("User không tồn tại");
		if(video == null) throw new AppException("Video không tồn tại");
	}
	
	private void logInsertError(String userId, String videoId, Exception e) {
		log.error("Lỗi khi insert Favorite: user={}, video={}", userId, videoId, e);
	}

	@Override
	public List<Video> findFavoritesByUser(String userId, int page, int size) {
		return favoriteDAO.findFavoriteVideosByUser(userId, page, size);
	}

	@Override
	public long countFavoritesByUser(String userId) {
		return favoriteDAO.countFavoritesByUser(userId);
	}

}
