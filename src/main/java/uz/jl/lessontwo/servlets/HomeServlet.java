package uz.jl.lessontwo.servlets;

import uz.jl.lessontwo.domain.Book;

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
    private List<Book> books;

    @Override
    public void init() throws ServletException {
        books = List.of(
                new Book(1, "Otgan kunlar", "Abdulla Qodiriy"),
                new Book(2, "Rich Dad, Rich Mam", "Robert Kiyosaki")
        );

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String authSession = (String) session.getAttribute("auth_session");

        if (Objects.nonNull(authSession)) {
            req.setAttribute("username", req.getSession().getAttribute("auth_session"));
            req.setAttribute("books", books);
            RequestDispatcher dispatcher = req.getRequestDispatcher("views/main.jsp");
            dispatcher.forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }
}