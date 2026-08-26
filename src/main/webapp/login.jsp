<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Login</title></head>
<body>
    <h2>Đăng nhập</h2>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <c:if test="${param.registered == '1'}">
        <p style="color:green">Đăng ký thành công, mời đăng nhập!</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/login">
        Username: <input type="text" name="username" required/><br/>
        Password: <input type="password" name="password" required/><br/>
        <button type="submit">Đăng nhập</button>
    </form>
    <a href="${pageContext.request.contextPath}/register">Đăng ký tài khoản mới</a>
</body>
</html>