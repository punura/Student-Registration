package repository;

import javafx.collections.ObservableList;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySqlDatabase implements Database{

    private final Connection connection;

    public MySqlDatabase(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User getUserbyId(String name) {
        return null;
    }

    @Override
    public void insert(User user) throws SQLException {
        String query = "INSERT INTO student_details (student_id, student_name, birth_date, subjects, phone_number) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(user.getStudentID()));
            statement.setString(2, user.getStudentName());
            statement.setString(3, user.getBirthDate().toString());
            statement.setString(4, user.getSubject());
            statement.setString(5, String.valueOf(user.getPhoneNumber()));
        statement.execute();
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
            PreparedStatement statement =  connection.prepareStatement(String.valueOf(deleteQuery));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(User user) {

    }

}
