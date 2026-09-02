package iotstar.vn.servlet;

import iotstar.vn.dao.UserDAO;
import iotstar.vn.entity.User;
import iotstar.vn.util.OtpUtil;
import iotstar.vn.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("email", req.getParameter("email"));
        req.getRequestDispatcher("reset-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String otp = req.getParameter("otp");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        User user = userDAO.findByEmail(email);

        if (user == null) {
            req.setAttribute("error", "Không tìm thấy tài khoản với email này.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("reset-password.jsp").forward(req, resp);
            return;
        }

        if (!OtpUtil.isValid(otp, user.getOtpCode(), user.getOtpExpiry())) {
            req.setAttribute("error", "Mã OTP không đúng hoặc đã hết hạn.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("reset-password.jsp").forward(req, resp);
            return;
        }

        if (newPassword == null || newPassword.isEmpty() || !newPassword.equals(confirmPassword)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("reset-password.jsp").forward(req, resp);
            return;
        }

        user.setPassword(PasswordUtil.hash(newPassword));
        user.setOtpCode(null);
        user.setOtpExpiry(null);
        userDAO.update(user);

        resp.sendRedirect(req.getContextPath() + "/login?reset=1");
    }
}
