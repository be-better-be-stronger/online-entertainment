<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>Nhập mã xác nhận</h2>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>
<form method="post" action="${pageContext.request.contextPath}/verify-reset-code">
  <label>Mã xác nhận:</label>
  <input type="text" name="code" required>
  <button type="submit">Xác nhận</button>
</form>


