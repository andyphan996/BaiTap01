package iotstar.vn.servlet;

import iotstar.vn.dao.UserDAO;
import iotstar.vn.entity.User;
import iotstar.vn.util.Constants;
import iotstar.vn.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/profile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 6
)
public class ProfileServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User loggedUser = (User) session.getAttribute("loggedUser");
        User user = userDAO.findById(loggedUser.getId());
        req.setAttribute("profileUser", user);
        req.getRequestDispatcher("profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User loggedUser = (User) session.getAttribute("loggedUser");
        User user = userDAO.findById(loggedUser.getId());

        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");
        if (ValidationUtil.isBlank(fullName) || fullName.length() > 100
                || !ValidationUtil.isValidPhone(phone)) {
            req.setAttribute("error", "Họ tên bắt buộc và số điện thoại phải có 10-15 chữ số.");
            req.setAttribute("profileUser", user);
            req.getRequestDispatcher("profile.jsp").forward(req, resp);
            return;
        }

        user.setFullName(fullName.trim());
        user.setPhone(ValidationUtil.isBlank(phone) ? null : phone.trim());

        Part filePart = req.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            String originalName = filePart.getSubmittedFileName();
            String extension = "";
            int dot = originalName == null ? -1 : originalName.lastIndexOf('.');
            if (dot >= 0) extension = originalName.substring(dot).toLowerCase();
            if (!extension.matches("\\.(jpg|jpeg|png|gif|webp)")) {
                req.setAttribute("error", "Chỉ chấp nhận ảnh JPG, PNG, GIF hoặc WEBP.");
                req.setAttribute("profileUser", user);
                req.getRequestDispatcher("profile.jsp").forward(req, resp);
                return;
            }

            File uploadDir = new File(Constants.USER_UPLOAD_DIRECTORY);
            if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                throw new IOException("Không thể tạo thư mục lưu ảnh profile");
            }
            if (user.getImagePath() != null) {
                File oldFile = new File(uploadDir, user.getImagePath());
                if (oldFile.exists()) oldFile.delete();
            }

            String newFileName = UUID.randomUUID() + extension;
            filePart.write(new File(uploadDir, newFileName).getAbsolutePath());
            user.setImagePath(newFileName);
        }

        userDAO.update(user);
        session.setAttribute("loggedUser", user);
        resp.sendRedirect(req.getContextPath() + "/profile?success=1");
    }
}