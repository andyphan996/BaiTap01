<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Category List</title></head>
<body>
    <h2>Danh sách Category</h2>

    <a href="${pageContext.request.contextPath}/category?action=new">+ Thêm mới</a>

    <table border="1" cellpadding="5">
        <tr><th>ID</th><th>Ảnh</th><th>Tên</th><th>Mô tả</th><th>Thao tác</th></tr>
        <c:forEach var="c" items="${categories}">
            <tr>
                <td>${c.id}</td>
                <td>
                    <c:if test="${not empty c.imagePath}">
                        <img src="${pageContext.request.contextPath}/images/${c.imagePath}" width="80"/>
                    </c:if>
                </td>
                <td>${c.name}</td>
                <td>${c.description}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/category?action=edit&id=${c.id}">Sửa</a> |
                    <a href="${pageContext.request.contextPath}/category?action=delete&id=${c.id}"
                       onclick="return confirm('Xóa category này?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>