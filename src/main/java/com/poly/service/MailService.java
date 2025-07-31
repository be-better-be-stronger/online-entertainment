package com.poly.service;

import jakarta.mail.MessagingException;

public interface MailService {
	void send(String to, String subject, String htmlContent) 
			throws MessagingException;
}
