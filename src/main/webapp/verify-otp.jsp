<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Xác thực OTP</title></head>
<body>
    <h2>Xác thực tài khoản</h2>
    <p>Nhập mã OTP đã được gửi tới email <b>${email}</b>. Mã có hiệu lực trong 5 phút.</p>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
    <c:if test="${not empty message}">
        <p style="color:green">${message}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/verify-otp">
        <input type="hidden" name="email" value="${email}"/>
        Mã OTP: <input type="text" name="otp" inputmode="numeric" pattern="[0-9]{6}" maxlength="6" required/><br/>
        <button type="submit">Xác thực</button>
    </form>

    <p>
        Không nhận được mã?
        <a href="${pageContext.request.contextPath}/verify-otp?action=resend&email=${email}">Gửi lại OTP</a>
    </p>
    <a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a>
</body>
</html>
