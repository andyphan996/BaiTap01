package iotstar.vn.servlet;

import iotstar.vn.dao.UserDAO;
import iotstar.vn.entity.User;
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

        userDAO.save(user);

        resp.sendRedirect(req.getContextPath() + "/login?registered=1");
    }
}