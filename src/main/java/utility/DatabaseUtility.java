package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtility {
    public static Connection databaseLink;

    public DatabaseUtility() {
    }

    public static Connection getMySqlConnection() throws ClassNotFoundException, SQLException {
        String databaseName = "student_registeration";
        String databaseUser = "root";
        String databasePassword = "math.hpunura@12345";
        String url = "jdbc:mysql://localhost/" + databaseName;


        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, databaseUser, databasePassword);
    }
}
