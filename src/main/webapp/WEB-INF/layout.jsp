<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Online Entertainment</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body style="background-color: #f8f9fa;">

    <!-- Header -->
    <header class="bg-light py-4 mb-4 border-bottom shadow-sm">
        <div class="container text-center">
            <h1 class="text-primary fw-bold">🎬 Online Entertainment</h1>
        </div>
    </header>

   <!-- Navigation Bar -->
<nav class="navbar navbar-expand-lg navbar-warning bg-warning mb-4 shadow-sm rounded-2 mx-3">
    <div class="container">
        <a class="navbar-brand fw-bold text-danger text-uppercase" href="${pageContext.request.contextPath}/home">OE</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
				<li class="nav-item">
                        <a class="nav-link text-dark fw-semibold" href="${pageContext.request.contextPath}/about">About Us</a>
                </li>
                <!-- Menu dành cho người dùng đăng nhập -->
                <c:if test="${not empty sessionScope.currentUser}">
                    <li class="nav-item">
                        <a class="nav-link text-dark fw-semibold" href="${pageContext.request.contextPath}/favorites">My Favorites</a>
                    </li>
                </c:if>

                <!-- Menu Admin -->
                <c:if test="${not empty sessionScope.currentUser and sessionScope.currentUser.admin}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-danger fw-bold" href="#" role="button" data-bs-toggle="dropdown">
                            Admin Panel
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/users">Quản lý người dùng</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/videos">Quản lý video</a></li>
                        </ul>
                    </li>
                </c:if>

            </ul>

            <!-- Tài khoản -->
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-dark fw-semibold" href="#" role="button" data-bs-toggle="dropdown">
                        <c:choose>
                            <c:when test="${not empty sessionScope.currentUser}">
                                ${sessionScope.currentUser.fullname}
                            </c:when>
                            <c:otherwise>My Account</c:otherwise>
                        </c:choose>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <c:if test="${not empty sessionScope.currentUser}">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/change-password">Change Password</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/delete-account">Delete Account</a></li>
                            <li><hr class="dropdown-divider" /></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logoff</a></li>
                        </c:if>
                        <c:if test="${empty sessionScope.currentUser}">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/login">Login</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/register">Register</a></li>
                        </c:if>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>


    <!-- Main Content -->
    <main class="container mb-5">
        <jsp:include page="${view}" />
    </main>

    <!-- Footer -->
    <footer class="bg-light py-4 mt-auto border-top text-center text-muted small">
        &copy; 2025 Online Entertainment. All rights reserved.
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
