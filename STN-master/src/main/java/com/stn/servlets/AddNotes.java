package com.stn.servlets;

import com.stn.helpers.OrarHelper;
import com.stn.helpers.UserHelper;
import com.stn.pojo.Orar;
import com.stn.pojo.User;

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

@WebServlet("/AddNotes")
public class AddNotes extends HttpServlet {
    private void setTempFields(HttpServletRequest request, Integer id_ora, Integer id_stud, String teme, String examen, Integer nota) {
        HttpSession session = request.getSession();
        session.setAttribute("id_stud", id_stud);
        session.setAttribute("id_ora", id_ora);
        session.setAttribute("teme", teme);
        session.setAttribute("examen", examen);
        session.setAttribute("nota", nota);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String error = "";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Integer id_ora = Integer.valueOf(request.getParameter("id_ora"));
        Integer id_student = Integer.valueOf(request.getParameter("id_student"));
        String url = "ora1.jsp?id=" + id_ora;
        OrarHelper addNotes = new OrarHelper();
        UserHelper user = new UserHelper();
        ArrayList<Orar> orar = null;
        User user1 = user.getUserInfo(id_student);
        try {
            orar = addNotes.getOrar(user1.getIdGrupa());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Orar ora = addNotes.getOra(id_ora, orar);
        try {
            if (addNotes.getSchool(id_ora, id_student).isEmpty()) {
                addNotes.addNotes(user1.getIdGrupa(), user1.getId(), ora.getName(), ora.getDurata(), ora.getTip_act(), ora.getSgr(), ora.getSala(), ora.getNume_prof(), ora.getZi(), ora.getGrupa(), ora.getSapt());
                error = "Aceasta este pagina cu notite";
            }else
                error="Buna!";
        } catch (SQLException | ClassNotFoundException e) {
            out.println(e);
            return;
        }
        session.setAttribute("error", error);
        response.sendRedirect(url);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String error = "";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        Integer id_ora = Integer.valueOf(request.getParameter("id_ora"));
        Integer id_student = Integer.valueOf(request.getParameter("id_user"));
        String url = "ora1.jsp?id=" + id_ora;
        String teme = request.getParameter("teme");
        String examen = request.getParameter("examen");
        Integer nota = Integer.valueOf(request.getParameter("nota"));
        OrarHelper addNotes = new OrarHelper();
        try {
            addNotes.updateNotes(id_ora, id_student, teme, examen, nota);
        } catch (SQLException | ClassNotFoundException e) {
            out.println(e);
            return;
        }
        error = "Schimbat";
        session.setAttribute("error", error);
        response.sendRedirect(url);
    }
}
