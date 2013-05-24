package servlets;

import java.io.IOException;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.util.List;

import dao.CommentsDAO;
import model.Comment;

public class PageServlet extends HttpServlet {

    private CommentsDAO commentsDAO;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        commentsDAO = new CommentsDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Comment> comments = commentsDAO.list();
        request.setAttribute("comments", comments);

        request.getRequestDispatcher("WEB-INF/page.jsp").forward(request, response);
    }
}