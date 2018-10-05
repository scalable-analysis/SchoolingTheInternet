package com.stn.helpers;

import com.mysql.jdbc.Statement;
import com.stn.pojo.Topic;
import com.stn.utils.DBConnection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicHelper extends DBConnection {

    public void addTopic(String title, String body, int idUser, int idGroup) throws SQLException, ClassNotFoundException {
        int last_inserted_id = 0;
        query = "INSERT INTO topics(Name,Author,GroupId) VALUES (?,?,?)";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(this.getHost(), this.getUser(), this.getPassword());
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, idUser);
            preparedStatement.setInt(3, idGroup);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                last_inserted_id = resultSet.getInt(1);
            }
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        }

        query = "INSERT INTO comments(IdPost,IdUser,Continut) VALUES (?,?,?)";

        if (last_inserted_id != 0) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(this.getHost(), this.getUser(), this.getPassword());
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, last_inserted_id);
                preparedStatement.setInt(2, idUser);
                preparedStatement.setString(3, body);
                preparedStatement.executeUpdate();
            } finally {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            }

        }
    }

    public List<Topic> getTopics(int catId) throws ClassNotFoundException, SQLException {
        List<Topic> topic = new ArrayList<Topic>();

        query = "SELECT TopicId,Name,GroupId,t.Author,u.Username,u.Class, (SELECT COUNT(*) FROM comments WHERE IdPost = TopicId)" +
                " FROM topics t JOIN users u ON t.Author = u.Id  WHERE GroupId = ?";


        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(this.getHost(), this.getUser(), this.getPassword());
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, catId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Topic t = new Topic();
                t.setTopicId(resultSet.getInt(1));
                t.setName(resultSet.getString(2));
                t.setGroupId(resultSet.getInt(3));
                t.setAuthorId(resultSet.getInt(4));
                t.setAuthorName(resultSet.getString(5));
                t.setAuthorClass(resultSet.getInt(6));
                t.setTotalposts(resultSet.getInt(7));
                topic.add(t);
            }
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        }

        return topic;
    }

}
