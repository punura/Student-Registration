package repository;

import javafx.collections.ObservableList;
import models.User;

import java.sql.SQLException;

public interface Database {


    void update(User user) throws SQLException;

    void insert(User user) throws SQLException;

    void delete(User user);

    void deleteMultiple(ObservableList<User> selectedUsers);







}
