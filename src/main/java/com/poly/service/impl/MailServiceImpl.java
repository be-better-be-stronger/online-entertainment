package com.poly.service.impl;


import java.util.Properties;

import com.poly.service.MailService;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MailServiceImpl implements MailService{
	private final String username;
	private final String appPassword;
	
	public MailServiceImpl(String username, String password) {
		this.username = username;
		this.appPassword = password;
	}
	@Override
	public void send(String to, String subject, String htmlContent) 
			throws MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props, 
				new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,  appPassword);
			}
		});
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username));
		message.setRecipients(Message.RecipientType.TO, 
				InternetAddress.parse(to));
		message.setSubject(subject);
		message.setContent(htmlContent, "text/html; charset=UTF-8");
		
		Transport.send(message);
	}

}
