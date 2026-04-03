package com.poly.dao.impl;

import com.poly.dao.ShareDAO;
import com.poly.entity.Share;
import com.poly.exception.AppException;
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

	@Override
	public int countByVideoId(String videoId) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			String jpql = "SELECT COUNT(s) FROM Share s WHERE s.video.id = :videoId";
		    return em.createQuery(jpql, Long.class)
		             .setParameter("videoId", videoId)
		             .getSingleResult()
		             .intValue();
		} catch (Exception e) {
			throw new AppException("Không thể đếm số lượt share của mỗi video", e);
		} finally {
			em.close();
		}
	}
}
