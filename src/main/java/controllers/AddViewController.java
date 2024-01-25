package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import repository.Database;
import utility.DatabaseFactory;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddViewController implements Initializable {

    @FXML
    private DatePicker birth_date = new DatePicker();

    @FXML
    private TextField phone_number = new TextField();

    @FXML
    private TextField txt_id = new TextField();

    @FXML
    private TextField txt_name = new TextField();

    @FXML
    private TextField txt_subject = new TextField();
    private controllers.Controller Controller;

    private Database database;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            database = DatabaseFactory.getDatabaseInctance("MYSQL");
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            throw new RuntimeException("Get Database Connection Unsuccessfull!");
        }
    }



    @FXML
    void add(ActionEvent event) throws SQLException {



        User user = new User();
             user.setStudentID(Integer.parseInt(txt_id.getText()));
             user.setStudentName(txt_name.getText());
             user.setBirthDate(java.sql.Date.valueOf(birth_date.getValue()));
             user.setSubject(txt_subject.getText());
             user.setPhoneNumber(Integer.parseInt(phone_number.getText()));

        if(database.setUpdate(true)){
            database.update(user);
        }else {
            database.insert(user);
        }


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }


    @FXML
    void cancel(ActionEvent event) {

        txt_id.setText(null);
        txt_name.setText(null);
        birth_date.setValue(null);
        txt_subject.setText(null);
        phone_number.setText(null);

    }





    void setTextField(int id, String name, LocalDate tolocalDate, String subject, int number) {

        txt_id.setText(String.valueOf(id));
        txt_name.setText(name);
        birth_date.setValue(tolocalDate);
        txt_subject.setText(subject);
        phone_number.setText(String.valueOf(number));

    }

}
