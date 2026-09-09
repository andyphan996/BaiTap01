package iotstar.vn.servlet;

import iotstar.vn.dao.CategoryDAO;
import iotstar.vn.entity.Category;
import iotstar.vn.util.Constants;
import iotstar.vn.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/category")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1MB
    maxFileSize = 1024 * 1024 * 5,        // 5MB / file
    maxRequestSize = 1024 * 1024 * 25     // 25MB / request
)
public class CategoryServlet extends HttpServlet {

    private final CategoryDAO dao = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.getRequestDispatcher("category/form.jsp").forward(req, resp);
                break;
            case "edit":
                Long id = Long.parseLong(req.getParameter("id"));
                req.setAttribute("category", dao.findById(id));
                req.getRequestDispatcher("category/form.jsp").forward(req, resp);
                break;
            case "delete":
                Category toDelete = dao.findById(Long.parseLong(req.getParameter("id")));
                if (toDelete != null && toDelete.getImagePath() != null) {
                    File oldFile = new File(Constants.CATEGORY_UPLOAD_DIRECTORY, toDelete.getImagePath());
                    if (oldFile.exists()) oldFile.delete();
                }
                dao.delete(Long.parseLong(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/category");
                break;
            default:
                req.setAttribute("categories", dao.findAll());
                req.getRequestDispatcher("category/list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        Category cat;
        if (idParam == null || idParam.isEmpty()) {
            cat = new Category();
        } else {
            cat = dao.findById(Long.parseLong(idParam));
        }

        if (ValidationUtil.isBlank(name) || name.trim().length() > 100
                || (description != null && description.length() > 255)) {
            req.setAttribute("error", "Tên bắt buộc và không quá 100 ký tự; mô tả không quá 255 ký tự.");
            req.setAttribute("category", cat);
            req.getRequestDispatcher("category/form.jsp").forward(req, resp);
            return;
        }

        cat.setName(name);
        cat.setDescription(description);

        // ----- Xử lý upload ảnh -----
        Part filePart = req.getPart("image"); // đúng tên input trong form

        if (filePart != null && filePart.getSize() > 0) {
            String originalName = filePart.getSubmittedFileName();
            int dot = originalName == null ? -1 : originalName.lastIndexOf('.');
            String ext = dot >= 0 ? originalName.substring(dot).toLowerCase() : "";
            if (!ext.matches("\\.(jpg|jpeg|png|gif|webp)")) {
                req.setAttribute("error", "Chỉ chấp nhận ảnh JPG, PNG, GIF hoặc WEBP.");
                req.setAttribute("category", cat);
                req.getRequestDispatcher("category/form.jsp").forward(req, resp);
                return;
            }
            String newFileName = UUID.randomUUID().toString() + ext;

            File uploadDir = new File(Constants.CATEGORY_UPLOAD_DIRECTORY);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Xóa ảnh cũ nếu đang edit và có ảnh cũ
            if (cat.getImagePath() != null) {
                File oldFile = new File(uploadDir, cat.getImagePath());
                if (oldFile.exists()) oldFile.delete();
            }

            filePart.write(uploadDir.getAbsolutePath() + File.separator + newFileName);
            cat.setImagePath(newFileName);
        }

        if (idParam == null || idParam.isEmpty()) {
            dao.save(cat);
        } else {
            dao.update(cat);
        }

        resp.sendRedirect(req.getContextPath() + "/category");
    }
}