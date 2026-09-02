package iotstar.vn.servlet;

import iotstar.vn.dao.ProductDAO;
import iotstar.vn.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Trang chi tiết 1 sản phẩm.
 * URL: /product-detail?id=X
 */
@WebServlet("/product-detail")
public class ProductDetailServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendRedirect(req.getContextPath() + "/product");
            return;
        }

        Product product;
        try {
            product = productDAO.findById(Long.parseLong(idParam));
        } catch (NumberFormatException e) {
            product = null;
        }

        if (product == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm.");
            return;
        }

        req.setAttribute("product", product);
        req.getRequestDispatcher("product/detail.jsp").forward(req, resp);
    }
}
