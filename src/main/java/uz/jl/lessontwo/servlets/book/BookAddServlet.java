package uz.jl.lessontwo.servlets.book;

import uz.jl.lessontwo.configs.ApplicationContextHolder;
import uz.jl.lessontwo.dao.BookDao;
import uz.jl.lessontwo.domain.Book;
import uz.jl.lessontwo.domain.Uploads;
import uz.jl.lessontwo.enums.BookStatus;
import uz.jl.lessontwo.enums.Language;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bookAdd")
public class BookAddServlet extends HttpServlet {
    BookDao bookDao = ApplicationContextHolder.getBean(BookDao.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String author = req.getParameter("author");
        String genre = req.getParameter("genre");
        String language = req.getParameter("language");
        String description = req.getParameter("description");
        String pageCount = req.getParameter("pageCount");
        Book book = Book.builder()
                .name(name)
                .author(author)
                .genre(Enum.valueOf(Book.Genre.class, genre.toUpperCase()))
                .description(description)
                .downloadCount(0)
                .language(Enum.valueOf(Language.class, language))
                .pageCount(Integer.parseInt(pageCount))
                .status(BookStatus.ACTIVE)
                .build();
        bookDao.save(book);
        resp.sendRedirect("/");
    }
}
