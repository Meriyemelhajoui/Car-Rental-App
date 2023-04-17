package com.example.javaproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeedbacksController implements Initializable {
    @FXML
    private Button bookings;

    @FXML
    private TableColumn<Feedback, String> cinU;

    @FXML
    private Button dashboard;

    @FXML
    private Button drivers;

    @FXML
    private TableColumn<Feedback, String> feedback;

    @FXML
    private Button feedbacks;

    @FXML
    private TableView<Feedback> feedbacksTable;

    @FXML
    private TableColumn<Feedback, Integer> id;

    @FXML
    private Button logout;
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    ObservableList<Feedback> feedbackList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void loadDate() throws SQLException {
        connection = ConnexDB.GetConnexion();
        refreshTable();

            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            cinU.setCellValueFactory(new PropertyValueFactory<>("cin"));
            feedback.setCellValueFactory(new PropertyValueFactory<>("description"));



    }

    private void refreshTable()  {

        try {
            feedbackList.clear();

            query = "SELECT * FROM `feedback`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                feedbackList.add(new Feedback(
                        resultSet.getInt("id"),
                        resultSet.getString("CIN_client"),
                        resultSet.getString("description")
                ));

                feedbacksTable.setItems(feedbackList);
            }

        } catch (SQLException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
        }

    }


    @FXML
    public void Logout(ActionEvent e) throws SQLException, IOException {
        ConnexDB.Deconnexion();
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rentify");
        stage.setScene(scene);
        scene.getWindow().sizeToScene();
        stage.show();
    }
    @FXML
    public void Drivers(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Drivers.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rentify");
        stage.setScene(scene);
        scene.getWindow().sizeToScene();
        stage.show();
    }
    @FXML
    public void Dashboard(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void Booking(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Bookings.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rentify");
        stage.setScene(scene);
        scene.getWindow().sizeToScene();
        stage.show();
    }
    @FXML
    public void Feedbacks(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Feedbacks.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rentify");
        stage.setScene(scene);
        scene.getWindow().sizeToScene();
        stage.show();
    }

}
