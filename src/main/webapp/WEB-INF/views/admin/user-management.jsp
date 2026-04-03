<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2 class="mb-3">Quản lý người dùng</h2>
<form action="${pageContext.request.contextPath}/admin/users" method="get" class="mb-3">
    <div class="d-flex justify-content-between align-items-center">

        <!-- BÊN TRÁI: Lọc theo vai trò -->
        <div class="d-flex align-items-center">
            <select name="role" class="form-select me-2">
                <option value="">-- Tất cả --</option>
                <option value="admin" ${param.role == 'admin' ? 'selected' : ''}>Admin</option>
                <option value="user" ${param.role == 'user' ? 'selected' : ''}>User</option>
            </select>
        </div>

        <!-- BÊN PHẢI: Tìm kiếm -->
        <div class="d-flex align-items-center">
            <input type="text" name="keyword" class="form-control me-2 w-100"
                placeholder="Tìm theo tên hoặc email" value="${keyword}" />
            <button type="submit" class="btn btn-primary">Tìm</button>
        </div>

    </div>
</form>




<table border="1" cellpadding="8" cellspacing="0" width="100%">
    <thead>
        <tr>
            <th>ID</th>
            <th>Email</th>
            <th>Họ tên</th>
            <th>Admin?</th>
            <th>Hành động</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.email}</td>
                <td>${user.fullname}</td>
                <td>
                    <c:choose>
                        <c:when test="${user.admin}">✔</c:when>
                        <c:otherwise>✖</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/users" method="post" onsubmit="return confirm('Bạn có chắc muốn xóa người dùng này?');">
                        <input type="hidden" name="action" value="delete" />
                        <input type="hidden" name="id" value="${user.id}" />
                        <input type="submit" value="Xóa" />
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<br>

<!-- Phân trang -->
<div style="text-align: center;">
    <c:forEach begin="0" end="${totalPages - 1}" var="i">
        <a href="${pageContext.request.contextPath}/admin/users?page=${i}&keyword=${keyword}
        	&role=${param.role}">[${i + 1}]</a>
    </c:forEach>
</div>
