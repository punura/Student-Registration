package utility;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtility {
    public Connection databaseLink;

    public DatabaseUtility() {
    }

    public Connection getConnection() {
        String databaseName = "student_registeration";
        String databaseUser = "root";
        String databasePassword = "math.hpunura@12345";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return this.databaseLink;
    }
}
