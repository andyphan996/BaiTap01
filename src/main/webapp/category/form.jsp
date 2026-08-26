<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Category Form</title></head>
<body>
    <h2>${empty category ? "Thêm" : "Sửa"} Category</h2>

    <form method="post" action="${pageContext.request.contextPath}/category"
          enctype="multipart/form-data">

        <input type="hidden" name="id" value="${category.id}"/>

        Tên: <input type="text" name="name" value="${category.name}" required/><br/>

        Mô tả: <textarea name="description">${category.description}</textarea><br/>

        Ảnh: <input type="file" name="image" accept="image/*"/><br/>

        <c:if test="${not empty category.imagePath}">
            <p>Ảnh hiện tại:</p>
            <img src="${pageContext.request.contextPath}/images/${category.imagePath}" width="150"/>
        </c:if>

        <button type="submit">Lưu</button>
    </form>
</body>
</html>