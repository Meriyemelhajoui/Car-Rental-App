package com.example.javaproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
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

public class RentCarController implements Initializable {
    @FXML
    private TableView<Location> bookingTable;
    @FXML
    private TableColumn<Location,String> matricule;
    @FXML
    private TableColumn<Location,String> cinD;
    @FXML
    private TableColumn<Location,Integer> prixT;
    @FXML
    private TableColumn<Users, Integer> prixA;
    @FXML
    private TableColumn<Users,String> pickupD;
    @FXML
    private TableColumn<Users,String> returnD;

    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Location location = null ;
    ObservableList<Location> bookingList = FXCollections.observableArrayList();

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


        {
            matricule.setCellValueFactory(new PropertyValueFactory<>("MatriculeV"));
            cinD.setCellValueFactory(new PropertyValueFactory<>("CIN"));
            prixT.setCellValueFactory(new PropertyValueFactory<>("Prix_Total"));
            prixA.setCellValueFactory(new PropertyValueFactory<>("Prix_Avance"));
            pickupD.setCellValueFactory(new PropertyValueFactory<>("datedeb"));
            returnD.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        }


    }

    private void refreshTable()  {

        try {
            bookingList.clear();

            query = "SELECT * FROM `location`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                bookingList.add(new Location(
                        resultSet.getString("MatriculeV"),
                        resultSet.getString("CIN"),
                        resultSet.getString("datedeb"),
                        resultSet.getString("datefin"),
                        resultSet.getInt("Prix_Total"),
                        resultSet.getInt("Prix_Avance")
                ));

                bookingTable.setItems(bookingList);
            }

        } catch (SQLException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
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
    public void Notification(ActionEvent e)
    {}

}
