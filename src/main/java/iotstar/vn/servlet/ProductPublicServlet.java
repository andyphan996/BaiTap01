package iotstar.vn.servlet;

import iotstar.vn.dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


@WebServlet("/product")
public class ProductPublicServlet extends HttpServlet {

    private static final int PAGE_SIZE = 6;

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int page = 1;
        String pageParam = req.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException ignored) {
                page = 1;
            }
        }
        if (page < 1) page = 1;

        long totalItems = productDAO.countAll();
        int totalPages = (int) Math.ceil(totalItems / (double) PAGE_SIZE);
        if (totalPages < 1) totalPages = 1;
        if (page > totalPages) page = totalPages;

        req.setAttribute("products", productDAO.findPage(page, PAGE_SIZE));
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("product/list.jsp").forward(req, resp);
    }
}
