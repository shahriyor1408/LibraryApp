package uz.jl.lessontwo.servlets.book;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/showCover")
public class ShowCoverServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String img = req.getParameter("img");
        ServletOutputStream outputStream = resp.getOutputStream();
        Path path = Path.of("/home/shahriyor/IdeaProjects/lesson-two/apps/library" + img);
        Files.copy(path, outputStream);
    }
}
