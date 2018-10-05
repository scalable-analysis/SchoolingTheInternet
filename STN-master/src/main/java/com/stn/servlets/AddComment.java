package com.stn.servlets;

import com.stn.helpers.CommentsHelper;
import com.stn.helpers.UserHelper;
import com.stn.utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/AddComment")
public class AddComment extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        HttpSession session=request.getSession();
        PrintWriter out=response.getWriter();
        int idPost=Integer.parseInt(request.getParameter("idPost"));
        int idUser=(int) session.getAttribute("userId");
        CommentsHelper commHelp=new CommentsHelper();
        String replyee=null;
        int reply_temp;
        try {
            reply_temp = Integer.parseInt(request.getParameter("idReply"));
        }catch(Exception e){reply_temp=-1;}
        try {
            if(reply_temp!=-1)
            replyee = commHelp.getComment(reply_temp).getCont()+"\n"+commHelp.getComment(reply_temp).getUsername()+" on "+commHelp.getComment(reply_temp).getDop();
        }catch (Exception e){e.printStackTrace();
        replyee=null;}
        String error="";
        String url="/topic.jsp?id="+idPost;
        String body=request.getParameter("body");
        CommentsHelper commentsHelper=new CommentsHelper();

        if(Validator.isEmpty(body)){
            error="<br/><b>Nu ai scris nimic</b>";
        }
        else{
            if(replyee!=null){
                try {
                    commentsHelper.addReply(idPost,idUser,body,replyee);
                }catch (SQLException|ClassNotFoundException e){
                    out.println(e);
                }
            }
            else
            if(idPost>0)
            try {
                commentsHelper.addComment(idPost,idUser,body);
            }catch (SQLException|ClassNotFoundException e){
                out.println(e);
            }
        }
        session.setAttribute("error",error);
        response.sendRedirect(url);
    }
}
