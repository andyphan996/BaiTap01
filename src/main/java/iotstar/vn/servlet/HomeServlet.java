package iotstar.vn.servlet;

import iotstar.vn.dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Trang chủ: hiển thị 10 sản phẩm mới nhất.
 */
@WebServlet({"/home"})
public class HomeServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("latestProducts", productDAO.findLatest(10));
        req.getRequestDispatcher("home.jsp").forward(req, resp);
    }
}
