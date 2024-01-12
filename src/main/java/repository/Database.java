package repository;

import models.User;

import java.sql.SQLException;

public interface Database {

    User getUserbyId(String name);
    void insert(User user) throws SQLException;

}
