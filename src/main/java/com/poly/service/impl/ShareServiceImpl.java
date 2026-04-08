package com.poly.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.poly.dao.ShareDAO;
import com.poly.dao.impl.ShareDAOImpl;
import com.poly.entity.Share;
import com.poly.service.ShareService;

public class ShareServiceImpl implements ShareService{
	
	private final ShareDAO shareDAO;
	
	public ShareServiceImpl() {
		this.shareDAO = new ShareDAOImpl();
	}
	
	@Override
	public void create(Share share) {
		shareDAO.create(share);
	}

	@Override
	public int countByVideoId(String videoId) {
		return shareDAO.countByVideoId(videoId);
	}

	@Override
	public Map<String, Integer> countByVideoIds(List<String> videoIds) {
		if (videoIds == null || videoIds.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Long> rawMap = shareDAO.countByVideoIds(videoIds);

        Map<String, Integer> result = new HashMap<>();

        for (String videoId : videoIds) {
            result.put(videoId, rawMap.getOrDefault(videoId, 0L).intValue());
        }

        return result;
	}
	

}
