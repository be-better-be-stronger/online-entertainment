<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h2>Bạn có chắc chắn muốn xóa tài khoản của mình không?</h2>

<form method="post"
	action="${pageContext.request.contextPath}/delete-account">
	<button type="submit" class="btn btn-danger">Có</button>
</form>

<form method="get" action="${pageContext.request.contextPath}/home">
	<button type="submit" class="btn btn-secondary">Không</button>
</form>