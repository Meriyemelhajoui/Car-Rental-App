package com.example.javaproject;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

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

public class DashboardController implements Initializable {
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
    public void Setting(ActionEvent e)
    {}




    @FXML
    private TableView<Voiture> carTable;
    @FXML
    private TableColumn<Voiture,String> matricule;
    @FXML
    private TableColumn<Voiture,String> marque;
    @FXML
    private TableColumn<Voiture,String> model;
    @FXML
    private TableColumn<Voiture,Date> visit;
    @FXML
    private TableColumn<Voiture,Date> assurance;
    @FXML
    private TableColumn<Voiture,Integer> prix;
    @FXML
    private TableColumn<Voiture,String> image;
    @FXML
    private TableColumn<Voiture,String> etat;
    @FXML
    private TableColumn<Voiture,String> action;


    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Voiture voiture = null ;

    ObservableList<Voiture> voitureList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            loadDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private ImageView addC;
    @FXML
    private void getAddView(MouseEvent event)
    {

        try {

            Parent parent = FXMLLoader.load(getClass().getResource("AddCar.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage)addC.getParent().getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);

        }

    }
    @FXML
    private void refreshTable()  {

        try {
            voitureList.clear();

            query = "SELECT * FROM `voiture`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                voitureList.add(new Voiture(
                        resultSet.getString("MatriculeV"),
                        resultSet.getString("DateVisite"),
                        resultSet.getString("DateAssu"),
                        resultSet.getString("Marque"),
                        resultSet.getString("Modele"),
                        resultSet.getInt("PrixParJour"),
                        resultSet.getString("image"),
                        resultSet.getString("etat")
                ));
                carTable.setItems(voitureList);
            }
        } catch (SQLException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }


    private void loadDate() throws SQLException {
        connection = ConnexDB.GetConnexion();
        refreshTable();


        matricule.setCellValueFactory(new PropertyValueFactory<>("MatriculeV"));
        marque.setCellValueFactory(new PropertyValueFactory<>("Marque"));
        model.setCellValueFactory(new PropertyValueFactory<>("Modele"));
        visit.setCellValueFactory(new PropertyValueFactory<>("DateVisite"));
        assurance.setCellValueFactory(new PropertyValueFactory<>("DateAssu"));
        prix.setCellValueFactory(new PropertyValueFactory<>("PrixParjr"));
        image.setCellValueFactory(new PropertyValueFactory<>("imageV"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        //add cell of button action
        Callback<TableColumn<Voiture, String>, TableCell<Voiture, String>> cellFoctory = (TableColumn<Voiture, String> param) -> {
            // make cell containing buttons
            final TableCell<Voiture, String> cell = new TableCell<Voiture, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        ImageView editIcon = new ImageView("E:\\School\\S3\\Java\\JavaProject\\src\\main\\resources\\image\\update.png");
                        ImageView deleteIcon = new ImageView("E:\\School\\S3\\Java\\JavaProject\\src\\main\\resources\\image\\delete.png");



                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                voiture = carTable.getSelectionModel().getSelectedItem();
                                String qr = "DELETE FROM `voiture` WHERE MatriculeV=?";
                                connection = ConnexDB.GetConnexion();
                                preparedStatement=connection.prepareStatement(qr);
                                preparedStatement.setString(1,voiture.getMatriculeV());
                                //preparedStatement.executeQuery();
                                preparedStatement.execute();
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setHeaderText(null);
                                alert.setContentText("Car Succesfuly Deleted!");
                                alert.showAndWait();
                                refreshTable();
                                ConnexDB.Deconnexion();
                                } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            voiture = carTable.getSelectionModel().getSelectedItem();
                            System.out.println(voiture);
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("UpdateCar.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddCarController addCarController = loader.getController(); addCarController.setUpdate(true);
                            addCarController.setTextField(voiture.getMatriculeV(),voiture.getPrixParjr(), voiture.getDateVisite(),voiture.getDateAssu());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.show();
                            refreshTable();
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        setGraphic(managebtn);
                        setText(null);

                    }
                }

            };

            return cell;
        };
        action.setCellFactory(cellFoctory);
        carTable.setItems(voitureList);
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
