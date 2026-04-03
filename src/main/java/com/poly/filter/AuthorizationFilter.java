package com.poly.filter;

import java.io.IOException;
import java.util.Set;


import com.poly.entity.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



public class AuthorizationFilter implements Filter {
	
    private static final Set<String> protectedPaths = Set.of(
        "/video/like", "/profile", "/favorite", "/comment" , "/share", "/logout", "/delete-account"// hoặc bất kỳ URL cần login
    );
    
    private static final String ADMIN_PREFIX = "/admin/";
    
    private static final Set<String> STATIC_PREFIX = Set.of("/assets/", "/static/", "/css/", "/js/", "/images/", "/vendor/");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();									// VD: /oe/video/like
        String path = uri.replace(req.getContextPath(), ""); // chuẩn hóa đường dẫn -> /video/like
        
        // Bypass static
        if (STATIC_PREFIX.stream().anyMatch(path::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        boolean requiresLogin = protectedPaths.stream().anyMatch(path::startsWith) || path.startsWith(ADMIN_PREFIX);
        
        HttpSession session = req.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;
        
        boolean isLoggedIn = session != null && currentUser != null;

        if (requiresLogin && !isLoggedIn) {
        	// Ghi nhớ đường dẫn gốc (bao gồm cả query string nếu có)
            String query = req.getQueryString(); // VD: videoId=abc123
            String fullUrl = uri + (query != null ? "?" + query : "");
            req.getSession(true).setAttribute("redirectBack", fullUrl);
           
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        } 

     // Nếu truy cập admin nhưng không phải admin
        if (path.startsWith(ADMIN_PREFIX)  ) {            
            if (!currentUser.getAdmin()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}

