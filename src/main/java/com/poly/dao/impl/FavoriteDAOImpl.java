package com.poly.dao.impl;

import java.util.List;



import com.poly.dao.FavoriteDAO;
import com.poly.dao.base.BaseDAOImpl;
import com.poly.entity.Favorite;
import com.poly.entity.Video;
import com.poly.exception.AppException;
import com.poly.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;


public class FavoriteDAOImpl extends BaseDAOImpl implements FavoriteDAO{
	
	
	/**
	 * Truy vấn một bản ghi Favorite dựa vào userId và videoId.
	 *
	 * @param userId ID của người dùng
	 * @param videoId ID của video
	 * @return đối tượng Favorite nếu tồn tại, null nếu không
	 * @throws AppException nếu xảy ra lỗi trong quá trình truy vấn
	 */

	@Override
	public Favorite findByUserAndVideo(String userId, String videoId) {
		EntityManager em = JpaUtil.getEntityManager();
//		chỉ đọc, không cần transaction
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
		}catch (Exception e) {
			logDaoError("Tìm Favorite theo user + video", e, userId, videoId);
			throw new AppException("Không thể truy vấn favorite", e);
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
	            log.debug("Đã xoá favorite: id={}", id);
	        }else {
	        	log.warn("Không tìm thấy favorite với id={} để xoá", id);
			}
	        trans.commit();
	        
	    } catch (Exception e) {
	        if (trans.isActive()) trans.rollback();
	        logDaoError("Xoá Favorite", e, id);
	        throw new AppException("Không thể xóa favorite", e);
	    } finally {
	        em.close();
	    }
		
	}

	@Override
	public void insert(Favorite newFav) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction trans = em.getTransaction();
	    try {
	    	trans.begin();
	        em.persist(newFav);
	        trans.commit();
	        log.debug("Insert favorite thành công: {}", newFav);
	    } catch (Exception e) {
	        if(trans.isActive()) trans.rollback();
	        logDaoError("Insert Favorite", e, newFav);
	        throw new AppException("Không thể insert favorite", e);
	    } finally {
	        em.close();
	    }
		
	}

//	Danh sách video đã thích (có phân trang)
	@Override
	public List<Video> findFavoriteVideosByUser(String userId, int page, int size) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			String jpql = "SELECT f.video FROM Favorite f WHERE f.user.id = :userId "
					+ "ORDER BY f.likeDate DESC";
			return em.createQuery(jpql, Video.class)
					.setParameter("userId", userId)
					.setFirstResult(page*size)
					.setMaxResults(size)
					.getResultList();
		}catch (Exception e) {
			logDaoError("Phân trang video yêu thích", e, userId, page, size);
	        throw new AppException("Không thể truy vấn danh sách video yêu thích", e);
	    }  finally {
			em.close();
		}
		
	}

//	Đếm tổng số video yêu thích
	@Override
	public long countFavoritesByUser(String userId) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			String jpql = "SELECT COUNT(f) FROM Favorite f WHERE f.user.id = :uid";
			return em.createQuery(jpql, Long.class)
								.setParameter("uid", userId)
								.getSingleResult();
		}catch (Exception e) {
			logDaoError("Đếm video yêu thích", e, userId);
			throw new AppException("Không thể đếm video yêu thích", e);
		} finally {
			em.close();
		}
	}


}
