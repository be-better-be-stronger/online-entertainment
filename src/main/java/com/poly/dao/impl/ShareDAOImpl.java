package com.poly.dao.impl;

import com.poly.dao.ShareDAO;
import com.poly.entity.Share;
import com.poly.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ShareDAOImpl implements ShareDAO{

	@Override
	public void create(Share share) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.persist(share);
			trans.commit();
		}catch (Exception e) {
			if(trans.isActive()) trans.rollback();
		}finally {
			em.close();
		}		
	}
}
