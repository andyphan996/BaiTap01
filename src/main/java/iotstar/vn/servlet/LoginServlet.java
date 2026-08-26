package iotstar.vn.servlet;

import iotstar.vn.dao.UserDAO;
import iotstar.vn.entity.User;
import iotstar.vn.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userDAO.findByUsername(username);

        if (user == null || !PasswordUtil.matches(password, user.getPassword())) {
            req.setAttribute("error", "Sai username hoặc password!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("loggedUser", user);

        resp.sendRedirect(req.getContextPath() + "/home.jsp");
    }
}