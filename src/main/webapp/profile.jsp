<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Thông tin cá nhân</title></head>
<body>
    <h2>Thông tin cá nhân</h2>
    <c:if test="${param.success == '1'}">
        <p style="color: green">Cập nhật thông tin thành công.</p>
    </c:if>
    <c:if test="${not empty error}">
        <p style="color: red">${error}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/profile" enctype="multipart/form-data">
        <p>Username: <strong>${profileUser.username}</strong></p>
        <p>Email: <strong>${profileUser.email}</strong></p>
        <p>Họ tên: <input type="text" name="fullName" value="${profileUser.fullName}" maxlength="100" required></p>
        <p>Số điện thoại: <input type="tel" name="phone" value="${profileUser.phone}" maxlength="15" pattern="[+]?[0-9]{10,15}"></p>
        <p>Ảnh đại diện: <input type="file" name="image" accept=".jpg,.jpeg,.png,.gif,.webp"></p>
        <c:if test="${not empty profileUser.imagePath}">
            <p><img src="${pageContext.request.contextPath}/images/users/${profileUser.imagePath}" alt="Ảnh đại diện" width="140"></p>
        </c:if>
        <button type="submit">Lưu thay đổi</button>
    </form>
</body>
</html>