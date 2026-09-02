<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Quên mật khẩu</title></head>
<body>
    <h2>Quên mật khẩu</h2>
    <p>Nhập email tài khoản của bạn, hệ thống sẽ gửi mã OTP để đặt lại mật khẩu.</p>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/forgot-password">
        Email: <input type="email" name="email" required/><br/>
        <button type="submit">Gửi mã OTP</button>
    </form>
    <a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a>
</body>
</html>
