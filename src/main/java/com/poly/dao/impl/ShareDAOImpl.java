package com.poly.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.poly.dao.ShareDAO;
import com.poly.entity.Share;
import com.poly.exception.AppException;
import com.poly.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Tuple;

public class ShareDAOImpl implements ShareDAO {
	

	@Override
	public void create(Share share) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.persist(share);
			trans.commit();
		} catch (Exception e) {
			if (trans.isActive())
				trans.rollback();
		} finally {
			em.close();
		}
	}

	@Override
	public int countByVideoId(String videoId) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			String jpql = "SELECT COUNT(s) FROM Share s WHERE s.video.id = :videoId";
			return em.createQuery(jpql, Long.class).setParameter("videoId", videoId).getSingleResult().intValue();
		} catch (Exception e) {
			throw new AppException("Không thể đếm số lượt share của mỗi video", e);
		} finally {
			em.close();
		}
	}

	@Override
	public Map<String, Long> countByVideoIds(List<String> videoIds) {
		
		EntityManager em = JpaUtil.getEntityManager();
//		GROUP BY + IN = batch query chuẩn production
		String jpql = """
				    SELECT s.video.id, COUNT(s)
				    FROM Share s
				    WHERE s.video.id IN :videoIds
				    GROUP BY s.video.id
				""";

		List<Tuple> result = em.createQuery(jpql, Tuple.class)
				.setParameter("videoIds", videoIds)
				.getResultList();

		Map<String, Long> map = new HashMap<>();

		for (Tuple t : result) {
			String videoId = t.get(0, String.class);
			Long count = t.get(1, Long.class);
			map.put(videoId, count);
		}
		
		em.close();

		return map;
	}
}
