package uz.jl.lessontwo.servlets.auth;

import uz.jl.lessontwo.configs.ApplicationContextHolder;
import uz.jl.lessontwo.configs.PasswordEncoder;
import uz.jl.lessontwo.dao.UserDao;
import uz.jl.lessontwo.domain.User;
import uz.jl.lessontwo.exceptions.AuthenticationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/login")
public class AuthLoginServlet extends HttpServlet {
    private final UserDao userDao = ApplicationContextHolder.getBean(UserDao.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("views/auth/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = userDao.findByUsername(username);

        if (Objects.isNull(user) || !PasswordEncoder.match(password, user.getPassword())) {
            throw new AuthenticationException("Bad Credentials");
        }

        Cookie cookie = new Cookie("session_user", user.getUsername());
        cookie.setMaxAge(900);
        resp.addCookie(cookie);
        HttpSession session = req.getSession();
        session.setAttribute("auth_session", username);
        resp.sendRedirect("/");
    }
}
