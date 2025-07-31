<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Chia sẻ video</h2>

<c:if test="${not empty message}">
    <p style="color: green;">${message}</p>
</c:if>

<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>

<hr>

<h3>Thông tin video</h3>
<p><strong>Tiêu đề:</strong> ${video.title}</p>
<p><strong>Mô tả:</strong> ${video.description}</p>
<p><strong>ID:</strong> ${video.id}</p>

<hr>

<form method="post" action="${pageContext.request.contextPath}/share">
    <input type="hidden" name="videoId" value="${video.id}" />

    <label>Email người nhận:</label><br>
    <input type="email" name="to" required /><br><br>

    <label>Lời nhắn:</label><br>
    <textarea name="message" rows="5" cols="40" placeholder="Nhập lời nhắn..."></textarea><br><br>

    <input type="submit" value="Gửi" />
</form>
