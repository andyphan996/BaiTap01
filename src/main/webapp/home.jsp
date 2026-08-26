<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Trang chủ</title></head>
<body>
    <h2>Chào mừng, ${sessionScope.loggedUser.fullName} (${sessionScope.loggedUser.role})</h2>

    <p>
        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
    </p>

    <ul>
        <li><a href="${pageContext.request.contextPath}/category">Quản lý Category</a></li>
        <c:if test="${sessionScope.loggedUser.role == 'ADMIN'}">
            <li><a href="${pageContext.request.contextPath}/user">Quản lý User</a></li>
        </c:if>
    </ul>
</body>
</html>