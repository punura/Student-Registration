package repository;

import controllers.AddViewController;
import javafx.collections.ObservableList;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySqlDatabase implements Database {

    private final Connection connection;
    private boolean update;

    public MySqlDatabase(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User getUserbyId(String name) {
        return null;
    }

    @Override
    public void insert(User user) throws SQLException {

        try {
            String query = "INSERT INTO student_details (student_id, student_name, birth_date, subjects, phone_number) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(user.getStudentID()));
            statement.setString(2, user.getStudentName());
            statement.setString(3, user.getBirthDate().toString());
            statement.setString(4, user.getSubject());
            statement.setString(5, String.valueOf(user.getPhoneNumber()));
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AddViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    @Override
    public void delete(User user) {
        String query = "DELETE FROM student_details WHERE student_id =" + user.getStudentID();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMultiple(ObservableList<User> selectedUsers) {
        StringBuilder deleteQuery = new StringBuilder("DELETE FROM student_details WHERE student_id IN (");

        for (User selectedUser : selectedUsers) {
            deleteQuery.append(selectedUser.getStudentID()).append(", ");
        }

        deleteQuery.setLength(deleteQuery.length() - 2);
        deleteQuery.append(")");

        try {
            PreparedStatement statement = connection.prepareStatement(String.valueOf(deleteQuery));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE student_details SET " +
                "student_name=?, " +
                "birth_date=?, " +
                "subjects=?, " +
                "phone_number=? " +
                "WHERE student_id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getStudentName());
        statement.setString(2, user.getBirthDate().toString());
        statement.setString(3, user.getSubject());
        statement.setString(4, String.valueOf(user.getPhoneNumber()));
        if (update) {
            statement.setString(5, String.valueOf(user.getStudentID()));
        }
        statement.execute();
    }

    @Override
    public boolean setUpdate(boolean b) {
        this.update = b;
        return b;
    }


}
