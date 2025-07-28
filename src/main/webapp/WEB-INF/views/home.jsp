<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<h3>🔥 Video phổ biến nhất</h3>
<div class="row">
	<c:forEach var="v" items="${popularVideos}">
		<div class="col-md-4">
			<div class="card mb-3">
				<a href="${pageContext.request.contextPath}/video-detail?id=${v.id}">
					<img src="images/${v.poster}" class="card-img-top">
				</a>
				<div class="card-body">
					<h5>
						<a
							href="${pageContext.request.contextPath}/video-detail?id=${v.id}">${v.title}</a>
					</h5>
					<p>Lượt xem: ${v.views}</p>
					<p>
						Ngày đăng:
						<fmt:formatDate value="${v.createdDate}"
							pattern="dd/MM/yyyy HH:mm" />
					</p>
					<!-- Form Like -->
					<form action="${pageContext.request.contextPath}/video/like"
						method="post">
						<input type="hidden" name="videoId" value="${v.id}" />
						<button type="submit"
							class="btn ${v.liked ? 'btn-danger' : 'btn-outline-danger'}">
							${v.liked ? '❤️ Đã thích' : '🤍 Thích'}</button>
					</form>
				</div>
			</div>
		</div>
	</c:forEach>
</div>

<hr>

<h3>🆕 Video mới nhất</h3>
<div class="row">
	<c:forEach var="v" items="${newVideos}">
		<div class="col-md-4">
			<div class="card mb-3">
				<a href="${pageContext.request.contextPath}/video-detail?id=${v.id}">
					<img src="images/${v.poster}" class="card-img-top">
				</a>
				<div class="card-body">
					<h5>
						<a href="${pageContext.request.contextPath}/video-detail?id=${v.id}">${v.title}</a>
					</h5>
					<p>Lượt xem: ${v.views}</p>
					<p>
						Ngày đăng:
						<fmt:formatDate value="${v.createdDate}"
							pattern="dd/MM/yyyy HH:mm" />
					</p>
					<!-- Form Like -->
					<form action="${pageContext.request.contextPath}/video/like"
						method="post">
						<input type="hidden" name="videoId" value="${v.id}" />
						<button type="submit"
							class="btn ${v.liked ? 'btn-danger' : 'btn-outline-danger'}">
							${v.liked ? '❤️ Đã thích' : '🤍 Thích'}</button>
					</form>
				</div>
			</div>
		</div>
	</c:forEach>
</div>

<!-- PHÂN TRANG -->


<hr>

<div class="row">
	<c:if test="${empty videos}">
		<div class="col-12 text-center">
			<div class="alert alert-info">Hiện chưa có video nào trong
				trang này.</div>
		</div>
	</c:if>

	<c:forEach var="v" items="${videos}">
		<div class="col-md-4 mb-4">
			<div class="card h-100 shadow-sm">
				<a href="${pageContext.request.contextPath}/video-detail?id=${v.id}">
					<img src="images/${v.poster}" class="card-img-top">
				</a>
				<div class="card-body">
					<h5 class="card-title">
						<a
							href="${pageContext.request.contextPath}/video-detail?id=${v.id}">${v.title}</a>
					</h5>
					<p>Lượt xem: ${v.views}</p>
					<p>
						Ngày đăng:
						<fmt:formatDate value="${v.createdDate}"
							pattern="dd/MM/yyyy HH:mm" />
					</p>
					<!-- Form Like -->
					<form action="${pageContext.request.contextPath}/video/like"
						method="post">
						<input type="hidden" name="videoId" value="${v.id}" />
						<button type="submit"
							class="btn ${v.liked ? 'btn-danger' : 'btn-outline-danger'}">
							${v.liked ? '❤️ Đã thích' : '🤍 Thích'}</button>
					</form>
				</div>
			</div>
		</div>
	</c:forEach>
</div>



<nav aria-label="Page navigation">
	<ul class="pagination justify-content-center">
		<li class="page-item ${currentPage == 0 ? 'disabled' : ''}"><a
			class="page-link" href="?page=0">First</a></li>
		<li class="page-item ${currentPage == 0 ? 'disabled' : ''}"><a
			class="page-link" href="?page=${currentPage - 1}">Prev</a></li>

		<c:forEach var="i" begin="${startPage}" end="${endPage}">
			<li class="page-item ${i == currentPage ? 'active' : ''}"><a
				class="page-link" href="?page=${i}">${i + 1}</a></li>
		</c:forEach>

		<li
			class="page-item ${currentPage >= totalPages - 1 ? 'disabled' : ''}">
			<a class="page-link" href="?page=${currentPage + 1}">Next</a>
		</li>
		<li
			class="page-item ${currentPage >= totalPages - 1 ? 'disabled' : ''}">
			<a class="page-link" href="?page=${totalPages - 1}">Last</a>
		</li>
	</ul>
</nav>
