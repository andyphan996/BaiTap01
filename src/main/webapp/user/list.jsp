<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>User List</title></head>
<body>
    <h2>Danh sách User</h2>
    <p>
        Xin chào, ${sessionScope.loggedUser.fullName} |
        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
    </p>
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th><th>Username</th><th>Email</th>
            <th>Họ tên</th><th>Role</th><th>Ngày tạo</th><th>Thao tác</th>
        </tr>
        <c:forEach var="u" items="${users}">
            <tr>
                <td>${u.id}</td>
                <td>${u.username}</td>
                <td>${u.email}</td>
                <td>${u.fullName}</td>
                <td>${u.role}</td>
                <td>${u.createdAt}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/user?action=edit&id=${u.id}">Sửa</a> |
                    <a href="${pageContext.request.contextPath}/user?action=delete&id=${u.id}"
                       onclick="return confirm('Xóa user này?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>