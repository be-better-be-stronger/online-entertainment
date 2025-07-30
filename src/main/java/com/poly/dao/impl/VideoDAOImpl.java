package com.poly.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poly.dao.VideoDAO;
import com.poly.dao.base.BaseDAOImpl;
import com.poly.entity.Video;
import com.poly.exception.AppException;
import com.poly.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

public class VideoDAOImpl extends BaseDAOImpl implements VideoDAO {

    @Override
    public List<Video> findTop6ByViews() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
        	System.out.println("----------------------------");
        	System.out.println("MySQL:\nSELECT * FROM videos "
             		+ "ORDER BY views DESC, id DESC "
             		+ "LIMIT 6;");
            String jpql = "SELECT v FROM Video v ORDER BY v.views DESC, v.id DESC";
            return em.createQuery(jpql, Video.class).setMaxResults(6)
            		.getResultList();
            
           
        } finally {
            em.close();
        }
    }
    
    @Override
	public List<Video> findTop6ByLatest() {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			System.out.println("----------------------------");
			System.out.println("MySQL:\nSELECT * FROM videos\r\n"
					+ "WHERE active = true\r\n"
					+ "ORDER BY createdDate DESC\r\n"
					+ "LIMIT 6;\r\n"
					+ "");
			String jpql = "select v from Video v where v.active = true "
					+ "order by v.createdDate DESC";
			return em.createQuery(jpql, Video.class).setMaxResults(6)
					.getResultList();
		} finally {
			em.close();
		}
	}

    @Override
    public List<Video> findAll(int page, int size) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
        	System.out.println("----------------------------");
        	System.out.println("MySQL:\nSELECT * FROM videos\r\n"
        			+ "WHERE active = true\r\n"
        			+ "ORDER BY id\r\n"
        			+ "LIMIT {" + (page*size) + "," + size + "}");
            String jpql = "SELECT v FROM Video v where v.active = true"
            		+ " ORDER BY v.id";
            return em.createQuery(jpql, Video.class)
                     .setFirstResult(page * size)
                     .setMaxResults(size)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
        	System.out.println("----------------------------");
        	System.out.println("MySQL:\nSELECT COUNT(*) FROM videos;");
            String jpql = "SELECT COUNT(v) FROM Video v";
            return em.createQuery(jpql, Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Video findById(String id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {            
            log.debug("[DAO] Tìm video với ID = {}", id);
            return em.find(Video.class, id);
        } catch (PersistenceException e) {
            log.error("[DAO ERROR] Lỗi truy vấn DB khi tìm video: {}", id);
            log.error("Lỗi lớp: {}", e.getClass().getName());
//            log.error("Chi tiết:", e);
            throw new AppException("Lỗi truy vấn DB với video ID: " + id);
        } catch (Exception e) {
            log.error("[DAO ERROR] Lỗi không xác định khi tìm video: {}", id);
            log.error("Lỗi lớp: {}", e.getClass().getName());
//            log.error("Chi tiết:", e);
            throw new AppException("Lỗi không xác định khi tìm video ID: " + id, e);
        } finally {
            em.close();
        }
    }


    @Override
    public void create(Video video) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(video);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Video video) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(video);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(String id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Video video = em.find(Video.class, id);
            if (video != null) {
                em.remove(video);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

	
}
