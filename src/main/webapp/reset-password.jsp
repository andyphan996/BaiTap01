<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Đặt lại mật khẩu</title></head>
<body>
    <h2>Đặt lại mật khẩu</h2>
    <p>Nhập mã OTP đã gửi tới email <b>${email}</b> và mật khẩu mới.</p>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/reset-password">
        <input type="hidden" name="email" value="${email}"/>
        Mã OTP: <input type="text" name="otp" maxlength="6" required/><br/>
        Mật khẩu mới: <input type="password" name="newPassword" required/><br/>
        Xác nhận mật khẩu: <input type="password" name="confirmPassword" required/><br/>
        <button type="submit">Đặt lại mật khẩu</button>
    </form>

    <p>
        Không nhận được mã?
        <a href="${pageContext.request.contextPath}/forgot-password">Gửi lại yêu cầu</a>
    </p>
    <a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a>
</body>
</html>
