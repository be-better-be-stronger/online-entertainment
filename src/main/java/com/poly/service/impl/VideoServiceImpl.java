package com.poly.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poly.dao.VideoDAO;
import com.poly.dao.impl.VideoDAOImpl;
import com.poly.dto.VideoDTO;
import com.poly.dto.mapper.VideoMapper;
import com.poly.dto.response.HomeResponse;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.exception.AppException;
import com.poly.service.FavoriteService;
import com.poly.service.ShareService;
import com.poly.service.VideoService;


public class VideoServiceImpl implements VideoService{
	
	private static final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);
	private final VideoDAO videoDAO;
	private final FavoriteService favoriteService = new FavoriteServiceImpl();
    private final ShareService shareService = new ShareServiceImpl();
	
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

	@Override
	public HomeResponse getHomeData(int page, int size, User currentUser) {
		// 1. Lấy 3 list
        List<Video> popularVideos = getTop6PopularVideos();
        List<Video> newVideos = getTop6LatestVideos();
        List<Video> pagedVideos = findAllActiveVideosByPage(page, size);
        
        // 2. Gộp tất cả video để batch xử lý
        List<Video> allVideos = new ArrayList<>();
        allVideos.addAll(popularVideos);
        allVideos.addAll(newVideos);
        allVideos.addAll(pagedVideos);
        
        // 🔥 3. Lấy videoIds
        List<String> videoIds = allVideos.stream()
                .map(Video::getId)
                .distinct()
                .toList();

        // 4. chuẩn bị userId
        String userId = (currentUser != null) ? currentUser.getId() : null;        

        // 5. batch query
        Map<String, Boolean> likedMap = (userId != null)
                ? favoriteService.getLikedMap(userId, videoIds)
                : Collections.emptyMap();

        Map<String, Integer> likeCountMap = favoriteService.countByVideoIds(videoIds);
        Map<String, Integer> shareCountMap = shareService.countByVideoIds(videoIds);
        
        // 6. map từng list riêng biệt
        List<VideoDTO> popularDTOs = mapList(popularVideos, likedMap, likeCountMap, shareCountMap);
        List<VideoDTO> newDTOs = mapList(newVideos, likedMap, likeCountMap, shareCountMap);
        List<VideoDTO> pagedDTOs = mapList(pagedVideos, likedMap, likeCountMap, shareCountMap);
        
        // 7. pagination (chỉ áp dụng cho paged)	    
	    long totalItems = getTotalVideos();
	    int totalPages = (int) Math.ceil((double) totalItems / size);
	    
	    int visiblePages = 9;
	    int startPage = Math.max(0, page - visiblePages / 2);
	    int endPage = Math.min(totalPages - 1, startPage + visiblePages - 1);

	    // Nếu bị hụt thì lùi startPage lại cho đủ visiblePages
	    if (endPage - startPage < visiblePages - 1) {
	        startPage = Math.max(0, endPage - visiblePages + 1);
	    }

        // 8. build response
        HomeResponse res = new HomeResponse();
        
        res.setPopularVideos(popularDTOs);
        res.setNewVideos(newDTOs);
        res.setPagedVideos(pagedDTOs);
        
        res.setCurrentPage(page);
        res.setTotalPages(totalPages);
        res.setStartPage(startPage);
        res.setEndPage(endPage);

        return res;
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
