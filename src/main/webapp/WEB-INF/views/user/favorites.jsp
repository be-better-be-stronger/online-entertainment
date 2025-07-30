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
	  			<a href="${pageContext.request.contextPath}/video-detail?id=${v.id}">	  				
	  				<img src="${pageContext.request.contextPath}/images/${v.poster}" alt="${v.title}" width="150" /> <br>
	  				<strong>${v.title }</strong>
	  			</a><br>
	  			Lượt xem: ${v.views } <br>
	  			Ngày đăng: 
	  			<fmt:formatDate value="${v.createdDate }" pattern="dd/MM/yyyy HH:mm"/> <br/><br/>
	  			<form action="${pageContext.request.contextPath}/video/like" method="post" style="display:inline;">
	                <input type="hidden" name="videoId" value="${v.id}" />
	                <button type="submit">❌ Xóa khỏi danh sách yêu thích</button>
	            </form>
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
