package com.poly.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        long start = System.currentTimeMillis();

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 1. Tạo requestId (trace)
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);

        String method = req.getMethod();
        String uri = req.getRequestURI();
        String query = req.getQueryString();
        String ip = request.getRemoteAddr();

        try {
            // 2. Log request vào
            log.info("➡️ [{}] {} {}{} | IP={}",
                    requestId,
                    method,
                    uri,
                    (query != null ? "?" + query : ""),
                    ip
            );

            // 3. Cho request đi tiếp
            chain.doFilter(request, response);

        } catch (Exception e) {

            log.error("💥 [{}] ERROR during request", requestId, e);
            throw e;

        } finally {
            long duration = System.currentTimeMillis() - start;

            // 4. Log response
            log.info("⬅️ [{}] {} {} -> status={} ({} ms)",
                    requestId,
                    method,
                    uri,
                    resp.getStatus(),
                    duration
            );

            MDC.clear();
        }
    }
}