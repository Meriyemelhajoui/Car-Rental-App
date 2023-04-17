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
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriversController implements Initializable {
    @FXML
    private TableView<Users> userTable;
    @FXML
    private TableColumn<Users,String> username;
    @FXML
    private TableColumn<Users,String> cin;
    @FXML
    private TableColumn<Users,String> prenom;
    @FXML
    private TableColumn<Users, String> nom;
    @FXML
    private TableColumn<Users,Integer> age;
    @FXML
    private TableColumn<Users,Integer> Npermis;
    @FXML
    private TableColumn<Users,String> catP;
    @FXML
    private TableColumn<Users,Date> dateP;

    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Users user = null ;
    ObservableList<Users> usersList = FXCollections.observableArrayList();

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
            username.setCellValueFactory(new PropertyValueFactory<>("username"));
            cin.setCellValueFactory(new PropertyValueFactory<>("CIN"));
            prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            age.setCellValueFactory(new PropertyValueFactory<>("age"));
            Npermis.setCellValueFactory(new PropertyValueFactory<>("N_permis"));
            catP.setCellValueFactory(new PropertyValueFactory<>("Catergorie"));
        }

    }

    private void refreshTable()  {

        try {
            usersList.clear();

            query = "SELECT * FROM `users`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                usersList.add(new Users(
                        resultSet.getString("username"),
                        resultSet.getString("CIN"),
                        resultSet.getString("prenom"),
                        resultSet.getString("nom"),
                        resultSet.getInt("age"),
                        resultSet.getInt("N_permis"),
                        resultSet.getString("Categorie_permis"),
                        resultSet.getString("tel")
                ));

                userTable.setItems(usersList);
            }

        } catch (SQLException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

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
    @FXML
    public void Setting(ActionEvent e)
    {}
    @FXML
    public void PayementDetails(ActionEvent e)
    {}
    @FXML
    public void Transactions(ActionEvent e)
    {}
    @FXML
    public void CarRepport(ActionEvent e)
    {}

}

