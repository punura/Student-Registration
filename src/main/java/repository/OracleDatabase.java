package repository;

import javafx.collections.ObservableList;
import models.User;

import java.sql.SQLException;

public class OracleDatabase implements Database{
    @Override
    public User getUserbyId(String name) {
        return null;
    }

    @Override
    public void insert(User user) {

    }

    @Override
    public void insertNew(User user) throws SQLException {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteMultiple(ObservableList<User> selectedUsers) {

    }

    @Override
    public void setUpdate(boolean b) {

    }

    @Override
    public void getQuery() {

    }

}
