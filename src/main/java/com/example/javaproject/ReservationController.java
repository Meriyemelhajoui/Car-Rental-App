package com.example.javaproject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.javaproject.ConnexDB.*;
import static com.example.javaproject.ConnexDB.prep;
import static com.example.javaproject.Location.ETAT_DISPONIBLE;
import static com.example.javaproject.Main.CURRENCY;
import static com.example.javaproject.Voiture.sdformat;

public class ReservationController implements Initializable {


    @FXML
    private TextField CINtxtfld;

    @FXML
    private Button bookBtn;

    @FXML
    private Label carNameLabel;

    @FXML
    private ImageView prev;

    @FXML
    private TextField prixAtxtfld;
    @FXML
    private Label pickupDlabel;
    @FXML
    private Label returnDlabel;


    @FXML
    private Label totalPriceLabel;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    private String name;
    public String getName(){return this.name;}
    public void setName(String name){ this.name=name;}
    private String priceT;
    public String getPriceT(){return this.priceT;}
    public void setPriceT(String priceT){ this.priceT=priceT;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {

        });
    }
    String matriculeV;

    @FXML
    private Label matriculeLabel;
    //Receive message from scene 1
    public void transferMessage(String name, String price, String dateP, String dateR, String matriculeV) {
        //Display the message
        matriculeV = matriculeV;

        matriculeLabel.setText(matriculeV);
        carNameLabel.setText(name);
        totalPriceLabel.setText(price+" "+CURRENCY);
        pickupDlabel.setText(dateP);
        returnDlabel.setText(dateR);

    }


    // restruct MAD from getCarsPriceLabel to int
    private String getTotalPriceLabelSplit() {
        String carPrice = totalPriceLabel.getText();
        String[] carPriceArray = carPrice.split(" MAD");
        return carPriceArray[0];
    }
    private String getAvancePriceLabelSplit() {
        String carPrice = prixAtxtfld.getText();
        String[] carPriceArray = carPrice.split(" MAD");
        return carPriceArray[0];
    }
    // compare between two dates

    public boolean checkDate() throws SQLException, ParseException,SQLIntegrityConstraintViolationException {
        boolean checkD= false;
        try {
            GetConnexion();
            String query = "SELECT *  FROM voiture WHERE MatriculeV=?  LIMIT 1";
            prep = mycnx.prepareStatement(query);
            prep.setString(1,matriculeLabel.getText());
            rs = prep.executeQuery();
            while (rs.next()) {
                String dateVisi = rs.getString("DateVisite");
                String dateAssu = rs.getString("DateAssu");
                Date DV = sdformat.parse(dateVisi);
                Date DA = sdformat.parse(dateAssu);
                String dateFinreservation = returnDlabel.getText();
                Date DR = sdformat.parse(dateFinreservation);
                if (DA.compareTo(DR)>0 && DV.compareTo(DR)>0) {

                    //System.out.println("La date de  fin de reservation vient Avant  dateASS ET dateVisite");
                    checkD = true;
                }

            }

        }catch (SQLException e){
            System.out.println(e);
        }
        Deconnexion();
        return checkD;
    }
    public boolean checkEtat() throws SQLException {
        boolean check=false;
        try {
            GetConnexion();
            String query ="SELECT etat FROM voiture WHERE MatriculeV=?";
            prep= mycnx.prepareStatement(query);
            prep.setString(1,matriculeLabel.getText()); // aykoun dk id dial Matricule
            rs=prep.executeQuery();
            while(rs.next()){
                String state =rs.getString("etat");
                if(state.equals(ETAT_DISPONIBLE)){
                    // System.out.println("Voiture valide  par etat ");
                    check=true;
                }
            }

        }catch (SQLException s){
            System.out.println(s);
        }
        return  check;
    }


    // methode pour calculer le prix restant d une location //
    public int CalculRestePrix() throws ParseException // prixActue c le prix qui saisit le client
    {
        int RestePrix=  Integer.parseInt(getTotalPriceLabelSplit()) - Integer.parseInt(getAvancePriceLabelSplit());
        System.out.println("Le reste de prix est : " + RestePrix);
        return RestePrix;
    }

    // methode qui set etat-loue pour chaque matricule de voiture loue //
    public  void SetEtat() throws SQLException {
        try {
                connection = ConnexDB.GetConnexion();
                String query="UPDATE  voiture SET etat=? WHERE MatriculeV=?";
                preparedStatement=connection.prepareStatement(query);
                preparedStatement.setString(1,"loue");
                preparedStatement.setString(2,matriculeLabel.getText());
                int  rs=preparedStatement.executeUpdate();
                if(rs>0){
                    System.out.println("L'etat des voitures reserves est updated ");
                }

        }catch (SQLException e){
            System.out.println(e);
        }
    }
    public String getClientName() throws SQLException {
        String fullName = null;
            connection= ConnexDB.GetConnexion();
            String query = "SELECT prenom,nom  FROM users WHERE CIN=? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,CINtxtfld.getText());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String prenom = resultSet.getString("prenom");
                String nom= resultSet.getString("nom");
                fullName = nom+" "+prenom;
            }
            return fullName;
    }
    public String getModele() throws SQLException {
        String modele = null;
        connection= ConnexDB.GetConnexion();
        String query = "SELECT *  FROM voiture WHERE MatriculeV=? ";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,matriculeLabel.getText());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            modele = resultSet.getString("Modele");

        }
        return modele;
    }

    @FXML
    public void displayBill() throws IOException, SQLException, ParseException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Facture.fxml"));
        Parent pane = (Parent) fxmlLoader.load();
        FactureController data = fxmlLoader.getController();
        data.transferMessage(pickupDlabel.getText(),returnDlabel.getText(),getClientName(),CINtxtfld.getText(),matriculeLabel.getText(),carNameLabel.getText(),getModele(),totalPriceLabel.getText(),prixAtxtfld.getText(), String.valueOf(CalculRestePrix()));
        Stage stage = new Stage();
        Scene scene = new Scene(pane);
        stage.setTitle("Facture");
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
   public void book(ActionEvent e) throws SQLException, ParseException, IOException {
       GetConnexion();
       String CIN = CINtxtfld.getText();
       String prixAv = prixAtxtfld.getText();
       if ( CIN.isEmpty() || prixAv.isEmpty()) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setHeaderText(null);
           alert.setContentText("Please Fill All DATA");
           alert.showAndWait();
       } else {
           Faireunelocation();
           clean();

       }
   }


    private void clean() {

        CINtxtfld.setText(null);
        prixAtxtfld.setText(null);

    }
    //get MatriculeV from database where car

    //Methode pour ajouter une location ou bien faire une reservation //
    public void Faireunelocation() throws ParseException {
        try
        {

            connection = ConnexDB.GetConnexion();
            // setCIN(User.cin)//
            if(checkDate()==true && checkEtat()==true ) {
                String query = "INSERT INTO location VALUES(?,?,?,?,?,?)";
                preparedStatement =connection.prepareStatement(query);
                preparedStatement.setString(1, matriculeLabel.getText());
                preparedStatement.setString(2, CINtxtfld.getText());
                preparedStatement.setInt(3, Integer.parseInt(getTotalPriceLabelSplit()));
                preparedStatement.setInt(4, Integer.parseInt(getAvancePriceLabelSplit()));
                preparedStatement.setString(5, pickupDlabel.getText());
                preparedStatement.setString(6, returnDlabel.getText());
                int rs = preparedStatement.executeUpdate();
                SetEtat();

                if(rs>0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Reservation Complete!");
                    alert.showAndWait();
                    displayBill();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Car is not available, please choose another car");
                    alert.showAndWait();
                }
            }
        }
        catch (SQLIntegrityConstraintViolationException e ){
            System.out.println(e);}
        catch (SQLException | IOException e){
            System.out.println(e);
        }
    }
    public String getCarNameLabel() {
        return carNameLabel.getText();
    }

    public void setCarNameLabel(Label carNameLabel) {
        this.carNameLabel = carNameLabel;
    }

    @FXML
    private void Prev(MouseEvent event)//pour retourner a la page precedente
    {
        try {

            Parent parent = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage)prev.getParent().getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);

        }
    }




}
