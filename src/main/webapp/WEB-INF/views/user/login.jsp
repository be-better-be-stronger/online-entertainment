<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty param.success}">
  <div class="alert alert-success text-center">${param.success}</div>
</c:if>

<c:if test="${not empty param.error}">
    <div class="alert alert-danger">${param.error}</div>
</c:if>

<c:if test="${not empty message}">
	<div class="alert alert-danger text-center">${message }</div>
</c:if>

<h2>Đăng nhập</h2>
<form action="${pageContext.request.contextPath}/login" method="post">
	<label>Email:</label><br> <input type="text" name="email"><br><br> 
	<label>Password:</label><br> <input type="password" name="password"><br> <br> 
	<label><input type="checkbox" name="remember" /> Ghi nhớ đăng nhập</label><br> <br> 
	<button type="submit">Đăng nhập</button>
</form>
<p>
	Quên mật khẩu? 
	<a href="${pageContext.request.contextPath}/forgot-password">
		Lấy lại mật khẩu
	</a>
</p>
