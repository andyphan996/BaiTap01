package iotstar.vn.servlet;

import iotstar.vn.dao.CategoryDAO;
import iotstar.vn.dao.ProductDAO;
import iotstar.vn.entity.Category;
import iotstar.vn.entity.Product;
import iotstar.vn.util.Constants;
import iotstar.vn.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Quản lý CRUD cho Product (dành cho khu vực quản trị, yêu cầu đăng nhập - xem AuthFilter).
 */
@WebServlet("/product-admin")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1MB
    maxFileSize = 1024 * 1024 * 5,        // 5MB / file
    maxRequestSize = 1024 * 1024 * 25     // 25MB / request
)
public class ProductServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.setAttribute("categories", categoryDAO.findAll());
                req.getRequestDispatcher("product-admin/form.jsp").forward(req, resp);
                break;
            case "edit":
                Long id = Long.parseLong(req.getParameter("id"));
                req.setAttribute("product", productDAO.findById(id));
                req.setAttribute("categories", categoryDAO.findAll());
                req.getRequestDispatcher("product-admin/form.jsp").forward(req, resp);
                break;
            case "delete":
                Product toDelete = productDAO.findById(Long.parseLong(req.getParameter("id")));
                if (toDelete != null && toDelete.getImagePath() != null) {
                    File oldFile = new File(Constants.PRODUCT_UPLOAD_DIRECTORY, toDelete.getImagePath());
                    if (oldFile.exists()) oldFile.delete();
                }
                productDAO.delete(Long.parseLong(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/product-admin");
                break;
            default:
                req.setAttribute("products", productDAO.findAll());
                req.getRequestDispatcher("product-admin/list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String priceParam = req.getParameter("price");
        String quantityParam = req.getParameter("quantity");
        String categoryIdParam = req.getParameter("categoryId");

        BigDecimal price;
        int quantity;
        Long categoryId;
        try {
            price = new BigDecimal(priceParam);
            quantity = Integer.parseInt(quantityParam);
            categoryId = Long.parseLong(categoryIdParam);
        } catch (NumberFormatException | NullPointerException e) {
            req.setAttribute("error", "Giá, số lượng và category phải là giá trị hợp lệ.");
            req.setAttribute("categories", categoryDAO.findAll());
            req.getRequestDispatcher("product-admin/form.jsp").forward(req, resp);
            return;
        }
        if (ValidationUtil.isBlank(name) || name.trim().length() > 150
                || (description != null && description.length() > 2000)
                || price.signum() < 0 || price.scale() > 2 || quantity < 0) {
            req.setAttribute("error", "Tên bắt buộc; giá và số lượng không âm; dữ liệu không vượt quá giới hạn cho phép.");
            req.setAttribute("categories", categoryDAO.findAll());
            req.getRequestDispatcher("product-admin/form.jsp").forward(req, resp);
            return;
        }

        Category category = categoryDAO.findById(categoryId);
        if (category == null) {
            req.setAttribute("error", "Category không tồn tại.");
            req.setAttribute("categories", categoryDAO.findAll());
            req.getRequestDispatcher("product-admin/form.jsp").forward(req, resp);
            return;
        }

        Product product;
        if (idParam == null || idParam.isEmpty()) {
            product = new Product();
        } else {
            product = productDAO.findById(Long.parseLong(idParam));
        }

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);

        product.setCategory(category);

        // ----- Xử lý upload ảnh -----
        Part filePart = req.getPart("image"); // đúng tên input trong form

        if (filePart != null && filePart.getSize() > 0) {
            String originalName = filePart.getSubmittedFileName();
            int dot = originalName == null ? -1 : originalName.lastIndexOf('.');
            String ext = dot >= 0 ? originalName.substring(dot).toLowerCase() : "";
            if (!ext.matches("\\.(jpg|jpeg|png|gif|webp)")) {
                req.setAttribute("error", "Chỉ chấp nhận ảnh JPG, PNG, GIF hoặc WEBP.");
                req.setAttribute("product", product);
                req.setAttribute("categories", categoryDAO.findAll());
                req.getRequestDispatcher("product-admin/form.jsp").forward(req, resp);
                return;
            }
            String newFileName = UUID.randomUUID().toString() + ext;

            File uploadDir = new File(Constants.PRODUCT_UPLOAD_DIRECTORY);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Xóa ảnh cũ nếu đang edit và có ảnh cũ
            if (product.getImagePath() != null) {
                File oldFile = new File(uploadDir, product.getImagePath());
                if (oldFile.exists()) oldFile.delete();
            }

            filePart.write(uploadDir.getAbsolutePath() + File.separator + newFileName);
            product.setImagePath(newFileName);
        }

        if (idParam == null || idParam.isEmpty()) {
            productDAO.save(product);
        } else {
            productDAO.update(product);
        }

        resp.sendRedirect(req.getContextPath() + "/product-admin");
    }
}
