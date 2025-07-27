package com.poly.filter;

import java.io.IOException;
import java.util.Set;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final Set<String> protectedPaths = Set.of(
        "/video/like", "/profile", "/favorite", "/comment" // hoặc bất kỳ URL cần login
    );
    
    private static final String ADMIN_PREFIX = "/admin/";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String path = uri.replace(req.getContextPath(), ""); // chuẩn hóa đường dẫn

        boolean requiresLogin = protectedPaths.stream().anyMatch(path::startsWith) ||
        		 path.startsWith(ADMIN_PREFIX);
        boolean isLoggedIn = req.getSession(false) != null && req.getSession(false).getAttribute("currentUser") != null;

        if (requiresLogin && !isLoggedIn) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            chain.doFilter(request, response); // Cho phép tiếp tục
        }
    }
}

