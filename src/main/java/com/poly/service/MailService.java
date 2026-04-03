package com.poly.service;

import jakarta.mail.MessagingException;

public interface MailService {
	/**
	 * Gửi email với nội dung HTML.
	 * 
	 * @param to địa chỉ email người nhận
	 * @param subject tiêu đề email
	 * @param htmlContent nội dung email dưới dạng HTML
	 * @throws MessagingException nếu có lỗi xảy ra khi gửi email
	 */
	void send(String to, String subject, String htmlContent) throws MessagingException;

	/**
	 * Gửi email chào mừng đến người dùng mới.
	 * 
	 * @param email địa chỉ email của người dùng
	 * @param fullname tên đầy đủ của người dùng
	 */
	void sendWelcomeMail(String email, String fullname);

	/**
	 * Gửi mã xác minh đến email của người dùng.
	 * 
	 * @param email địa chỉ email của người dùng
	 * @param fullname tên đầy đủ của người dùng
	 * @param code mã xác minh
	 */
	void sendVerifycationCode(String email, String fullname, String code);
}
