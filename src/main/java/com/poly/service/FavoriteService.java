/**
 * Service interface for managing user's favorite videos.
 * <p>
 * Provides methods to check, toggle, and retrieve favorite videos for users,
 * as well as counting likes for both users and videos.
 * </p>
 *
 * <ul>
 *   <li>{@link #isVideoLikedByUser(String, String)}: Checks if a video is liked by a user.</li>
 *   <li>{@link #toggleLike(String, String)}: Toggles the like status of a video for a user.</li>
 *   <li>{@link #findFavoritesByUser(String, int, int)}: Retrieves a paginated list of a user's favorite videos.</li>
 *   <li>{@link #countFavoritesByUser(String)}: Counts the number of favorite videos for a user.</li>
 *   <li>{@link #countByVideoId(String)}: Counts the number of likes for a specific video.</li>
 * </ul>
 *
 * @author Thanh
 */
package com.poly.service;

import java.util.List;
import java.util.Map;

import com.poly.dto.response.FavoritesResponse;
import com.poly.entity.User;

public interface FavoriteService {
	
	/**
	 * Kiểm tra xem video có được người dùng thích hay không.
	 * 
	 * @param userId ID của người dùng
	 * @param videoId ID của video
	 * @return true nếu video được thích bởi người dùng, ngược lại false
	 */
	boolean isVideoLikedByUser(String userId, String videoId);
	
	/**
	 * Chuyển đổi trạng thái thích của video bởi người dùng.
	 * 
	 * @param userId ID của người dùng
	 * @param videoId ID của video
	 * @return true nếu trạng thái thích được bật, ngược lại false
	 */
	boolean toggleLike(String userId, String videoId);
	
	/**
	 * Tìm danh sách video yêu thích của người dùng với phân trang.
	 * 
	 * @param người dùng hiện tại
	 * @param page số trang cần lấy
	 * @param size số lượng video trên mỗi trang
	 * @return danh sách video yêu thích của người dùng
	 */
	FavoritesResponse findFavoritesByUser(User currentUser, int page, int size);
	
	/**
	 * Đếm số lượng video yêu thích của người dùng.
	 * 
	 * @param userId ID của người dùng
	 * @return số lượng video yêu thích
	 */
	long countFavoritesByUser(String userId);
	
	/**
	 * Đếm số lượng lượt thích của một video.
	 * 
	 * @param videoId ID của video
	 * @return số lượng lượt thích của video
	 */
	int countByVideoId(String videoId);
	
	Map<String, Boolean> getLikedMap(String userId, List<String> videoIds);
	
	Map<String, Integer> countByVideoIds(List<String> videoIds);
}
