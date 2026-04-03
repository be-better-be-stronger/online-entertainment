package com.poly.dao.impl;

import com.poly.dao.RememberMeTokenDAO;
import com.poly.entity.RememberMeToken;
import com.poly.exception.AppException;
import com.poly.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class RememberMeTokenDAOImpl implements RememberMeTokenDAO {

    @Override
    public void save(RememberMeToken token) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(token);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new AppException("Lỗi khi lưu remember me token", e);
        } finally {
            em.close();
        }
    }

    @Override
    public RememberMeToken findByUserId(String userId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT r FROM RememberMeToken r WHERE r.userId = :uid";
            TypedQuery<RememberMeToken> query = em.createQuery(jpql, RememberMeToken.class);
            query.setParameter("uid", userId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    

    @Override
    public void deleteTokenByUserId(String userId) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM RememberMeToken t WHERE t.userId = :uid")
	            .setParameter("uid", userId)
	            .executeUpdate();
           tx.commit();
        } catch (Exception e) {
            if(tx.isActive()) tx.rollback();
            throw new AppException("Lỗi khi xóa remember token", e);
        } finally {
            em.close();
        }
    }

	
}
