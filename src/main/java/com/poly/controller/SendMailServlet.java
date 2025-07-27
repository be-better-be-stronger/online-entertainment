package com.poly.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.IOException;
import java.util.Properties;

@WebServlet("/send-mail")
public class SendMailServlet extends HttpServlet {

    // ⚠️ Cấu hình email & app password ở đây
    private static final String USERNAME = "dangquocthanh.la@gmail.com";
    private static final String APP_PASSWORD = "vbzs fngp smhx jcoy"; // App password 16 ký tự

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String to = req.getParameter("to");
        String subject = req.getParameter("subject");
        String body = req.getParameter("body");

        try {
            sendEmail(to, subject, body);
            req.setAttribute("message", "Gửi email thành công!");
        } catch (Exception e) {
            req.setAttribute("message", "Lỗi gửi email: " + e.getMessage());
        }

        req.getRequestDispatcher("/send-mail.jsp").forward(req, resp);
    }

    private void sendEmail(String to, String subject, String body) throws MessagingException {
        //Cấu hình kết nối SMTP của Gmail:
    	Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); //Bắt buộc xác thực (auth)
        props.put("mail.smtp.starttls.enable", "true"); // Kích hoạt TLS bảo mật
        //smtp.gmail.com, cổng 587: chuẩn Gmail
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        
        //Tạo session với Gmail và xác thực bằng tài khoản gửi
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, APP_PASSWORD);
            }
        });

        //Tạo đối tượng Message:
        Message message = new MimeMessage(session);
        //Gửi từ địa chỉ USERNAME: "dangquocthanh.la@gmail.com";
        message.setFrom(new InternetAddress(USERNAME));
        //Gửi đến người nhận
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        //Đặt tiêu đề và nội dung
        message.setSubject(subject);
        message.setText(body);

        //Gửi email thực sự qua SMTP
        Transport.send(message);
    }
}
