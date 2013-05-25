package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Comment;
import dao.CommentsDAO;

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