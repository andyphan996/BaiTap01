package iotstar.vn.servlet;

import iotstar.vn.dao.UserDAO;
import iotstar.vn.entity.User;
import iotstar.vn.util.MailUtil;
import iotstar.vn.util.OtpUtil;
import iotstar.vn.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String fullName = req.getParameter("fullName");

        if (userDAO.existsByUsernameOrEmail(username, email)) {
            req.setAttribute("error", "Username hoặc email đã tồn tại!");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordUtil.hash(password));
        user.setEmail(email);
        user.setFullName(fullName);
        user.setRole("USER");
        user.setEnabled(false);

        String otp = OtpUtil.generateOtp();
        user.setOtpCode(otp);
        user.setOtpExpiry(OtpUtil.newExpiry());

        userDAO.save(user);

        try {
            MailUtil.sendActivationOtp(email, otp);
        } catch (RuntimeException e) {
            // Vẫn cho qua để không chặn luồng đăng ký, nhưng báo lỗi rõ ràng để dev biết cấu hình mail sai
            req.setAttribute("error", "Đăng ký thành công nhưng gửi email OTP thất bại: " + e.getMessage());
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/verify-otp?email=" + java.net.URLEncoder.encode(email, "UTF-8"));
    }
}