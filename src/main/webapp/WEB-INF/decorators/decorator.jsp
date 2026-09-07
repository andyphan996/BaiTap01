<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><sitemesh:write property="title" /></title>
    <sitemesh:write property="head" />
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background: #f5f5f5; color: #222; }
        header { background: #2c3e50; color: #fff; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        header a, nav a { color: #fff; text-decoration: none; margin-left: 15px; }
        nav { padding: 10px 30px; background: #34495e; }
        nav a { margin: 0 20px 0 0; font-size: 14px; }
        main { padding: 20px 30px; }
        input, select, textarea, button { margin: 4px; padding: 6px; }
        table { background: #fff; border-collapse: collapse; }
        td, th { padding: 6px 10px; }
    </style>
</head>
<body>
<header>
    <strong>BaiTap01</strong>
    <span>
        <c:if test="${not empty sessionScope.loggedUser}">
            Xin chào, ${sessionScope.loggedUser.fullName}
            <a href="${pageContext.request.contextPath}/profile">Profile</a>
            <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
        </c:if>
    </span>
</header>
<nav>
    <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
    <a href="${pageContext.request.contextPath}/product">Sản phẩm</a>
    <c:if test="${not empty sessionScope.loggedUser}">
        <a href="${pageContext.request.contextPath}/category">Category</a>
        <a href="${pageContext.request.contextPath}/product-admin">Product</a>
        <c:if test="${sessionScope.loggedUser.role == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/user">User</a>
        </c:if>
    </c:if>
</nav>
<main><sitemesh:write property="body" /></main>
</body>
</html>