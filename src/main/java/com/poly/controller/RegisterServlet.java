package com.poly.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;

import com.poly.entity.User;
import com.poly.exception.AppExceptionHandler;
import com.poly.service.MailService;
import com.poly.service.UserService;
import com.poly.service.impl.MailServiceImpl;
import com.poly.service.impl.UserServiceImpl;
import com.poly.utils.HashUtil;
import com.poly.utils.VerificationCodeUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
 	private static final long serialVersionUID = 1L;
 	
 	private final UserService userService;
 	private final MailService mailService;
 	
 	public RegisterServlet() {
 		this(new UserServiceImpl(), new MailServiceImpl());
 	}
 	
	public RegisterServlet(UserService userService, MailService mailService) {
		super();
		this.userService = userService;
		this.mailService = mailService;
	}

 	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.setAttribute("view", "/WEB-INF/views/user/register.jsp");
        req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			String 	email = req.getParameter("email");
			if(userService.findByEmail(email) != null) {
				req.setAttribute("error", "Email đã được sử dụng");
				req.setAttribute("view", "/WEB-INF/views/user/register.jsp");
				req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, resp);
				return;
			}
			User user = new User();
			BeanUtils.populate(user, req.getParameterMap());
			user.setId(UUID.randomUUID().toString());
			user.setPassword(HashUtil.hash(user.getPassword()));
						
			String code = VerificationCodeUtil.generateCode(6);
			
			//lưu tạm user và verify code vào session
			HttpSession session = req.getSession(true);
			session.setAttribute("pendingUser", user);
			session.setAttribute("verificationCode", code);
			
			//gửi mail 
			mailService.sendVerifycationCode(email, user.getFullname(), code);
			
			
			
//			userService.create(user);			
//			mailService.sendWelcomeMail(user.getEmail(), user.getFullname());
			
			
//			String message = URLEncoder.encode("Đăng ký thành công", "UTF-8");
//			resp.sendRedirect(req.getContextPath() + "/login?success=" + message);
			//Chuyển sang trang nhập mã xác thực
			resp.sendRedirect(req.getContextPath() + "/verify-code");
		} catch (Exception e) {
			AppExceptionHandler.handle(req, resp, e);
		}
	}




}

