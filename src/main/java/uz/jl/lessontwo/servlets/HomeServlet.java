package uz.jl.lessontwo.servlets;

import uz.jl.lessontwo.configs.ApplicationContextHolder;
import uz.jl.lessontwo.dao.BookDao;
import uz.jl.lessontwo.dao.UserDao;
import uz.jl.lessontwo.domain.Book;
import uz.jl.lessontwo.domain.User;
import uz.jl.lessontwo.enums.Language;
import uz.jl.lessontwo.enums.UserStatus;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Stream;

@WebServlet("/")
public class HomeServlet extends HttpServlet {
    BookDao bookDao = ApplicationContextHolder.getBean(BookDao.class);
    private final UserDao userDao = ApplicationContextHolder.getBean(UserDao.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String authSession = (String) session.getAttribute("auth_session");
        String search = req.getParameter("search");
        if (Objects.isNull(search)) {
            search = "";
        }
        List<Book> books = bookDao.getAll(search);

        if (Objects.nonNull(authSession)) {
            req.setAttribute("search", search);
            req.setAttribute("username", req.getSession().getAttribute("auth_session"));
            req.setAttribute("genres", Book.Genre.values());
            req.setAttribute("languages", Language.values());
            User user = userDao.findByUsername(authSession);

            int page = 1;
            int recordsPerPage = 6;
            if (req.getParameter("page") != null)
                page = Integer.parseInt(req.getParameter("page"));

            List<Book> list = bookDao.viewAllBooks((page - 1) * recordsPerPage, recordsPerPage, search);
            int noOfRecords = books.size();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            req.setAttribute("books", list);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);

            RequestDispatcher dispatcher;
            if (user.getUserStatus().equals(UserStatus.USER)) {
                dispatcher = req.getRequestDispatcher("views/main.jsp");
            } else {
                dispatcher = req.getRequestDispatcher("views/adminPage.jsp");
            }
            dispatcher.forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }
}