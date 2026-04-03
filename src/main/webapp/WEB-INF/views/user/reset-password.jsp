<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<h2>Đặt lại mật khẩu</h2>
<form method="post" action="${pageContext.request.contextPath}/reset-password">
  <label>Mật khẩu mới:</label>
  <input type="password" name="password" required>
  <button type="submit">Cập nhật</button>
</form>

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>
<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>
