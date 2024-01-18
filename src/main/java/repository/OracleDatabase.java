package repository;

import javafx.collections.ObservableList;
import models.User;

public class OracleDatabase implements Database{
    @Override
    public User getUserbyId(String name) {
        return null;
    }

    @Override
    public void insert(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteMultiple(ObservableList<User> selectedUsers) {

    }

    @Override
    public void update(User user, boolean b) {

    }

}
