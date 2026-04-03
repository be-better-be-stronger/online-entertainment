package com.poly.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poly.dao.UserDAO;
import com.poly.entity.User;
import com.poly.exception.AppException;
import com.poly.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

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

	@Override
	public User findByEmail(String email) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }catch (Exception e) {
			throw new AppException("Lỗi kết nối", e);
		}finally {
			em.close();
		}
	}

	@Override
	public void insert(User user) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            if(tx.isActive()) tx.rollback();
            throw new AppException("Lỗi khi thêm user", e);
        }finally {
			em.close();
		}		
	}

	@Override
	public boolean delete(String userId) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			User user = em.find(User.class, userId);
			if (user == null) {
	            tx.rollback();
	            return false; // Không tìm thấy user
	        }
			em.remove(user);
			tx.commit();
			return true;
		} catch (Exception e) {
			if(tx.isActive()) tx.rollback();
			throw new AppException("Không thể xóa User có id " + userId, e);		
		}finally {
			em.close();
		}
		
	}

	@Override
	public List<User> findPageByRole(Boolean isAdmin, int page, int size) {
		EntityManager em = JpaUtil.getEntityManager();
	    try {
	        String jpql = "SELECT u FROM User u ORDER BY u.id";
	        if (isAdmin != null) jpql = "SELECT u FROM User u WHERE u.admin = :admin "
	        		+ "ORDER BY u.id";
	        TypedQuery<User> query = em.createQuery(jpql, User.class)
	                 .setFirstResult(page * size)
	                 .setMaxResults(size);
	        if (isAdmin != null) query.setParameter("admin", isAdmin);
	        
	        return query.getResultList();
	    } catch (Exception e) {
	        throw new AppException("Lỗi truy vấn người dùng", e); // bọc lại
	    } finally {
	        em.close();
	    }
	}

	@Override
	public int countByRole(Boolean isAdmin) {
	    EntityManager em = JpaUtil.getEntityManager();
	    try {
	        String jpql = "SELECT COUNT(u) FROM User u";
	        if (isAdmin != null) jpql += " WHERE u.admin = :admin";
	        TypedQuery<Long> query = em.createQuery(jpql, Long.class);	        
	        if (isAdmin != null) query.setParameter("admin", isAdmin);
	        return query.getSingleResult().intValue();
	    } catch (Exception e) {
	        throw new AppException("Lỗi khi đếm số người dùng", e);
	    } finally {
	        em.close();
	    }
	}

	@Override
	public List<User> findPageByNameOrEmailAndRole(Boolean isAdmin,String keyword, int page, int size) {
		EntityManager em = JpaUtil.getEntityManager();
	    try {
	    	String jpql = "SELECT u FROM User u WHERE (u.fullname LIKE :kw OR u.email LIKE :kw)";
	    	if (isAdmin != null) jpql += " AND u.admin = :admin";
	        
	    	TypedQuery<User> query = em.createQuery(jpql, User.class);
	       query .setParameter("kw", "%" + keyword + "%")
	                .setFirstResult(page * size)
	                .setMaxResults(size);
	       if (isAdmin != null) query.setParameter("admin", isAdmin);
	       
	       return query.getResultList();	       
	    } catch (Exception e) {
	        throw new AppException("Lỗi tìm kiếm người dùng theo tên/email", e);
	    } finally {
			em.close();
		}
	}


	@Override
	public int countByNameOrEmailAndRole(Boolean isAdmin, String keyword) {
		EntityManager em = JpaUtil.getEntityManager();
        try {
        	String jpql = "SELECT COUNT(u) FROM User u WHERE (u.fullname LIKE :kw OR u.email LIKE :kw)";
            if (isAdmin != null) jpql += " AND u.admin = :admin";
          
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("kw", "%" + keyword + "%");            
            if (isAdmin != null) query.setParameter("admin", isAdmin);
            
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            throw new AppException("Lỗi khi đếm người dùng theo tên/email", e);
        } finally {
			em.close();
		}
    }
	


}
