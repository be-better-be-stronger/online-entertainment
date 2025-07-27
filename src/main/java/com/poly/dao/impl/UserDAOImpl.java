package com.poly.dao.impl;

import java.util.List;

import com.poly.dao.UserDAO;
import com.poly.entity.User;
import com.poly.exception.AppException;
import com.poly.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UserDAOImpl implements UserDAO{

	@Override
	public User findById(String userId) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			return em.find(User.class, userId);
		}catch (Exception e) {
			throw new AppException(
				"Lỗi khi truy vấn người dùng với ID: " + userId, e
			);
		}
		finally {
			em.close();
		}
	}	

	@Override
	public List<User> findAll() {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			String jpql = "select u from User u";
			return em.createQuery(jpql, User.class).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public void update(User u) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(u);
			tx.commit();
		} catch (Exception e) {
			if(tx.isActive()) tx.rollback();
			throw new AppException("Lỗi khi cập nhật người dùng", e);			
		} finally {
			em.close();
		}
		
	}

}
