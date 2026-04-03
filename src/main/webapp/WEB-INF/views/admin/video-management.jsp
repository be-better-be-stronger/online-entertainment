<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="container mt-4">
	<h2>Quản lý Video</h2>

	<!-- Form Thêm / Cập nhật Video -->
	<form method="post"
		action="${pageContext.request.contextPath}/admin/videos"
		enctype="multipart/form-data" class="mb-4">
		<input type="hidden" name="action"
			value="${videoEdit != null ? 'update' : 'create'}">
		<c:if test="${videoEdit != null}">
			<input type="hidden" name="id" value="${videoEdit.id}" />
		</c:if>

		<div class="row g-3">
			<div class="col-md-6">
				<label>Tiêu đề</label> <input type="text" class="form-control"
					name="title" value="${videoEdit.title}" required>
			</div>
			<div class="col-md-6">
				<label>Link</label> <input type="text" class="form-control"
					name="link" value="${videoEdit.link}">
			</div>
			<div class="col-md-6">
				<label>Mô tả</label> <input type="text" class="form-control"
					name="description" value="${videoEdit.description}">
			</div>
			<div class="col-md-6">
				<label>Trạng thái</label><br> <input type="checkbox"
					name="active" value="true" ${videoEdit.active ? 'checked' : ''} />
				Kích hoạt
			</div>
			<div class="col-md-12">
				<label>Ảnh Poster</label> <input type="file" class="form-control"
					name="poster">
			</div>
		</div>

		<div class="mt-3">
			<button class="btn btn-primary" type="submit">${videoEdit != null ? 'Cập nhật' : 'Thêm mới'}</button>
			<a href="${pageContext.request.contextPath}/admin/videos"
				class="btn btn-secondary">Hủy</a>
		</div>
	</form>

	<!-- Danh sách video -->
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th>Ảnh</th>
				<th>Link</th>
				<th>Tiêu đề</th>
				<th>Mô tả</th>
				<th>Ngày tạo</th>
				<th>Active</th>
				<th>Hành động</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${videos}" var="v">
				<tr>
					<td><img
						src="${pageContext.request.contextPath}/poster?id=${v.id}"
						alt="poster" width="100" height="60"></td>
					<td>${v.link}</td>
					<td>${v.title}</td>
					<td>${v.description}</td>
					<td><fmt:formatDate value="${v.createdDate}"
							pattern="dd/MM/yyyy" /></td>
					<td>${v.active ? '✔' : '✘'}</td>
					<td>
						<div class="d-flex gap-1">
							<a
								href="${pageContext.request.contextPath}/admin/videos?editId=${v.id}"
								class="btn btn-sm btn-warning">Sửa</a>
							<form method="post"
								action="${pageContext.request.contextPath}/admin/videos">
								<input type="hidden" name="action" value="delete" /> <input
									type="hidden" name="id" value="${v.id}" />
								<button class="btn btn-sm btn-danger"
									onclick="return confirm('Xác nhận xóa?')">Xóa</button>
							</form>
						</div>

					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- PHÂN TRANG -->
	<nav aria-label="Video page navigation">
		<ul class="pagination">
			<c:forEach begin="0" end="${totalPages - 1}" var="i">
				<li class="page-item ${i == currentPage ? 'active' : ''}"><a
					class="page-link"
					href="${pageContext.request.contextPath}/admin/videos?page=${i}">${i + 1}</a>
				</li>
			</c:forEach>
		</ul>
	</nav>
</div>
