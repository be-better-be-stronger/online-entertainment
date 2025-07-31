package com.poly.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory factory;

    static {
        try {
        	String unitName = detectTestMode() ? "testOE" : "OE";
            factory = Persistence.createEntityManagerFactory(unitName); 
        } catch (Exception e) {
            System.err.println("Initial EntityManagerFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
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
    
    private static boolean detectTestMode() {
        return System.getProperty("app.env", "prod").equals("test");
    }
}
