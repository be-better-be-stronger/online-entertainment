<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="text-center mt-5">
    <h1 class="text-danger fw-bold">Lỗi ${statusCode}</h1>
    <p>${message}</p>
    <p>Trang yêu cầu: <code>${requestUri}</code></p>
    <a href="${pageContext.request.contextPath}/home">Về trang chủ</a>
</div>
