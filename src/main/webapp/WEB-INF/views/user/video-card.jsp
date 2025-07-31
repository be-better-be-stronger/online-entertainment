<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="card mb-3">
	<a href="${pageContext.request.contextPath}/video-detail?id=${v.id}">
		<img src="images/${v.poster}" alt="Poster" class="card-img-top">
	</a>
	<div class="card-body">
		<h5>
			<a href="${pageContext.request.contextPath}/video-detail?id=${v.id}">${v.title}</a>
		</h5>
		<p>Lượt xem: ${v.views}</p>
		<p>
			Ngày đăng:
			<fmt:formatDate value="${v.createdDate}" pattern="dd/MM/yyyy HH:mm" />
		</p>

		<!-- Nút Like & Share cùng hàng -->
		<div>
			<form class="d-inline-block me-1" action="${pageContext.request.contextPath}/video/like" method="post">
				<input type="hidden" name="videoId" value="${v.id}" />
				<button type="submit"
					class="btn ${v.liked ? 'btn-danger' : 'btn-outline-danger'}">
					${v.liked ? '❤️ Đã thích' : '🤍 Thích'}
				</button>
			</form>

			<form class="d-inline-block" action="${pageContext.request.contextPath}/share" method="get">
				<input type="hidden" name="videoId" value="${v.id}" />
				<button type="submit" class="btn btn-outline-primary">🔗 Chia sẻ</button>
			</form>
		</div>
	</div>
</div>
