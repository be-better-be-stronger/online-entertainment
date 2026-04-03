<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Đổi mật khẩu</h2>

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<form action="${pageContext.request.contextPath }/change-password" method="post">
    <label>Mật khẩu hiện tại:</label><br>
    <input type="password" name="oldPassword"><br><br>

    <label>Mật khẩu mới:</label><br>
    <input type="password" name="newPassword"><br><br>

    <label>Xác nhận mật khẩu mới:</label><br>
    <input type="password" name="confirmPassword"><br><br>

    <button type="submit">Đổi mật khẩu</button>
</form>
