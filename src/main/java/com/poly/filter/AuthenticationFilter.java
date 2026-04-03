package com.poly.filter;

import java.io.IOException;

import com.poly.entity.User;
import com.poly.service.RememberMeService;
import com.poly.service.UserService;
import com.poly.service.impl.RememberMeServiceImpl;
import com.poly.service.impl.UserServiceImpl;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


public class AuthenticationFilter implements Filter {

    private final UserService userService = new UserServiceImpl();
    private final RememberMeService rememberMeService = new RememberMeServiceImpl();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false); // false: không tạo mới nếu chưa có

        if (session == null || session.getAttribute("currentUser") == null) {
			String email = null;
			String token = null;
			Cookie[] cookies = req.getCookies();
			if(cookies != null) {
				for(Cookie c : cookies) {
					if("remember_email".equals(c.getName())) email = c.getValue();
					else if("remember_token".equals(c.getName())) token = c.getValue();
				}
			}
			
			if(email != null && token != null) {
				User user = userService.findByEmail(email);
				if(user != null && rememberMeService.validateToken(user.getId(), token)) {
					req.getSession(true).setAttribute("currentUser", user);
					System.out.println(">>> Auto-login thành công cho " + user.getFullname() + " - email: " + email);
				}
			}
		}

        chain.doFilter(request, response);
    }
}
