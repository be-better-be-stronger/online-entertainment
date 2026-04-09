<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:url var="baseUrl" value="/favorites" />
<div class="container mt-4">
	<h2 class="mt-4">❤️ Danh sách Video Yêu Thích</h2>
	<c:if test="${empty data.items}">
		<div class="alert alert-info">Bạn chưa yêu thích video nào.</div>
	</c:if>

	<div class="row row-cols-1 row-cols-md-3 g-4">
		<c:forEach var="v" items="${data.items }">
		<c:set var="v" value="${v }" scope="request" /> 
			<div class="col">
				<jsp:include page="/WEB-INF/views/user/video-card.jsp"/>
			</div>			
		</c:forEach>
	</div>

	<!-- PHÂN TRANG -->
  <c:if test="${data.totalPages > 1}">
    <nav class="mt-4">
      <ul class="pagination justify-content-center">
        <c:choose>
          <c:when test="${data.page > 0}">
            <li class="page-item">
              <a class="page-link" href="${baseUrl}?page=0">⏪</a>
            </li>
            <li class="page-item">
              <a class="page-link" href="${baseUrl}?page=${data.page - 1}">◀</a>
            </li>
          </c:when>
          <c:otherwise>
            <li class="page-item disabled"><span class="page-link">⏪</span></li>
            <li class="page-item disabled"><span class="page-link">◀</span></li>
          </c:otherwise>
        </c:choose>

        <li class="page-item disabled">
          <span class="page-link">Trang ${data.page + 1} / ${data.totalPages}</span>
        </li>

        <c:choose>
          <c:when test="${data.page < data.totalPages - 1}">
            <li class="page-item">
              <a class="page-link" href="${baseUrl}?page=${data.page + 1}">▶</a>
            </li>
            <li class="page-item">
              <a class="page-link" href="${baseUrl}?page=${data.totalPages - 1}">⏩</a>
            </li>
          </c:when>
          <c:otherwise>
            <li class="page-item disabled"><span class="page-link">▶</span></li>
            <li class="page-item disabled"><span class="page-link">⏩</span></li>
          </c:otherwise>
        </c:choose>
      </ul>
    </nav>
  </c:if>
</div>

