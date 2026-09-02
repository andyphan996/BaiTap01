<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Product Form</title></head>
<body>
    <h2>${empty product ? "Thêm" : "Sửa"} Product</h2>

    <form method="post" action="${pageContext.request.contextPath}/product-admin"
          enctype="multipart/form-data">

        <input type="hidden" name="id" value="${product.id}"/>

        Tên: <input type="text" name="name" value="${product.name}" required/><br/>

        Mô tả: <textarea name="description" rows="4" cols="40">${product.description}</textarea><br/>

        Giá: <input type="number" step="0.01" min="0" name="price" value="${product.price}" required/><br/>

        Số lượng: <input type="number" min="0" name="quantity" value="${product.quantity}" required/><br/>

        Category:
        <select name="categoryId" required>
            <option value="">-- Chọn category --</option>
            <c:forEach var="c" items="${categories}">
                <option value="${c.id}" ${not empty product.category && product.category.id == c.id ? 'selected' : ''}>
                    ${c.name}
                </option>
            </c:forEach>
        </select><br/>

        Ảnh: <input type="file" name="image" accept="image/*"/><br/>

        <c:if test="${not empty product.imagePath}">
            <p>Ảnh hiện tại:</p>
            <img src="${pageContext.request.contextPath}/images/products/${product.imagePath}" width="150"/>
        </c:if>

        <button type="submit">Lưu</button>
    </form>

    <a href="${pageContext.request.contextPath}/product-admin">&laquo; Quay lại danh sách</a>
</body>
</html>
