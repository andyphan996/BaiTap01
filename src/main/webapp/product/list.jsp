<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Sản phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background: #f5f5f5; }
        header { background: #2c3e50; color: #fff; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        header a { color: #fff; text-decoration: none; margin-left: 15px; }
        nav.menu { padding: 10px 30px; background: #34495e; }
        nav.menu a { color: #fff; text-decoration: none; margin-right: 20px; font-size: 14px; }
        .container { padding: 20px 30px; }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 16px; }
        .card { background: #fff; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,0.15); overflow: hidden; text-decoration: none; color: inherit; display: block; }
        .card img { width: 100%; height: 150px; object-fit: cover; background: #eee; }
        .card .info { padding: 10px; }
        .card .name { font-weight: bold; font-size: 14px; margin: 0 0 6px; }
        .card .price { color: #e74c3c; font-weight: bold; }
        .pagination { margin-top: 20px; }
        .pagination a, .pagination span { display: inline-block; padding: 6px 12px; margin-right: 6px; border: 1px solid #ccc; border-radius: 4px; text-decoration: none; color: #333; }
        .pagination .current { background: #2c3e50; color: #fff; border-color: #2c3e50; }
        .empty { color: #777; }
    </style>
</head>
<body>
<header>
    <h2 style="margin:0;">Sản phẩm</h2>
    <div>
        <c:choose>
            <c:when test="${not empty sessionScope.loggedUser}">
                Xin chào, ${sessionScope.loggedUser.fullName}
                <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>

<nav class="menu">
    <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
    <a href="${pageContext.request.contextPath}/product">Sản phẩm</a>
    <c:if test="${not empty sessionScope.loggedUser}">
        <a href="${pageContext.request.contextPath}/category">Quản lý Category</a>
        <a href="${pageContext.request.contextPath}/product-admin">Quản lý Product</a>
    </c:if>
</nav>

<div class="container">
    <h3>Tất cả sản phẩm (trang ${currentPage}/${totalPages})</h3>

    <c:if test="${empty products}">
        <p class="empty">Chưa có sản phẩm nào.</p>
    </c:if>

    <div class="grid">
        <c:forEach var="p" items="${products}">
            <a class="card" href="${pageContext.request.contextPath}/product-detail?id=${p.id}">
                <c:choose>
                    <c:when test="${not empty p.imagePath}">
                        <img src="${pageContext.request.contextPath}/images/products/${p.imagePath}" alt="${p.name}"/>
                    </c:when>
                    <c:otherwise>
                        <img src="https://via.placeholder.com/200x150?text=No+Image" alt="no image"/>
                    </c:otherwise>
                </c:choose>
                <div class="info">
                    <p class="name">${p.name}</p>
                    <p class="price"><fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/> đ</p>
                    <p style="font-size:12px;color:#888;margin:0;">${p.category.name}</p>
                </div>
            </a>
        </c:forEach>
    </div>

    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="${pageContext.request.contextPath}/product?page=${currentPage - 1}">&laquo; Trước</a>
        </c:if>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span class="current">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/product?page=${i}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <a href="${pageContext.request.contextPath}/product?page=${currentPage + 1}">Sau &raquo;</a>
        </c:if>
    </div>
</div>
</body>
</html>
