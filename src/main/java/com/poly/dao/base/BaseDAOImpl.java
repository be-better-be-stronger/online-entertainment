package com.poly.dao.base;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poly.utils.JpaUtil;

import jakarta.persistence.EntityManager;

public abstract class BaseDAOImpl {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	protected  void logDaoError(String action, Exception e, Object... params) {       
        log.error("❌ Lỗi [{}] - Params: {}", action, params, e);
        log.error("Lỗi lớp: {}", e.getClass().getName());
//        log.error("Chi tiết:", e);
    }
	
	// Helper để lấy EntityManager
    protected EntityManager getEntityManager() {
        return JpaUtil.getEntityManager();
    }
}
