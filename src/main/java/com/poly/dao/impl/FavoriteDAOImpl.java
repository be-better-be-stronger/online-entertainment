package com.poly.dao.impl;

import com.poly.dao.FavoriteDAO;
import com.poly.entity.Favorite;
import com.poly.exception.AppException;
import com.poly.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;


public class FavoriteDAOImpl implements FavoriteDAO{

	@Override
	public Favorite findByUserAndVideo(String userId, String videoId) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			String jpql = "select f from Favorite f where "
					+ "f.user.id = :uid and "
					+ "f.video.id = :vid";
//			TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
//			query.setParameter("uid", userId);
//			query.setParameter("vid", videoId);
//			return query.getSingleResult();
			return em.createQuery(jpql, Favorite.class)
				.setParameter("uid", userId)
				.setParameter("vid", videoId)
				.getSingleResult();
		}catch (NoResultException e) {
			return null;		
		}finally {
			em.close();
		}
	}

	@Override
	public void delete(Long id) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction trans = em.getTransaction();
	    try {
	        trans.begin();
	        Favorite favorite = em.find(Favorite.class, id);
	        if (favorite != null) {
	            em.remove(favorite);	            
	        }
	        trans.commit();
	    } catch (Exception e) {
	        trans.rollback();
	        throw new AppException("Lỗi khi delete Favorite", e);
	    } finally {
	        em.close();
	    }
		
	}

	@Override
	public void insert(Favorite newFav) {
		EntityManager em = JpaUtil.getEntityManager();
	    try {
	    	em.getTransaction().begin();
	        em.persist(newFav);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        e.printStackTrace();
	        throw new AppException("Lỗi khi insert Favorite", e);
	    } finally {
	        em.close();
	    }
		
	}

}
