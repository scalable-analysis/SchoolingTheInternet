package com.stn.servlets;

import com.stn.helpers.CommentsHelper;
import com.stn.helpers.UserHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/EditComment")
public class EditComment extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String error = "";
        Integer idPost=Integer.parseInt(request.getParameter("idp"));
        String url = "/topic.jsp?id="+idPost;
        response.setContentType("text/html");
        CommentsHelper commHelper = new CommentsHelper();

        int idComment = Integer.parseInt(request.getParameter("id"));

        try {
            commHelper.deleteComment(idComment);
        } catch (SQLException | ClassNotFoundException e) {
            out.println(e);
        }

        response.sendRedirect(url);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String error = "";
        Integer idPost=Integer.parseInt(request.getParameter("comm_id_post_temp"));
        String url = "/topic.jsp?id="+idPost;
        response.setContentType("text/html");

        UserHelper userHelper = new UserHelper();

        String body = "";


            body = request.getParameter("comm_body_temp");


        int idComment = Integer.parseInt(request.getParameter("comm_id_temp"));
        CommentsHelper commHelper = new CommentsHelper();

            try {
                commHelper.editComment(idComment,body);
            } catch (SQLException | ClassNotFoundException e) {
                out.println(e);
            }


        response.sendRedirect(url);

    }
}
