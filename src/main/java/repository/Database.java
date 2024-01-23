package repository;

import javafx.collections.ObservableList;
import models.User;

import java.sql.SQLException;

public interface Database {

    User getUserbyId(String name);
    void update(User user) throws SQLException;

    void insert(User user) throws SQLException;

    void delete(User user);

    void deleteMultiple(ObservableList<User> selectedUsers);

    boolean setUpdate(boolean b);

    void getQuery();



}
