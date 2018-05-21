package com.stn.servlets;

import com.stn.helpers.UserHelper;
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

@WebServlet("/EditUser")
public class EditUser extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("editUser.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        String error = "";
        response.setContentType("text/html");

        UserHelper userHelper = new UserHelper();
        int id = Integer.parseInt(request.getParameter("idaplicatie"));

        String username = request.getParameter("user");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        String avatar = request.getParameter("avatar");
        String url = "/editUser.jsp?id=" + id;

        if (Validator.isEmpty(username, email, firstName, lastName)) {
            error = "You must fill all the requiered fields!";
            url = "/editUser.jsp?id=" + id;
        } else if (!Validator.between(username, 3, 20)) {
            error = "Username must be between 3 and 20 characters!";
            url = "/editUser.jsp?id=" + id;
        } else if (!Validator.between(firstName, 2, 30)) {
            error = "Firstname must be between 2 and 30 characters!";
            url = "/editUser.jsp?id=" + id;
        } else if (!Validator.isName(firstName)) {
            error = "Invalid Firstname!";
            url = "/editUser.jsp?id=" + id;
        } else if (!Validator.between(lastName, 2, 30)) {
            error = "Lastname must be between 2 and 30 characters!";
            url = "/editUser.jsp?id=" + id;
        } else if (!Validator.isName(lastName)) {
            error = "Invalid Lastname!";
            url = "/editUser.jsp?id=" + id;
        } else if (!Validator.between(email, 6, 30)) {
            error = "Email must be between 6 and 30 characters!";
            url = "/editUser.jsp?id=" + id;
        } else if (!Validator.isEmail(email)) {
            error = "Invalid email addres!";
            url = "/editUser.jsp?id=" + id;
        } else if (!Validator.isURL(avatar)) {
            error = " Invalid avatar!";
            url = "/editUser.jsp?id=" + id;
        } else {
                try {
                    userHelper.updateUser(id, username, firstName, lastName, email, avatar);
                    error = "Update completed!";
                } catch (ClassNotFoundException | SQLException e) {
                    out.println(e);
                    return;
                }
        }

        session.setAttribute("error", error);
        response.sendRedirect(url);
    }

}
