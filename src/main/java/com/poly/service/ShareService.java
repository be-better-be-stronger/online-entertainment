package com.poly.service;

import java.util.List;
import java.util.Map;

import com.poly.entity.Share;

public interface ShareService {
	/**
	 * Tạo một chia sẻ mới.
	 * 
	 * @param share đối tượng chia sẻ cần tạo
	 */
	void create(Share share);

	/**
	 * Đếm số lượng chia sẻ của một video.
	 * 
	 * @param videoId ID của video
	 * @return số lượng chia sẻ của video
	 */
	int countByVideoId(String videoId);
	
	Map<String, Integer> countByVideoIds(List<String> videoIds);
}
