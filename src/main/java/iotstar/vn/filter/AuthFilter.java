package iotstar.vn.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String path = request.getRequestURI().substring(request.getContextPath().length());

        boolean isPublic = path.equals("/") || path.equals("/index.jsp")
                || path.equals("/login") || path.equals("/register")
                || path.equals("/verify-otp")
                || path.equals("/forgot-password") || path.equals("/reset-password")
                || path.equals("/home") || path.equals("/product") || path.equals("/product-detail")
                || path.startsWith("/images/")
                || path.startsWith("/assets") || path.endsWith(".css") || path.endsWith(".js");

        boolean loggedIn = (session != null && session.getAttribute("loggedUser") != null);

        if (isPublic || loggedIn) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}