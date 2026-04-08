package com.poly.dao;

import java.util.List;
import java.util.Map;

import com.poly.entity.Share;

public interface ShareDAO {

	void create(Share share);
	
	int countByVideoId(String videoId);

	Map<String, Long> countByVideoIds(List<String> videoIds);
}
