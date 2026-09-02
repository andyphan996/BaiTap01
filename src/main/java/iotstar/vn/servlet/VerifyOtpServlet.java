package iotstar.vn.servlet;

import iotstar.vn.dao.UserDAO;
import iotstar.vn.entity.User;
import iotstar.vn.util.MailUtil;
import iotstar.vn.util.OtpUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/verify-otp")
public class VerifyOtpServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String email = req.getParameter("email");

        if ("resend".equals(action)) {
            User user = (email != null) ? userDAO.findByEmail(email) : null;
            if (user != null && !user.isEnabled()) {
                String otp = OtpUtil.generateOtp();
                user.setOtpCode(otp);
                user.setOtpExpiry(OtpUtil.newExpiry());
                userDAO.update(user);
                try {
                    MailUtil.sendActivationOtp(user.getEmail(), otp);
                    req.setAttribute("message", "Đã gửi lại mã OTP mới, vui lòng kiểm tra email.");
                } catch (RuntimeException e) {
                    req.setAttribute("error", "Gửi lại OTP thất bại: " + e.getMessage());
                }
            }
        }

        req.setAttribute("email", email);
        req.getRequestDispatcher("verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String otp = req.getParameter("otp");

        User user = userDAO.findByEmail(email);

        if (user == null) {
            req.setAttribute("error", "Không tìm thấy tài khoản với email này.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("verify-otp.jsp").forward(req, resp);
            return;
        }

        if (user.isEnabled()) {
            resp.sendRedirect(req.getContextPath() + "/login?verified=1");
            return;
        }

        if (!OtpUtil.isValid(otp, user.getOtpCode(), user.getOtpExpiry())) {
            req.setAttribute("error", "Mã OTP không đúng hoặc đã hết hạn. Vui lòng thử lại hoặc gửi lại mã.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("verify-otp.jsp").forward(req, resp);
            return;
        }

        user.setEnabled(true);
        user.setOtpCode(null);
        user.setOtpExpiry(null);
        userDAO.update(user);

        resp.sendRedirect(req.getContextPath() + "/login?verified=1");
    }
}
