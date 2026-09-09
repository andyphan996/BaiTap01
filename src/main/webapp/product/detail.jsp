<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>${product.name}</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background: #f5f5f5; }
        .container { padding: 20px 30px; max-width: 900px; margin: 0 auto; }
        .detail { background: #fff; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,0.15); display: flex; gap: 24px; padding: 20px; }
        .detail img { width: 320px; height: 320px; object-fit: cover; border-radius: 6px; background: #eee; }
        .detail .price { color: #e74c3c; font-size: 22px; font-weight: bold; margin: 10px 0; }
        .detail .meta { color: #666; font-size: 14px; }
        .back { display: inline-block; margin-bottom: 16px; }
    </style>
</head>
<body>
<div class="container">
    <a class="back" href="${pageContext.request.contextPath}/product">&laquo; Quay lại danh sách sản phẩm</a>

    <div class="detail">
        <c:choose>
            <c:when test="${not empty product.imagePath}">
                <img src="${pageContext.request.contextPath}/images/products/${product.imagePath}" alt="${product.name}"/>
            </c:when>
            <c:otherwise>
                <img src="https://via.placeholder.com/320x320?text=No+Image" alt="no image"/>
            </c:otherwise>
        </c:choose>
        <div>
            <h2>${product.name}</h2>
            <p class="meta">Danh mục: ${product.category.name}</p>
            <p class="price"><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/> đ</p>
            <p class="meta">Số lượng còn: ${product.quantity}</p>
            <p>${product.description}</p>
        </div>
    </div>
</div>
</body>
</html>
