package com.poly.dao.impl;

import java.util.List;

import com.poly.dao.VideoDAO;
import com.poly.dao.base.BaseDAOImpl;
import com.poly.entity.Video;
import com.poly.exception.AppException;
import com.poly.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

public class VideoDAOImpl extends BaseDAOImpl implements VideoDAO {

    @Override
    public List<Video> findTop6ByViews() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
        	log.debug("[DAO] Bắt đầu truy vấn top 6 video có lượt xem cao nhất");
            String jpql = "SELECT v FROM Video v ORDER BY v.views DESC, v.id DESC";
            return em.createQuery(jpql, Video.class).setMaxResults(6)
            		.getResultList();           
        } catch (Exception e) {
			logDaoError("lấy top 6 video có lượt xem cao nhất", e);
			throw new AppException("Không thể truy vấn top 6 video phổ biến", e);
		} finally {
            em.close();
        }
    }
    
    @Override
	public List<Video> findTop6ByLatest() {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			log.debug("[DAO] Bắt đầu truy vấn top 6 video mới nhất");
			String jpql = "select v from Video v where v.active = true "
					+ "order by v.createdDate DESC";
			return em.createQuery(jpql, Video.class).setMaxResults(6)
					.getResultList();
		}catch (Exception e) {
			logDaoError("lấy top 6 video mới nhất", e);
			throw new AppException("Không thể truy vấn top 6 video mới nhất", e);
		} finally {
			em.close();
		}
	}

    @Override
    public List<Video> findAll(int page, int size) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
        	log.debug("[DAO] Truy vấn video phân trang - page={}, size={}", page, size);
            String jpql = "SELECT v FROM Video v where v.active = true"
            		+ " ORDER BY v.id";
            return em.createQuery(jpql, Video.class)
                     .setFirstResult(page * size)
                     .setMaxResults(size)
                     .getResultList();
        } catch (Exception e) {
			logDaoError("phân trang danh sách video", e, page, size);
			throw new AppException("Không thể truy vấn danh sách video theo trang", e);
		} finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
        	log.debug("[DAO] Đếm tổng số video trong hệ thống");
        	String jpql = "SELECT COUNT(v) FROM Video v";
            return em.createQuery(jpql, Long.class).getSingleResult();
        } catch (Exception e) {
			logDaoError("đếm tổng số video", e);
			throw new AppException("Không thể đếm tổng số video", e);
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
            logDaoError("truy vấn DB khi tìm video", e, id);
            throw new AppException("Lỗi truy vấn DB với video ID: " + id);
        } catch (Exception e) {
	        logDaoError("không xác định khi tìm video ID: ", e, id);
            throw new AppException("Lỗi không xác định khi tìm video ID: " + id, e);
        } finally {
            em.close();
        }
    }


    @Override
    public void create(Video video) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
        	log.debug("[DAO] Tạo video mới - ID: {}", video.getId());
            trans.begin();
            em.persist(video);
            trans.commit();
        } catch (Exception e) {
            if(trans.isActive()) trans.rollback();
            logDaoError("thêm video mới", e, video.getId());
            throw new AppException("Không thể thêm video mới", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Video video) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
        	log.debug("[DAO] Cập nhật video - ID: {}", video.getId());
            trans.begin();
            em.merge(video);
            trans.commit();
        } catch (Exception e) {
            if(trans.isActive()) trans.rollback();
            logDaoError("cập nhật video", e, video.getId());
            throw new AppException("Không thể cập nhật video", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(String id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
        	log.debug("[DAO] Xóa video - ID: {}", id);
            trans.begin();
            Video video = em.find(Video.class, id);
            if (video != null) {
                em.remove(video);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            logDaoError("xóa video", e, id);
            throw new AppException("Không thể xóa video", e);
        } finally {
            em.close();
        }
    }

	
}
