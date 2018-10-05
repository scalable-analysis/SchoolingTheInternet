package com.stn.servlets;

import com.stn.helpers.CategoryHelper;
import com.stn.pojo.Category;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CategoryProcess")
public class CategoryProcess extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("category.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        String result = "";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String url = "category.jsp";

        CategoryHelper categoryHelper = new CategoryHelper();
        String type = request.getParameter("type");
        String categorie = request.getParameter("categorie");
        String descriere = request.getParameter("descriere");
        int idFacultate = Integer.parseInt(request.getParameter("idFacultate"));
        int idSerie = Integer.parseInt(request.getParameter("idSerie"));
        int idGrupa = Integer.parseInt(request.getParameter("idGrupa"));
        String pinned= request.getParameter("pinned");
        int is_pinned;

        if(pinned == null){
            is_pinned = 0;
        } else {
            is_pinned = 1;
        }

        if(Validator.isEmpty(categorie)) {
            result = "You must add a category!";
        } else if(Validator.isEmpty(descriere)){
            result = "You must add a description!";
        } else {
            try {
                categoryHelper.addCategory(categorie, descriere, idFacultate, idSerie, idGrupa, is_pinned);
                result = "Succes!";
            } catch (ClassNotFoundException | SQLException e) {
                out.println(e);
                return;
            }
        }

        response.sendRedirect(url);
    }
}