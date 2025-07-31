package com.poly.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.poly.exception.AppException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory factory;

    static {
        try {
        	// Phát hiện file config phù hợp
            String configFile = detectConfigFile();
            
         // Load cấu hình từ file properties
            Properties props = new Properties();
            try (InputStream input = JpaUtil.class.getClassLoader().getResourceAsStream(configFile)) {
                if (input == null) {
                    throw new AppException("Không tìm thấy file cấu hình: " + configFile);
                }
                props.load(input);
            }
            
         // Lấy tên persistence unit từ file config
            String persistenceUnit = props.getProperty("persistence.unit.name");
            
         // Tạo các thuộc tính JPA động
            Properties jpaProps = new Properties();
            jpaProps.put("jakarta.persistence.jdbc.user", props.getProperty("jdbc.user"));
            jpaProps.put("jakarta.persistence.jdbc.password", props.getProperty("jdbc.password"));
                   
            factory = Persistence.createEntityManagerFactory(persistenceUnit, jpaProps); 
        } catch (IOException e) {
            throw new AppException("Lỗi khi đọc file cấu hình", e);
        } catch (Exception e) {
            throw new AppException("Lỗi khi khởi tạo EntityManagerFactory", e);
        }
    }

    // Trả về EntityManager mới mỗi lần gọi
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    // Đóng EMF khi ứng dụng shutdown
    public static void close() {
        factory.close();
    }
    
    private static String detectConfigFile() {
        ClassLoader cl = JpaUtil.class.getClassLoader();
        if (cl.getResource("config-test.properties") != null) {
            return "config-test.properties"; // Đang chạy test
        }
        return "config.properties"; // Chạy thật
    }
}
