<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty error}">
    <div class="alert alert-danger text-center">${error}</div>
</c:if>

<h2>Đăng ký tài khoản</h2>
<form action="#" method="post">
   <div>
	   <label class="d-inline-block me-1">Họ tên:</label>
	   <input class="d-inline-block" type="text" name="fullname">
   </div>

    <div>
	    <label class="d-inline-block me-1">Email:</label>
	    <input class="d-inline-block" type="email" name="email">
    </div>

    <div>
	    <label class="d-inline-block me-1">Mật khẩu:</label>
	    <input class="d-inline-block" type="password" name="password">
    </div>

    <button type="submit">Đăng ký</button>
</form>
