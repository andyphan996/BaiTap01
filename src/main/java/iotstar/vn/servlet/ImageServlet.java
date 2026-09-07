package iotstar.vn.servlet;

import iotstar.vn.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Phục vụ file ảnh đã upload.
 * URL dạng: /images/categories/{fileName}, /images/products/{fileName} hoặc /images/users/{fileName}
 */
@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // pathInfo dạng "/categories/xxx.jpg" hoặc "/products/xxx.jpg"
        String[] parts = pathInfo.substring(1).split("/", 2);
        if (parts.length < 2 || parts[1].isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String type = parts[0];
        String fileName = parts[1];

        String baseDir;
        switch (type) {
            case "categories":
                baseDir = Constants.CATEGORY_UPLOAD_DIRECTORY;
                break;
            case "products":
                baseDir = Constants.PRODUCT_UPLOAD_DIRECTORY;
                break;
            case "users":
                baseDir = Constants.USER_UPLOAD_DIRECTORY;
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
        }

        File file = new File(baseDir, fileName);
        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType = getServletContext().getMimeType(file.getAbsolutePath());
        resp.setContentType(mimeType != null ? mimeType : "application/octet-stream");
        resp.setContentLengthLong(file.length());

        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = resp.getOutputStream()) {
            in.transferTo(out);
        }
    }
}
