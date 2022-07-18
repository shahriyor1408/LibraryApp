package uz.jl.lessontwo.servlets;

import uz.jl.lessontwo.configs.ApplicationContextHolder;
import uz.jl.lessontwo.dao.BookDao;
import uz.jl.lessontwo.domain.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/pendingBooks")
public class PendingBooksServlet extends HttpServlet {
    BookDao bookDao = ApplicationContextHolder.getBean(BookDao.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> books = bookDao.getAllPendingBooks();
        req.setAttribute("books", books);
        RequestDispatcher dispatcher = req.getRequestDispatcher("views/pendingBookPage.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String yes = req.getParameter("yes");
        String no = req.getParameter("no");
        if (Objects.nonNull(yes)) {
            bookDao.confirm(id);
        } else if (Objects.nonNull(no)) {
            bookDao.deny(id);
        }
        resp.sendRedirect("/");
    }
}
