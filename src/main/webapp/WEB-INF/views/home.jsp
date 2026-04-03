<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${empty sessionScope.currentUser}">
	<div class="alert alert-info" role="alert">
		🔒 Vui lòng <a href="${pageContext.request.contextPath}/login">đăng
			nhập</a> để tương tác với video (Thích, Chia sẻ...).
	</div>
</c:if>


<h3>🔥 Video phổ biến nhất</h3>
<div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
	<c:forEach var="v" items="${popularVideos}">
		<div class="col-md-4">
			<c:set var="v" value="${v }" scope="request" />
			<jsp:include page="/WEB-INF/views/user/video-card.jsp"></jsp:include>
		</div>
	</c:forEach>
</div>

<hr>

<h3>🆕 Video mới nhất</h3>
<div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
	<c:forEach var="v" items="${newVideos}">
		<div class="col">
			<c:set var="v" value="${v }" scope="request" />
			<jsp:include page="/WEB-INF/views/user/video-card.jsp"></jsp:include>
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
	<div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
		<c:forEach var="v" items="${videos}">			
				<c:set var="v" value="${v }" scope="request" />
				<div class="col">
					<jsp:include page="/WEB-INF/views/user/video-card.jsp"></jsp:include>
				</div>
		</c:forEach>
	</div>

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
