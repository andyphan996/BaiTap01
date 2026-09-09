package iotstar.vn.servlet;

import iotstar.vn.dao.UserDAO;
import iotstar.vn.entity.User;
import iotstar.vn.util.MailUtil;
import iotstar.vn.util.OtpUtil;
import iotstar.vn.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("forgot-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        if (!ValidationUtil.isValidEmail(email)) {
            req.setAttribute("error", "Email không hợp lệ.");
            req.getRequestDispatcher("forgot-password.jsp").forward(req, resp);
            return;
        }
        User user = userDAO.findByEmail(email);

        if (user == null) {
            req.setAttribute("error", "Không tìm thấy tài khoản với email này.");
            req.getRequestDispatcher("forgot-password.jsp").forward(req, resp);
            return;
        }

        String otp = OtpUtil.generateOtp();
        user.setOtpCode(otp);
        user.setOtpExpiry(OtpUtil.newExpiry());
        userDAO.update(user);

        try {
            MailUtil.sendResetPasswordOtp(email, otp);
        } catch (RuntimeException e) {
            req.setAttribute("error", "Gửi email OTP thất bại: " + e.getMessage());
            req.getRequestDispatcher("forgot-password.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/reset-password?email=" + java.net.URLEncoder.encode(email, "UTF-8"));
    }
}
