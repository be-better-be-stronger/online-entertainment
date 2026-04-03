<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>Quên mật khẩu</h2>

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>
<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/forgot-password">
  <label>Email:</label>
  <input type="email" name="email" required>
  <button type="submit">Gửi mã xác nhận</button>
</form>


