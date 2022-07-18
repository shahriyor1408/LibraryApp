package uz.jl.lessontwo.servlets.book;

import uz.jl.lessontwo.configs.ApplicationContextHolder;
import uz.jl.lessontwo.domain.Uploads;
import uz.jl.lessontwo.exceptions.NotFoundException;
import uz.jl.lessontwo.service.FileStorageService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@WebServlet("/downloadFile")
public class DownloadFileServlet extends HttpServlet {
    FileStorageService fileStorageService = ApplicationContextHolder.getBean(FileStorageService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestedFile = req.getParameter("file");
        Uploads uploads = fileStorageService.getByPath(requestedFile);
        if (Objects.isNull(uploads)) {
            throw new NotFoundException("Path not found!");
        }
        resp.setContentType(uploads.getContentType());
        resp.setHeader("Content-disposition", "attachment; filename=" + uploads.getOriginalName());
        Path path = Paths.get("/home/shahriyor/IdeaProjects/lesson-two/apps/library", uploads.getPath());
        ServletOutputStream outputStream = resp.getOutputStream();
        Files.copy(path, outputStream);
    }
}
