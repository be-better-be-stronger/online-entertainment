<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:url var="baseUrl" value="/favorites"/>

<h2>❤️ Danh sách Video Yêu Thích</h2>
<c:if test="${empty favorites}">
	<p>Không có video nào được yêu thích</p>
</c:if>

<ul>
	  <c:forEach var="v" items="${favorites }">
	  		<li>
	  			<c:set var="v" value="${v }" scope="request" />
				<jsp:include page="/WEB-INF/views/user/video-card.jsp"></jsp:include>
	  		</li>
	  		<hr>
	  </c:forEach>
</ul>

<!-- PHÂN TRANG -->
<c:if test="${totalPages > 1}">
    <div>
        <c:choose>
            <c:when test="${page > 0}">
                <a href="${baseUrl }?page=0">⏪</a>
                <a href="${baseUrl }?page=${page - 1}">◀</a>
            </c:when>
            <c:otherwise>
                ⏪ ◀
            </c:otherwise>
        </c:choose>

        Trang ${page + 1} / ${totalPages}

        <c:choose>
            <c:when test="${page < totalPages - 1}">
                <a href="${baseUrl }?page=${page + 1}">▶</a>
                <a href="${baseUrl }?page=${totalPages - 1}">⏩</a>
            </c:when>
            <c:otherwise>
                ▶ ⏩
            </c:otherwise>
        </c:choose>
    </div>
</c:if>
