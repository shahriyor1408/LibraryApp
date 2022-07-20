package uz.jl.lessontwo.servlets.book;

import uz.jl.lessontwo.configs.ApplicationContextHolder;
import uz.jl.lessontwo.dao.BookDao;
import uz.jl.lessontwo.domain.Book;
import uz.jl.lessontwo.domain.Uploads;
import uz.jl.lessontwo.enums.BookStatus;
import uz.jl.lessontwo.enums.Language;
import uz.jl.lessontwo.service.FileStorageService;
import uz.jl.lessontwo.service.FileStorageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@WebServlet("/bookAdd")
@MultipartConfig
public class BookAddServlet extends HttpServlet {
    FileStorageService fileStorageService = ApplicationContextHolder.getBean(FileStorageServiceImpl.class);
    BookDao bookDao = ApplicationContextHolder.getBean(BookDao.class);

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String author = req.getParameter("author");
        String genre = req.getParameter("genre");
        String language = req.getParameter("language");
        String description = req.getParameter("description");
        String pageCount = req.getParameter("pageCount");
        Part file = req.getPart("file");

        Uploads upload = fileStorageService.upload(file);
        CompletableFuture<Uploads> future = CompletableFuture.supplyAsync(() -> {
            return fileStorageService.uploadCover(file);
        });

        Uploads uploads;
        try {
            uploads = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        Book book = Book.builder()
                .name(name)
                .author(author)
                .genre(Enum.valueOf(Book.Genre.class, genre.toUpperCase()))
                .description(description)
                .downloadCount(0)
                .language(Enum.valueOf(Language.class, language))
                .pageCount(Integer.parseInt(pageCount))
                .status(BookStatus.PENDING)
                .file(upload)
                .cover(uploads)
                .build();
        bookDao.save(book);
        resp.sendRedirect("/");
    }
}
