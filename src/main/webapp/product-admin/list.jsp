<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head><title>Product List</title></head>
<body>
    <h2>Danh sách Product</h2>
    <p>
        <a href="${pageContext.request.contextPath}/home">&laquo; Trang chủ</a> |
        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
    </p>

    <a href="${pageContext.request.contextPath}/product-admin?action=new">+ Thêm mới</a>

    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th><th>Ảnh</th><th>Tên</th><th>Category</th>
            <th>Giá</th><th>SL</th><th>Thao tác</th>
        </tr>
        <c:forEach var="p" items="${products}">
            <tr>
                <td>${p.id}</td>
                <td>
                    <c:if test="${not empty p.imagePath}">
                        <img src="${pageContext.request.contextPath}/images/products/${p.imagePath}" width="80"/>
                    </c:if>
                </td>
                <td>${p.name}</td>
                <td>${p.category.name}</td>
                <td><fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/></td>
                <td>${p.quantity}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/product-admin?action=edit&id=${p.id}">Sửa</a> |
                    <a href="${pageContext.request.contextPath}/product-admin?action=delete&id=${p.id}"
                       onclick="return confirm('Xóa sản phẩm này?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
