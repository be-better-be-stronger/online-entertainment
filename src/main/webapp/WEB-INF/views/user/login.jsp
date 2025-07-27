<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<h2>Đăng nhập</h2>
<form action="${pageContext.request.contextPath}/login" method="post">
	<label>Username:</label><br> <input type="text" name="username"><br>
	<br> <label>Password:</label><br> <input type="password"
		name="password"><br> <br> 
	<label> 
		<input type="checkbox" name="remember" /> Ghi nhớ đăng nhập
	</label>
	<button type="submit">Đăng nhập</button>
</form>
<p>
	Quên mật khẩu? 
	<a href="${pageContext.request.contextPath}/forgot-password">
		Lấy lại mật khẩu
	</a>
</p>
