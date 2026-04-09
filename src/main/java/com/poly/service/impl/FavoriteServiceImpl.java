package com.poly.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poly.dao.FavoriteDAO;
import com.poly.dao.UserDAO;
import com.poly.dao.VideoDAO;
import com.poly.dao.impl.FavoriteDAOImpl;
import com.poly.dao.impl.UserDAOImpl;
import com.poly.dao.impl.VideoDAOImpl;
import com.poly.dto.VideoDTO;
import com.poly.dto.mapper.VideoMapper;
import com.poly.dto.response.FavoritesResponse;
import com.poly.entity.Favorite;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.exception.AppException;
import com.poly.service.FavoriteService;
import com.poly.service.ShareService;

public class FavoriteServiceImpl implements FavoriteService{
	
	private static final Logger log = LoggerFactory.getLogger(FavoriteServiceImpl.class);

	private final FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
	private final UserDAO userDAO = new UserDAOImpl();
	private final VideoDAO videoDAO = new VideoDAOImpl();
	private final ShareService shareService = new ShareServiceImpl();
	
	
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
	        favoriteDAO.delete(fav.getId());
	        return false;
	    } else {
	    	try {
	    		log.debug("[TOGGLE] Chưa yêu thích, đang thực hiện insert.");
		        Favorite newFav = new Favorite();
		        User user = userDAO.findById(userId);
		        Video video = videoDAO.findById(videoId);
		        validateEntities(user, video);
		        newFav.setUser(user);
		        newFav.setVideo(video);
		        newFav.setLikeDate(new Date());
		        favoriteDAO.insert(newFav);
		        log.debug("[TOGGLE] Xử lý xong.");
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
	public FavoritesResponse findFavoritesByUser(User currentUser, int page, int size) {
		// 1. lấy dữ liệu
		List<Video> videos = favoriteDAO.findFavoriteVideosByUser(currentUser.getId(), page, size);
		
		//2. lấy videoIds
		List<String> videoIds = videos.stream()
                .map(Video::getId)
                .toList();
		
		// 3. chuẩn bị userId
        String userId = (currentUser != null) ? currentUser.getId() : null;        

        // 4. batch query
        Map<String, Boolean> likedMap = (userId != null)
                ? getLikedMap(userId, videoIds)
                : Collections.emptyMap();
        
        Map<String, Integer> likeCountMap = countByVideoIds(videoIds);
        Map<String, Integer> shareCountMap = shareService.countByVideoIds(videoIds);
		
	    long totalItems = favoriteDAO.countFavoritesByUser(currentUser.getId());
	    
	    int totalPages = (int) Math.ceil((double) totalItems / size);

	    List<VideoDTO> dtos = mapList(videos, likedMap, likeCountMap, shareCountMap);

	    return new FavoritesResponse(dtos, page, totalPages);
	}

	@Override
	public long countFavoritesByUser(String userId) {
		return favoriteDAO.countFavoritesByUser(userId);
	}

	@Override
	public int countByVideoId(String videoId) {
		return favoriteDAO.countByVideoId(videoId);
	}

	@Override
	public Map<String, Boolean> getLikedMap(String userId, List<String> videoIds) {
		// 1. validate
        if (userId == null || videoIds == null || videoIds.isEmpty()) {
            return Collections.emptyMap();
        }

        // 2. query 1 lần duy nhất
        List<String> likedVideoIds = favoriteDAO.findLikedVideoIds(userId, videoIds);

        // 3. convert sang Set để lookup O(1)
        Set<String> likedSet = new HashSet<>(likedVideoIds);

        // 4. build result map
        Map<String, Boolean> result = new HashMap<>();

        for (String videoId : videoIds) {
            result.put(videoId, likedSet.contains(videoId));
        }

        return result;
	}

	@Override
	public Map<String, Integer> countByVideoIds(List<String> videoIds) {
		if(videoIds == null || videoIds.isEmpty())
			return Collections.emptyMap();
		Map<String, Long> rawMap = favoriteDAO.countByVideoIds(videoIds);
		Map<String, Integer> result = new HashMap<>();
		for(String videoId : videoIds) {
			result.put(videoId, rawMap.getOrDefault(videoId, 0L).intValue());
		}
		return result;
	}
	
	private List<VideoDTO> mapList(
            List<Video> videos,
            Map<String, Boolean> likedMap,
            Map<String, Integer> likeCountMap,
            Map<String, Integer> shareCountMap
    ) {
        return videos.stream()
                .map(v -> VideoMapper.toVideoDTO(
                        v,
                        likedMap.getOrDefault(v.getId(), false),
                        likeCountMap.getOrDefault(v.getId(), 0),
                        shareCountMap.getOrDefault(v.getId(), 0)
                ))
                .toList();
    }

}
