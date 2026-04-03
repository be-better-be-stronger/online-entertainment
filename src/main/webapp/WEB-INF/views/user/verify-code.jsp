<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>Xác thực email</h2>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>
<form action="${pageContext.request.contextPath}/verify-code" method="post">
    <label>Nhập mã xác thực đã gửi qua email:</label>
    <input type="text" name="code" maxlength="6" required />
    <button type="submit">Xác nhận</button>
</form>
