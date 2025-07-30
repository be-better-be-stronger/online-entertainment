package com.poly.dao.base;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poly.utils.JpaUtil;

import jakarta.persistence.EntityManager;

public abstract class BaseDAOImpl {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * Ghi log lỗi xảy ra trong tầng DAO với thông tin chi tiết bao gồm hành động, 
	 * tham số liên quan và loại exception.
	 *
	 * @param action  Mô tả hành động đang thực hiện (ví dụ: "Tìm video theo ID", "Cập nhật user").
	 * @param e       Exception xảy ra trong quá trình thực hiện hành động.
	 * @param params  Các tham số liên quan đến hành động để giúp dễ truy vết dữ liệu đầu vào.
	 */
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
