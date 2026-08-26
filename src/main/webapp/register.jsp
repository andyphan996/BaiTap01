<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Register</title></head>
<body>
    <h2>Đăng ký</h2>
    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/register">
        Username: <input type="text" name="username" required/><br/>
        Password: <input type="password" name="password" required/><br/>
        Email: <input type="email" name="email" required/><br/>
        Họ tên: <input type="text" name="fullName"/><br/>
        <button type="submit">Đăng ký</button>
    </form>
</body>
</html>