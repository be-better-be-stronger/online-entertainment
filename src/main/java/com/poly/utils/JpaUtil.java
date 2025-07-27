package com.poly.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory factory;

    static {
        try {
            factory = Persistence.createEntityManagerFactory("OE"); // Tên PU trong persistence.xml
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
}
