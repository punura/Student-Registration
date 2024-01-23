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
    public void update(User user) {

    }

    @Override
    public void insert(User user) throws SQLException {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteMultiple(ObservableList<User> selectedUsers) {

    }

    @Override
    public boolean setUpdate(boolean b) {

        return b;
    }

    @Override
    public void getQuery() {

    }

}
