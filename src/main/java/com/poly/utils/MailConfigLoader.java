package com.poly.utils;

import java.io.InputStream;
import java.util.Properties;

import com.poly.exception.AppException;

public class MailConfigLoader {
	private static final Properties props = new Properties();
	
	static {
		try (InputStream is = MailConfigLoader.class.getClassLoader()
				.getResourceAsStream("config.properties")) {
			if (is == null) throw new RuntimeException("Khong tìm thấy file config.properties trong classpath");
			props.load(is);
		} catch (Exception e) {
			throw new AppException("Lỗi khi đọc file cấu hình email", e);
		}
	}
	
	public static String getUsername() {
		return props.getProperty("mail.username");
	}
	public static String getPassword() {
		return props.getProperty("mail.password");
	}
}
