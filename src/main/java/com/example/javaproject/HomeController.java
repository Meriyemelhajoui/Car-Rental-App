package com.example.javaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

import static com.example.javaproject.Main.CURRENCY;

public class HomeController {

    @FXML
    private Label feedbackLabel;
    @FXML
    private  DatePicker dateReturn;


    public void Login(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rentify");
        stage.setScene(scene);
        stage.show();
    }

    public void ViewAll(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ViewAll.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rentify");
        stage.setScene(scene);
        stage.show();
    }


    public void SignUp(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Registration");
        stage.setScene(scene);
        stage.show();
    }



    public void transferMessage(String username) {
        //Display the message
        usernameIfLogLabel.setText(username);
    }



    @FXML
    public void searchAvailCars(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("AvailCars.fxml"));
        Parent pane = (Parent) loader.load();
        AvailCarsController avl = loader.getController();
        avl.setUserameIfLogLabel(getUserameIfLogLabel());
        avl.setUserameIfLogLabel(getUserameIfLogLabel());
        avl.setDateR(String.valueOf(getReturnDate()));
        avl.setDateP(String.valueOf(getPickupDate()));
        //avl.transferMessage(getUserameIfLogLabel(),String.valueOf(getReturnDate()),String.valueOf(getPickupDate()));
        stage.setTitle("Available Cars");
        Scene scene =  new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private DatePicker datePickup;
    private LocalDate getPickupDate() {
        return datePickup.getValue();
    }

    public LocalDate getReturnDate()
    {
        return dateReturn.getValue();
    }

    @FXML
    private Label usernameIfLogLabel;

    public String getUserameIfLogLabel() {return usernameIfLogLabel.getText();}

}
