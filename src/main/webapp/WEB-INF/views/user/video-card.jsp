<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<div class="card h-100 shadow-sm">
	<!-- Poster -->
	<a href="${v.link }">
		<div style="height: 200px; overflow: hidden;">
			<img src="${pageContext.request.contextPath}/poster?id=${v.id}"
				alt="Poster" class="card-img-top img-fluid" 
				style="height: 100%; width: 100%; object-fit: cover;">
		</div>
	</a>
	<div class="card-body d-flex flex-column">
		<h5 class="card-title text-truncate mb-2" style="max-width: 100%;">
			<a class="text-decoration-none text-dark" href="${pageContext.request.contextPath}/video-detail?id=${v.id}">${v.title}</a>
		</h5>
		<p class="mb-1">Lượt xem: ${v.views}</p>
		<p class="mb-1">
			Ngày đăng:
			<fmt:formatDate value="${v.createdDate}" pattern="dd/MM/yyyy HH:mm" />
		</p>

		<div class="mb-2">
			<span class="me-3">❤️ Lượt thích: ${v.likeCount}</span>
			<span>🔁 Lượt chia sẻ: ${v.shareCount}</span>
		</div>

		<!-- Nút Like & Share -->
		<c:if test="${not empty sessionScope.currentUser }">
			<div class="mt-auto d-flex gap-2">
				<form action="${pageContext.request.contextPath}/video/like"
					method="post">
					<input type="hidden" name="videoId" value="${v.id}" />
					<button type="submit"
						class="btn ${v.liked ? 'btn-danger' : 'btn-outline-danger'}">
						${v.liked ? '❤️ Đã thích' : '🤍 Thích'}</button>
				</form>

				<form action="${pageContext.request.contextPath}/share" method="get">
					<input type="hidden" name="videoId" value="${v.id}" />
					<button type="submit" class="btn btn-outline-primary">🔗
						Chia sẻ</button>
				</form>
			</div>
		</c:if>
	</div>
</div>
