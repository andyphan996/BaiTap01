<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Trang chủ</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background: #f5f5f5; }
        .container { padding: 20px 30px; }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 16px; }
        .card { background: #fff; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,0.15); overflow: hidden; text-decoration: none; color: inherit; display: block; }
        .card img { width: 100%; height: 150px; object-fit: cover; background: #eee; }
        .card .info { padding: 10px; }
        .card .name { font-weight: bold; font-size: 14px; margin: 0 0 6px; }
        .card .price { color: #e74c3c; font-weight: bold; }
        .empty { color: #777; }
    </style>
</head>
<body>
<div class="container">
    <h3>10 sản phẩm mới nhất</h3>

    <c:if test="${empty latestProducts}">
        <p class="empty">Chưa có sản phẩm nào.</p>
    </c:if>

    <div class="grid">
        <c:forEach var="p" items="${latestProducts}">
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
</div>
</body>
</html>
