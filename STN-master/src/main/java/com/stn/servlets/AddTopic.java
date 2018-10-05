package com.stn.servlets;

import com.stn.helpers.TopicHelper;
import com.stn.utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/AddTopic")
public class AddTopic extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        HttpSession session = request.getSession();

        String url = "view_forum.jsp?id="+request.getParameter("group_id");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String error = "";

        String title = request.getParameter("topic_name");
        String body = request.getParameter("body");
        int group_id = Integer.parseInt(request.getParameter("group_id"));
        int idUser = (int) session.getAttribute("userId");
        TopicHelper topicHelper = new TopicHelper();

        if (Validator.isEmpty(title,body)) {
            error = "<br/><b style='color: #db5555; padding-top: 5pt'>You must fill all the requiered fields!</b>";
        } else {
            try {
                topicHelper.addTopic(title,body,idUser,group_id);
            } catch (SQLException | ClassNotFoundException e) {
                out.println(e);
            }
        }

        session.setAttribute("error",error);
        response.sendRedirect(url);

    }
}
