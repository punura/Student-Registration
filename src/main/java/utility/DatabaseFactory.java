package utility;

import repository.Database;
import repository.MySqlDatabase;

import java.sql.Connection;
import java.sql.SQLException;



public class DatabaseFactory {
    private DatabaseFactory() throws IllegalAccessException {
        throw new IllegalAccessException("Utility Class");
    }

    public static Database getDatabaseInctance(String type) throws SQLException, ClassNotFoundException, IllegalAccessException {
        return new MySqlDatabase(getConnection(type));
    }

    private static Connection getConnection(String type) throws ClassNotFoundException, SQLException, IllegalAccessException {
        if (type.equals("MYSQL")){
            return DatabaseUtility.getMySqlConnection();
        } else if (type.equals("ORACLE")) {
            return null;
        }else {
            throw new IllegalAccessException("Invalid Database Type");
        }

    }

}
