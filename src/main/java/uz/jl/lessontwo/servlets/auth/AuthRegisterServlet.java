package uz.jl.lessontwo.servlets.auth;

import uz.jl.lessontwo.configs.ApplicationContextHolder;
import uz.jl.lessontwo.configs.PasswordEncoder;
import uz.jl.lessontwo.dao.UserDao;
import uz.jl.lessontwo.domain.User;
import uz.jl.lessontwo.enums.UserStatus;
import uz.jl.lessontwo.exceptions.InvalidInputException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@WebServlet("/register")
public class AuthRegisterServlet extends HttpServlet {

    private final UserDao userDao = ApplicationContextHolder.getBean(UserDao.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("views/auth/register.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = userDao.findByUsername(username);
        if (Objects.nonNull(user))
            throw new InvalidInputException("'%s' Username already taken".formatted(username));
        user = User.builder()
                .username(username)
                .password(PasswordEncoder.encode(password))
                .userStatus(UserStatus.USER)
                .build();
        userDao.save(user);

        resp.sendRedirect("/login");
    }


}

