package uz.jl.lessontwo.servlets;

import uz.jl.lessontwo.configs.ApplicationContextHolder;
import uz.jl.lessontwo.dao.BookDao;
import uz.jl.lessontwo.domain.Book;
import uz.jl.lessontwo.enums.Language;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@WebServlet("/")
public class HomeServlet extends HttpServlet {
    BookDao bookDao = ApplicationContextHolder.getBean(BookDao.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String authSession = (String) session.getAttribute("auth_session");

        List<Book> books = bookDao.getAll();

        if (Objects.nonNull(authSession)) {
            req.setAttribute("username", req.getSession().getAttribute("auth_session"));
            req.setAttribute("books", books);
            req.setAttribute("genres", Book.Genre.values());
            req.setAttribute("languages", Language.values());
            RequestDispatcher dispatcher = req.getRequestDispatcher("views/main.jsp");
            dispatcher.forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }
}