<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>User Form</title></head>
<body>
    <h2>Sửa thông tin User</h2>
    <form method="post" action="${pageContext.request.contextPath}/user">
        <input type="hidden" name="id" value="${user.id}"/>

        Username: <input type="text" value="${user.username}" disabled/><br/>

        Họ tên:
        <input type="text" name="fullName" value="${user.fullName}"/><br/>

        Email:
        <input type="email" name="email" value="${user.email}" required/><br/>

        Role:
        <select name="role">
            <option value="USER" ${user.role == 'USER' ? 'selected' : ''}>USER</option>
            <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
        </select><br/>

        Mật khẩu mới (để trống nếu không đổi):
        <input type="password" name="password"/><br/>

        <button type="submit">Lưu</button>
        <a href="${pageContext.request.contextPath}/user">Hủy</a>
    </form>
</body>
</html>