package com.poly.service.impl;

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
	

}
