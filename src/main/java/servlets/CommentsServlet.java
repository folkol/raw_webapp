package servlets;

import java.io.IOException;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import dao.CommentsDAO;
import model.Comment;

public class CommentsServlet extends HttpServlet {

    private CommentsDAO commentsDAO;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        commentsDAO = new CommentsDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String alias = request.getParameter("alias");
        String message = request.getParameter("message");
        Comment comment = new Comment(alias, message);
        commentsDAO.store(comment);

        response.sendRedirect(request.getContextPath());
    }
}