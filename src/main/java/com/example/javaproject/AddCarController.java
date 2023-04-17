package com.example.javaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.javaproject.ConnexDB.mycnx;

public class AddCarController implements Initializable {

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Voiture voiture = null;
    private boolean update;
    String carM;

    List<String> lstFile;
    @FXML
    private Button addpic;
    @FXML
    private Label labelpic;

    @FXML
    private void ImageChooser() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", lstFile));
        File f = fc.showOpenDialog(null);

        if (f != null) {
            labelpic.setText(f.getAbsolutePath());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lstFile = new ArrayList<>();
        lstFile.add("*.jpeg");
        lstFile.add("*.png");
        lstFile.add("*.jpg");
    }

    @FXML
    private TextField addmatricule;
    @FXML
    private TextField addmarque;
    @FXML
    private TextField addmodel;
    @FXML
    private TextField addprice;
    @FXML
    private TextField addetat;
    @FXML
    private DatePicker addvisit;
    @FXML
    private DatePicker addassur;


    @FXML
    private void save(MouseEvent event) throws SQLException, IOException {
        connection = ConnexDB.GetConnexion();
        String matricule = addmatricule.getText();
        String modele = addmodel.getText();
        String marque = addmarque.getText();
        String etat = addetat.getText();
        String price = addprice.getText();
        String visit = String.valueOf(addvisit.getValue());
        String assur = String.valueOf(addassur.getValue());

        if (matricule.isEmpty() || modele.isEmpty() || marque.isEmpty() || etat.isEmpty() || price.isEmpty() || visit.isEmpty() || assur.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        } else {
                insert();
                clean();
        }

    }

    @FXML
    private void updateC(MouseEvent event) throws SQLException {
        connection = ConnexDB.GetConnexion();
        String price = addprice.getText();
        String visit = String.valueOf(addvisit.getValue());
        String assur = String.valueOf(addassur.getValue());

        if (price.isEmpty() || visit.isEmpty() || assur.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        } else {
            updateCar();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Car Succesfuly Updated!");
            alert.showAndWait();
            ConnexDB.Deconnexion();
        }

    }

    public void updateCar() throws SQLException {
             ConnexDB.GetConnexion();

                String query="UPDATE voiture SET DateVisite=? , DateAssu =? , PrixParJour=? WHERE MatriculeV=?";
                preparedStatement=mycnx.prepareStatement(query);
                preparedStatement.setString(4,carM);
                preparedStatement.setString(1, String.valueOf(addvisit.getValue()));
                preparedStatement.setString(2, String.valueOf(addassur.getValue()));
                preparedStatement.setInt(3, Integer.parseInt(addprice.getText()));
                preparedStatement.executeUpdate();


    }
    private void clean() {
        addmatricule.setText(null);
        addmarque.setText(null);
        addmodel.setText(null);
        addvisit.setValue(null);
        addassur.setValue(null);
        addetat.setText(null);
        addpic.setText(null);
        addprice.setText(null);
        labelpic.setText(null);
    }

    @FXML
    private ImageView prev;
    @FXML
    private void Prev(MouseEvent event)
    {
        try {

            Parent parent = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage)prev.getParent().getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    private  void insert() {


        try {
            ConnexDB.GetConnexion();
            String q = "SELECT MatriculeV FROM voiture WHERE MatriculeV=? ";
            preparedStatement = mycnx.prepareStatement(q);
            preparedStatement.setString(1, addmatricule.getText());
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
            {
                query = "INSERT INTO `voiture`(`MatriculeV`, `DateVisite`, `DateAssu`, `Marque`, `Modele`, `PrixParJour`, `image`, `etat`) VALUES (?,?,?,?,?,?,?,?)";
                preparedStatement = mycnx.prepareStatement(query);
                preparedStatement.setString(1, addmatricule.getText());
                preparedStatement.setString(2, String.valueOf(addvisit.getValue()));
                preparedStatement.setString(3, String.valueOf(addassur.getValue()));
                preparedStatement.setString(4, addmarque.getText());
                preparedStatement.setString(5, addmodel.getText());
                preparedStatement.setInt(6, Integer.parseInt(addprice.getText()));
                preparedStatement.setString(7, labelpic.getText());
                preparedStatement.setString(8, addetat.getText());
                //preparedStatement.executeUpdate();
                int  rs=preparedStatement.executeUpdate();
                if (rs>0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Car Succesfuly Added!");
                    alert.showAndWait();

                } else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Car Insert Failed!");
                    alert.showAndWait();
                }


            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Car already exist");
                alert.showAndWait();

            }

            ConnexDB.Deconnexion();
        } catch (SQLException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
        }

    }


    public void cancel(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    private Label matriculelabel;

    void setTextField(String matricule,int price, String dateV, String dateA) {

        carM = matricule;
        matriculelabel.setText(matricule);
        addprice.setText(String.valueOf(price));
        addvisit.setValue(LocalDate.parse(dateV));
        addassur.setValue(LocalDate.parse(dateA));
    }
    void setUpdate(boolean b) {
        this.update = b;

    }


}