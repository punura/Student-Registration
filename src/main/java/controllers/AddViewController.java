package controllers;

import com.mysql.cj.xdevapi.PreparableStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.DatabaseUtility;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class AddViewController {

    PreparedStatement pst;
    DatabaseUtility connectNow = new DatabaseUtility();
    Connection connectDB;

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

    String query;

    private boolean update;


    @FXML
    void add(ActionEvent event) {
        connectDB = connectNow.getConnection();
        String studentId = this.txt_id.getText();
        String studentName = this.txt_name.getText();
        String birthDate = String.valueOf(this.birth_date.getValue());
        String subjects = this.txt_subject.getText();
        String phoneNumber = this.phone_number.getText();


        if (studentId.isEmpty() || studentName.isEmpty() || birthDate.isEmpty() || subjects.isEmpty() || phoneNumber.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All Data!");
            alert.showAndWait();
        } else {
            getQuery();
           insert();


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

    private void insertNew() {
        try {
            pst = connectDB.prepareStatement(query);
            pst.setString(1, txt_id.getText());
            pst.setString(2, txt_name.getText());
            pst.setString(3, String.valueOf(birth_date.getValue()));
            pst.setString(4, txt_subject.getText());
            pst.setString(5, phone_number.getText());
            pst.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getQuery() {
        if (!update){
            query = "INSERT INTO student_details (student_id, student_name, birth_date, subjects, phone_number) VALUES(?, ?, ?, ?, ?)";
        }else{
            query = "UPDATE student_details SET " +
                    "student_name=?, " +
                    "birth_date=?, " +
                    "subjects=?, " +
                    "phone_number=? " +
                    "WHERE student_id=?";
        }
    }

    private void insert() {
        try {
            pst = connectDB.prepareStatement(query);
            pst.setString(1, txt_name.getText());
            pst.setString(2, String.valueOf(birth_date.getValue()));
            pst.setString(3, txt_subject.getText());
            pst.setString(4, phone_number.getText());
            if (update) {
                pst.setString(5, txt_id.getText());
            }
            pst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void setTextField(int id, String name, LocalDate tolocalDate, String subject, int number){

        txt_id.setText(String.valueOf(id));
        txt_name.setText(name);
        birth_date.setValue(tolocalDate);
        txt_subject.setText(subject);
        phone_number.setText(String.valueOf(number));

    }

    void setUpdate(boolean b){

        this.update = b;

    }

}
