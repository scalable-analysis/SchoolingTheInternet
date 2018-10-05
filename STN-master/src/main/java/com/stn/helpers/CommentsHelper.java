package com.stn.helpers;

import com.stn.pojo.Comments;
import com.stn.utils.DBConnection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentsHelper extends DBConnection {
    public List<Comments> getComments(int idPost) throws ClassNotFoundException, SQLException {
        List<Comments> comm = new ArrayList<Comments>();


            query = "SELECT n.Id,Continut,Data,IdPost,n.IdUser,Username,Class,LastEdit FROM comments n " +
                    "JOIN users u ON u.Id = n.IdUser " +
                    "WHERE n.IdPost = ? ORDER BY Data";


        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(this.getHost(), this.getUser(), this.getPassword());
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idPost);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Comments commtemp = new Comments();
                commtemp.setId(resultSet.getInt(1));
                commtemp.setCont(resultSet.getString(2));
                commtemp.setDop(resultSet.getTimestamp(3));
                commtemp.setIdPost(resultSet.getInt(4));
                commtemp.setIdUser(resultSet.getInt(5));
                commtemp.setUsername(resultSet.getString(6));
                commtemp.setUserClass(resultSet.getInt(7));
                commtemp.setLastEdit(resultSet.getTimestamp(8));
                comm.add(commtemp);
            }
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        }

        return comm;
    }

    public Comments getComment(Integer idComment)throws ClassNotFoundException, SQLException{
        Comments comm=new Comments();

        query="Select n.Id,Continut,Data,IdPost,IdUser,Username,Class,n.LastEdit from comments n join users u on(n.IdUser=u.Id) where n.Id=?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(this.getHost(), this.getUser(), this.getPassword());
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idComment);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Comments commtemp = new Comments();
                commtemp.setId(resultSet.getInt(1));
                commtemp.setCont(resultSet.getString(2));
                commtemp.setDop(resultSet.getTimestamp(3));
                commtemp.setIdPost(resultSet.getInt(4));
                commtemp.setIdUser(resultSet.getInt(5));
                commtemp.setUsername(resultSet.getString(6));
                commtemp.setUserClass(resultSet.getInt(7));
                commtemp.setLastEdit(resultSet.getTimestamp(8));
                comm=commtemp;
            }
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        }

        return comm;
    }

    public void addComment(Integer idPost, Integer idUser, String body) throws SQLException, ClassNotFoundException {
        java.sql.Timestamp date = new java.sql.Timestamp((new java.util.Date().getTime()));
        query = "Insert into comments(IdPost,IdUser,Continut,Data,LastEdit) values(?,?,?,?,?)";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(this.getHost(), this.getUser(), this.getPassword());
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idPost);
            preparedStatement.setInt(2, idUser);
            preparedStatement.setString(3, body);
            preparedStatement.setTimestamp(4, date);
            preparedStatement.setTimestamp(5,date);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        }
    }
	
	public void addReply(Integer idPost, Integer idUser, String body, String replyee) throws SQLException, ClassNotFoundException {
        java.sql.Timestamp date = new java.sql.Timestamp((new java.util.Date().getTime()));
        query = "Insert into comments(IdPost,IdUser,Continut,Data) values(?,?,?,?)";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(this.getHost(), this.getUser(), this.getPassword());
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idPost);
            preparedStatement.setInt(2, idUser);
            preparedStatement.setString(3, "Reply to: [i]"+replyee+"[/i]\n"+body);
            preparedStatement.setTimestamp(4, date);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        }
    }

    public void deleteComment(Integer id) throws SQLException, ClassNotFoundException {
        query = "Delete from comments where Id=?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(this.getHost(), this.getUser(), this.getPassword());
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        }
    }

    public void editComment(Integer id, String body) throws SQLException, ClassNotFoundException {
        java.sql.Timestamp date = new java.sql.Timestamp((new java.util.Date().getTime()));
        query = "Update comments set Continut=?,LastEdit=? where Id=?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(this.getHost(), this.getUser(), this.getPassword());
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, body);
            preparedStatement.setTimestamp(2, date);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        }
    }

}
