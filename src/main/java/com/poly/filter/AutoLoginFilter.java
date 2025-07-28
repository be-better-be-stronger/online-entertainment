package com.poly.filter;

import java.io.IOException;

import com.poly.entity.User;
import com.poly.service.UserService;
import com.poly.service.impl.UserServiceImpl;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*") 
public class AutoLoginFilter implements Filter {

    private final UserService userService = new UserServiceImpl();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(true); // false: không tạo mới nếu chưa có

        if (session.getAttribute("currentUser") == null) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("remember_username".equals(c.getName())) { // ✅ TÊN COOKIE PHẢI KHỚP
                        String username = c.getValue();
                        User user = userService.getByUserName(username);
                        if (user != null) {
                            session.setAttribute("currentUser", user);
                            System.out.println(">>> Auto-login thành công cho " + username); // để debug
                        }
                        break;
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }
}
