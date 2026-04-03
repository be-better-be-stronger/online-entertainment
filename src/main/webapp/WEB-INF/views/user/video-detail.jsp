<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="container mt-4">

  <div class="card shadow">
    <!-- Video poster -->
    <a href="${video.link}" target="_blank" class="text-decoration-none">
      <img src="${pageContext.request.contextPath}/poster?id=${video.id}" 
           alt="Poster" class="card-img-top img-fluid" style="max-height: 500px; object-fit: cover;">
    </a>

    <div class="card-body">
      <!-- Tiêu đề -->
      <h3 class="card-title text-dark mb-3">${video.title}</h3>

      <div class="row mb-3">
        <!-- Cột thông tin -->
        <div class="col-md-6">
          <p class="mb-1">👁️ <strong>Lượt xem:</strong> ${video.views}</p>
          <p class="mb-1">📅 <strong>Ngày đăng:</strong>
            <fmt:formatDate value="${video.createdDate}" pattern="dd/MM/yyyy HH:mm" />
          </p>
          <p class="mb-1">❤️ <strong>Lượt thích:</strong> ${video.likeCount}</p>
          <p class="mb-1">🔁 <strong>Lượt chia sẻ:</strong> ${video.shareCount}</p>
        </div>

        <!-- Cột nút Like + Share -->
        <c:if test="${not empty sessionScope.currentUser}">
          <div class="col-md-6 text-md-end mt-3 mt-md-0">
            <form class="d-inline-block me-2" action="${pageContext.request.contextPath}/video/like" method="post">
              <input type="hidden" name="videoId" value="${video.id}" />
              <button type="submit" class="btn ${video.liked ? 'btn-danger' : 'btn-outline-danger'}">
                ${video.liked ? '❤️ Đã thích' : '🤍 Thích'}
              </button>
            </form>

            <form class="d-inline-block" action="${pageContext.request.contextPath}/share" method="get">
              <input type="hidden" name="videoId" value="${video.id}" />
              <button type="submit" class="btn btn-outline-primary">🔗 Chia sẻ</button>
            </form>
          </div>
        </c:if>
      </div>

      <!-- Mô tả -->
      <div class="mt-3">
        <h5 class="text-muted">Mô tả</h5>
        <p class="card-text">${video.description}</p>
      </div>
    </div>
  </div>
</div>
