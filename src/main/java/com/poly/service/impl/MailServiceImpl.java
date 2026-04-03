package com.poly.service.impl;


import java.util.Properties;

import com.poly.exception.AppException;
import com.poly.service.MailService;
import com.poly.utils.MailConfigLoader;

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
	
	public MailServiceImpl() {
		this.username = MailConfigLoader.getUsername();
		this.appPassword = MailConfigLoader.getPassword();
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
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);
		message.setContent(htmlContent, "text/html; charset=UTF-8");
		
		Transport.send(message);
	}
	@Override
	public void sendWelcomeMail(String email, String fullname) {
		String subject = "Chào mừng đến với Online Entertaiment!";
		String body = "Xin chào " + fullname 
								+ ". Cảm ơn bạn đã đăng ký tài khoản tại Online Entertainment. "
						        + "Chúc bạn có trải nghiệm tuyệt vời!"
						        + "Trân trọng, "
						        + "Đội ngũ OE";
		try {
			send(email, subject, body);
		} catch (MessagingException e) {
			throw new AppException("Không gửi được email", e);
		}		
	}
	@Override
	public void sendVerifycationCode(String email, String fullname, String code) {
		String subject = "Mã xác thực đăng ký tài khoản";
	    String body = String.format("""
	        <p>Chào %s,</p>
	        <p>Mã xác thực đăng ký tài khoản của bạn là: <strong>%s</strong></p>
	        <p>Vui lòng nhập mã này vào trang xác thực để hoàn tất đăng ký.</p>
	        """, fullname, code);
	    try {
	    	send(email, subject, body);
		} catch (Exception e) {
			throw new AppException("Không gửi được email", e);
		}		
	}
	

}
