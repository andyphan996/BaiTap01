<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><sitemesh:write property="title" /></title>
    <sitemesh:write property="head" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-semibold" href="${pageContext.request.contextPath}/home">BaiTap01</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav"
                aria-controls="mainNav" aria-expanded="false" aria-label="Mở menu">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="mainNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/product">Sản phẩm</a></li>
                <c:if test="${not empty sessionScope.loggedUser}">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/category">Category</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/product-admin">Product</a></li>
                    <c:if test="${sessionScope.loggedUser.role == 'ADMIN'}">
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/user">User</a></li>
                    </c:if>
                </c:if>
            </ul>
            <c:if test="${not empty sessionScope.loggedUser}">
                <span class="navbar-text me-3">Xin chào, ${sessionScope.loggedUser.fullName}</span>
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/profile">Profile</a>
                <a class="btn btn-warning btn-sm ms-2" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </c:if>
            <c:if test="${empty sessionScope.loggedUser}">
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                <a class="btn btn-warning btn-sm ms-2" href="${pageContext.request.contextPath}/register">Đăng ký</a>
            </c:if>
        </div>
    </div>
</nav>
<main class="container py-4">
    <sitemesh:write property="body" />
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>