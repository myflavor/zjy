package cn.myflavor.zjy.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter
@Component
public class AdminFilter implements Filter {
    String[] excludeUris = {"/admin/login", "sa-html", "sa-resources", "static", "/admin/logout"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        if (isexcludeUri(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (session.getAttribute("user") != null) {
                if ("/admin/".equals(uri)) {
                    request.getRequestDispatcher("/admin/index.html").forward(servletRequest, servletResponse);
                } else if ("/admin".equals(uri)) {
                    response.sendRedirect("/admin/");
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            } else {
                if ("/admin/index.html".equals(uri) | "/admin/".equals(uri) | "/admin".equals(uri)) {
                    response.sendRedirect("/admin/login.html");
                } else {
                    response.setStatus(403);
                }
            }
        }

    }

    public boolean isexcludeUri(String uri) {
        for (String excludeUrl : excludeUris) {
            if (uri.contains(excludeUrl)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
