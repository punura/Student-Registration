module student.registration {
    requires javafx.controls;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;
    requires mysql.connector.j;
    requires fontawesomefx;

    exports controllers;
    opens models to javafx.base;
    opens controllers to javafx.fxml,  fontawesomefx;
    opens application to
            javafx.graphics,
            javafx.fxml;
}