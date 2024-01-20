package repository;

import javafx.collections.ObservableList;
import models.User;

import java.sql.SQLException;

public interface Database {

    User getUserbyId(String name);
    void insert(User user) throws SQLException;

    void insertNew(User user) throws SQLException;

    void delete(User user);

    void deleteMultiple(ObservableList<User> selectedUsers);

    void setUpdate(boolean b);

    void getQuery();



}
