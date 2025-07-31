package com.poly.service.impl;

import com.poly.dao.ShareDAO;
import com.poly.entity.Share;
import com.poly.service.ShareService;

public class ShareServiceImpl implements ShareService{
	
	private final ShareDAO shareDAO;
	
	public ShareServiceImpl(ShareDAO shareDAO) {
		this.shareDAO = shareDAO;
	}
	
	@Override
	public void create(Share share) {
		shareDAO.create(share);
	}
	

}
