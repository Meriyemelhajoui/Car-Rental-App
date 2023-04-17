package com.example.javaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.javaproject.Main.CURRENCY;

public class FactureController {
    @FXML
    private Label CINlabel;

    @FXML
    private Label ModeleLabel;

    @FXML
    private Button backHomebtn;

    @FXML
    private Label clientNameLabel;

    @FXML
    private Button feedbackBtn;

    @FXML
    private TextArea feedbackLabel;

    @FXML
    private Label marqueLabel;

    @FXML
    private Label matriculeLabel;

    @FXML
    private Label matriculeLabel11;

    @FXML
    private Label pickupDlabel;

    @FXML
    private Label prixALabel;

    @FXML
    private Label prixRLabel;

    @FXML
    private Label prixTLabel;

    @FXML
    private Label returnDlabel;
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;


    @FXML
    public void backHomePage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Home.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(pane);
        stage.setTitle("Rentify");
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
  }

    public void transferMessage(String dateP, String dateR, String name, String cin, String matricule, String marque, String modele, String prixT, String prixA, String prixR) {
        //Display the message

        pickupDlabel.setText(dateP);
        returnDlabel.setText(dateR);
        clientNameLabel.setText(name);
        CINlabel.setText(cin);
        matriculeLabel.setText(matricule);
        marqueLabel.setText(marque);
        ModeleLabel.setText(modele);
        prixTLabel.setText(prixT);
        prixALabel.setText(prixA);
        prixRLabel.setText(prixR);

    }
    @FXML
    public void addFeedback(ActionEvent e) throws SQLException {
        connection = ConnexDB.GetConnexion();
        String query = "INSERT INTO feedback(CIN_client,description) VALUES(?,?)";
        preparedStatement =connection.prepareStatement(query);
        preparedStatement.setString(1, CINlabel.getText());
        preparedStatement.setString(2, feedbackLabel.getText());
        int rs = preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Thank you for your feedback! Hope Meet You Soon!");
            alert.showAndWait();

    }

}

