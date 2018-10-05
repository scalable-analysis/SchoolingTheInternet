package com.stn.helpers;

import com.stn.pojo.Category;
import com.stn.utils.DBConnection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryHelper extends DBConnection {

    public void addCategory(String categorie, String descriere, int idFacultate, int idSerie, int idGrupa, int pinned) throws ClassNotFoundException, SQLException {
        query = "INSERT INTO categories(categorie, descriere, idFacultate, idSerie, idGrupa, Pinned) VALUES(?,?,?,?,?,?)";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(this.getHost(), this.getUser(), this.getPassword());
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, categorie);
            preparedStatement.setString(2, descriere);
            preparedStatement.setInt(3, idFacultate);
            preparedStatement.setInt(4, idSerie);
            preparedStatement.setInt(5, idGrupa);
            preparedStatement.setInt(6, pinned);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        }
    }

    public List<Category> getCategories(int idFacultate) throws SQLException {

        List<Category> category1 = new ArrayList<>();

        query = "SELECT IdCat, categorie, pinned, descriere, idFacultate, idSerie, idGrupa, (SELECT COUNT(*) FROM topics WHERE GroupId = IdCat)," +
                "(SELECT COUNT(*) FROM comments WHERE IdPost IN (SELECT TopicId FROM topics WHERE GroupId = IdCat)) " +
                "FROM categories " +
                "WHERE idFacultate = ?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(this.getHost(), this.getUser(), this.getPassword());
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idFacultate);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt(1));
                category.setCategorie(resultSet.getString(2));
                category.setPinned(resultSet.getInt(3));
                category.setDescriere(resultSet.getString(4));
                category.setIdFacultate(idFacultate);
                category.setIdSerie(resultSet.getInt(6));
                category.setIdGrupa(resultSet.getInt(7));
                category.setTotalTopics(resultSet.getInt(8));
                category.setTotalPosts(resultSet.getInt(9));
                category1.add(category);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
            if (resultSet != null)
                resultSet.close();
        }
        return category1;
    }
}
