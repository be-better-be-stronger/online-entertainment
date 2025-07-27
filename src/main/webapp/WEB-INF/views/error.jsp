<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container mt-5">
  <div class="alert alert-danger">
    <h3>Lỗi!</h3>
    <p>${errorMessage}</p>
  </div>
  <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">Quay về trang chủ</a>
</div>
