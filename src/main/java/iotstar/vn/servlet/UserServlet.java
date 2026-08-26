package iotstar.vn.servlet;

import iotstar.vn.dao.UserDAO;
import iotstar.vn.entity.User;
import iotstar.vn.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "edit":
                Long id = Long.parseLong(req.getParameter("id"));
                req.setAttribute("user", dao.findById(id));
                req.getRequestDispatcher("user/form.jsp").forward(req, resp);
                break;
            case "delete":
                dao.delete(Long.parseLong(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/user");
                break;
            default:
                req.setAttribute("users", dao.findAll());
                req.getRequestDispatcher("user/list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));
        User user = dao.findById(id);

        user.setFullName(req.getParameter("fullName"));
        user.setEmail(req.getParameter("email"));
        user.setRole(req.getParameter("role"));

        String newPassword = req.getParameter("password");
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(PasswordUtil.hash(newPassword));
        }

        dao.update(user);
        resp.sendRedirect(req.getContextPath() + "/user");
    }
}