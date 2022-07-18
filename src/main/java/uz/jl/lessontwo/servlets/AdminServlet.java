package uz.jl.lessontwo.servlets;

import uz.jl.lessontwo.configs.ApplicationContextHolder;
import uz.jl.lessontwo.dao.BookDao;
import uz.jl.lessontwo.domain.Book;
import uz.jl.lessontwo.exceptions.NotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/adminPage")
public class AdminServlet extends HttpServlet {
    BookDao bookDao = ApplicationContextHolder.getBean(BookDao.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Book book = bookDao.getById(id);
        if (Objects.isNull(book)) {
            throw new NotFoundException("Book not found!");
        }
        bookDao.confirm(id);
        resp.sendRedirect("/");
    }
}
