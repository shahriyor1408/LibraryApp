package uz.jl.lessontwo.filters;

import uz.jl.lessontwo.configs.ApplicationContextHolder;
import uz.jl.lessontwo.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalFilter implements Filter {
    public static List<String> WHITE_LIST = new ArrayList<>(Arrays.asList("/login", "/register"));
    UserDao userDao = ApplicationContextHolder.getBean(UserDao.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (WHITE_LIST.contains(req.getRequestURI())) {
            chain.doFilter(req, response);
            return;
        }
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("session_user")) {
                    String username = cookie.getValue();
                    req.setAttribute("user", userDao.findByUsername(username));
                    chain.doFilter(req, response);
                    return;
                }
            }
        }
        ((HttpServletResponse) response).sendRedirect("/login");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
