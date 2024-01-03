package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.User;
import utility.DatabaseUtility;

public class Controller implements Initializable {
    PreparedStatement pst;
    DatabaseUtility connectNow = new DatabaseUtility();
    Connection connectDB;
    ObservableList<User> UserList = FXCollections.observableArrayList();
    User user;
    String query;

    ResultSet resultSet;

    @FXML
    private Button addBtn;
    @FXML
    private TableColumn<User, String> dateColumn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> phoneColumn;
    @FXML
    private TableColumn<User, String> idColumn;
    @FXML
    private TableColumn<User, Boolean> selectColumn = new TableColumn<>("Select");
    @FXML
    private TextField phone_number;
    @FXML
    private TableColumn<User, String> subjectColumn;
    @FXML
    private TableView<User> table_view = new TableView<>();
    @FXML
    private TableColumn<User, String> editColumn;
    @FXML
    private TextField txt_id = new TextField();
    @FXML
    private Button updateBtn;

    public void initialize(URL url, ResourceBundle rb) {
        this.table_view();
    }

    public Controller() {
        this.connectDB = connectNow.getConnection();
    }


    @FXML
    private void getAddView(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/add-view.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add Student Details");
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void refresh() {


        try {
            UserList.clear();

            query = "SELECT * FROM student_details";
            pst = connectDB.prepareStatement(query);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                UserList.add(new User(resultSet.getInt("student_id"),
                        resultSet.getString("student_name"),
                        resultSet.getDate("birth_date"),
                        resultSet.getString("subjects"),
                        resultSet.getInt("phone_number")));
                table_view.setItems(UserList);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void delete(ActionEvent event) {

        try {
            user = table_view.getSelectionModel().getSelectedItem();
            query = "DELETE FROM student_details WHERE student_id =" + user.getStudentID();
            connectDB = connectNow.getConnection();
            pst = connectDB.prepareStatement(query);
            pst.execute();
            refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void table_view() {

        connectDB = connectNow.getConnection();
        refresh();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));

        Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> checkboxCellFactory = new CallBack<>(){
            @Override
            public TableCell<User, Boolean> call(TableColumn<User, Boolean> param){
                return new TableCell<>(){
                    final CheckBox checkBox = new CheckBox();

                    checkBox.setOnAction(event -> {

                    });

                };
            }
        };

        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory = (TableColumn<User, String> param) -> {

            final TableCell<User, String> cell = new TableCell<User, String>() {

                @Override
                public void updateItem(String item, boolean empty) {

                    Button deleteIcon = new Button();
                    Image deleteImage = new Image(getClass().getResourceAsStream("/assets/icons8-trash-40.png"));
                    ImageView delete = new ImageView(deleteImage);
                    deleteIcon.setGraphic(delete);

                    Button editIcon = new Button();
                    Image editImage = new Image(getClass().getResourceAsStream("/assets/icons8-edit-40.png"));
                    ImageView edit = new ImageView(editImage);
                    editIcon.setGraphic(edit);

                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {


                        deleteIcon.setStyle(
                                "-fx-cursor: hand;"
                                        + "-fx-font-family: 'FontAwesome';"
                                        + "-fx-font-size: 28px;"
                                        + "-fx-background-color: 'transparent';"
                                        + "-fx-fill: #ff1744;"

                        );

                        editIcon.setStyle(
                                "-fx-cursor: hand;"
                                        + "-fx-font-family: 'FontAwesome';"
                                        + "-fx-font-size: 28px;"
                                        + "-fx-background-color: 'transparent';"
                                        + "-fx-fill: #00E676;"
                        );

                        deleteIcon.setOnMouseClicked((event) -> {

                            try {
                                user = table_view.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM student_details WHERE student_id =" + user.getStudentID();
                                connectDB = connectNow.getConnection();
                                pst = connectDB.prepareStatement(query);
                                pst.execute();
                                refresh();

                            } catch (SQLException ex) {
                                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        editIcon.setOnMouseClicked((event) -> {

                            user = table_view.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/fxml/add-view.fxml"));
                            try {
                                loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            AddViewController addViewController = loader.getController();
                            addViewController.setUpdate(true);
                            addViewController.setTextField(user.getStudentID(), user.getStudentName(), LocalDate.parse(user.getBirthDate().toString()), user.getSubject(), user.getPhoneNumber());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2,2,0,3));
                        HBox.setMargin(editIcon, new Insets(2,3,0,2));

                        setGraphic(managebtn);


                    }

                }

            };
            return cell;
        };
        editColumn.setCellFactory(cellFactory);
        table_view.setItems(UserList);
    }
}