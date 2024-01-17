package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

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
import repository.Database;
import utility.DatabaseFactory;
import utility.DatabaseUtility;

public class Controller implements Initializable {

    Connection connectDB;
    ObservableList<User> UserList = FXCollections.observableArrayList();
    User user;

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
    private TableColumn<User, Boolean> selectColumn;
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

    private Database database;

    private User editUser;


    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.table_view();
            database = DatabaseFactory.getDatabaseInctance("MYSQL");
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Controller() throws SQLException, ClassNotFoundException {
        this.connectDB = DatabaseUtility.getMySqlConnection();
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

            String query = "SELECT * FROM student_details";
            PreparedStatement pst = connectDB.prepareStatement(query);
            ResultSet resultSet;
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
    void delete(ActionEvent event) throws ClassNotFoundException {

        User user = table_view.getSelectionModel().getSelectedItem();
        database.delete(user);
        refresh();

    }

    @FXML
    void deleteMultiple(ActionEvent event) throws ClassNotFoundException {

        ObservableList<User> selectedUsers = table_view.getSelectionModel().getSelectedItems();
        database.deleteMultiple(selectedUsers);
        refresh();

    }



    public void table_view() throws SQLException, ClassNotFoundException {

        connectDB = DatabaseUtility.getMySqlConnection();
        refresh();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));

        table_view.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> checkboxCellFactory = new Callback<>() {
            @Override
            public TableCell<User, Boolean> call(TableColumn<User, Boolean> param) {
                return new TableCell<>() {
                    final CheckBox checkBox = new CheckBox();

                    {
                        checkBox.setOnAction(event -> {
                            user = getTableView().getItems().get(getIndex());
                            user.setSelected(checkBox.isSelected());

                            if (checkBox.isSelected()) {
                                table_view.getSelectionModel().select(getIndex());
                            } else {
                                table_view.getSelectionModel().clearSelection(getIndex());
                            }
                        });
                    }

                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            user = getTableView().getItems().get(getIndex());
                            checkBox.setSelected(user.isSelected());
                            setGraphic(checkBox);

                        }
                    }
                };
            }
        };
        selectColumn.setCellFactory(checkboxCellFactory);

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

                            user = table_view.getSelectionModel().getSelectedItem();
                            database.delete(user);
                            refresh();

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
                            //database.update(user);
                            addViewController.setTextField(user.getStudentID(), user.getStudentName(), LocalDate.parse(user.getBirthDate().toString()), user.getSubject(), user.getPhoneNumber());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();

                        });


                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

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