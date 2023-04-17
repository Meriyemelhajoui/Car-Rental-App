package com.example.javaproject;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.javaproject.ConnexDB.GetConnexion;

public class ViewAllController implements Initializable {



    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;
    Voiture V;
    @FXML
    TextField prixmin;
    @FXML
    TextField prixmax;
    @FXML
    private Button marqueSearch;

    @FXML
    private TextField marquetxtfld;
    private  MyListener myListener;
    int Primin;
    int Primax;
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;




    private List<Voiture> voitures = new ArrayList<>();


    private List<Voiture> getData() throws SQLException {
        List<Voiture> voitures = new ArrayList<>();
        Voiture voiture;

        connection =ConnexDB.GetConnexion();
        try {
            voitures.clear();

            query = "SELECT * FROM `voiture`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                voitures.add(new Voiture(
                        resultSet.getString("MatriculeV"),
                        resultSet.getString("Marque"),
                        resultSet.getString("Modele"),
                        resultSet.getInt("PrixParJour"),
                        resultSet.getString("image")
                ));

            }
        } catch (SQLException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
        }

        return voitures;
    }



    public List<Voiture> marqueSearchF()
    {
        System.out.println("hana");
        List<Voiture> voitures = new ArrayList<>();
        Voiture voiture;
        voitures.clear();
        try {
            connection= ConnexDB.GetConnexion();
            String query = ("SELECT * FROM voiture WHERE Marque=?");
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,marquetxtfld.getText() );
            resultSet = preparedStatement.executeQuery();
            System.out.println("hi2"+marquetxtfld.getText());
            while (resultSet.next()){
                //String model=resultSet.getString("Marque");
                String marque=resultSet.getString("Marque");
                System.out.println("hi"+marquetxtfld.getText());
                System.out.println("hi"+marque);
                System.out.println(marque.equals(marqueSearch.getText()));
                // Test if the marque is the same as the one in the database
                if(!(marque.equals(marqueSearch.getText()))){
                    System.out.println("Ce modele existe voila les voitures qui sont de ce type ! ");
                    voitures.add(new Voiture(
                            resultSet.getString("MatriculeV"),
                            resultSet.getString("Marque"),
                            resultSet.getString("Modele"),
                            resultSet.getInt("PrixParJour"),
                            resultSet.getString("image")

                    ));
                }else {
                    throw new ExistanceException("Ce Module e voiture n existe pas ");

                }
            }
        } catch(ExistanceException ex){
            System.out.println(ex);
        }
        catch (SQLException s  ){
            System.out.println(s);
        }
        return voitures;
    }
    public List<Voiture> prixSearch() throws SQLException {

        List<Voiture> voitures = new ArrayList<>();
        Voiture voiture;
        voitures.clear();
        try {
            connection= ConnexDB.GetConnexion();
            String query = ("SELECT * FROM voiture WHERE PrixParJour BETWEEN ? AND ?");
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(prixmin.getText()));
            preparedStatement.setInt(2,Integer.parseInt(prixmax.getText()));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                voitures.add(new Voiture(
                        resultSet.getString("MatriculeV"),
                        resultSet.getString("Marque"),
                        resultSet.getString("Modele"),
                        resultSet.getInt("PrixParJour"),
                        resultSet.getString("image")

                ));
            }
        } catch (SQLException s  ){
            System.out.println(s);
        }
        System.out.println(voitures);
        return voitures;
    }
    public void prixFiltre(ActionEvent e) throws IOException, SQLException {
        //clear
        grid.getChildren().clear();
        voitures.clear();
        voitures.addAll(prixSearch());
        int column = 0;
        int raw = 1;
        try {
            for(int i=0; i<voitures.size(); i++)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Items.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemsController itemsController = fxmlLoader.getController();
                itemsController.setData(voitures.get(i),myListener);

                if(column == 2)
                {
                    column = 0;
                    raw++;
                }
                grid.add(anchorPane,column++,raw);
                GridPane.setMargin(anchorPane,new Insets(10));
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void marqueFiltre(ActionEvent e) throws IOException {
        //clear
        grid.getChildren().clear();
        voitures.clear();
        voitures.addAll(marqueSearchF());
        int column = 0;
        int raw = 1;
        try {
            for(int i=0; i<voitures.size(); i++)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Items.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemsController itemsController = fxmlLoader.getController();
                itemsController.setData(voitures.get(i),myListener);

                if(column == 2)
                {
                    column = 0;
                    raw++;
                }
                grid.add(anchorPane,column++,raw);
                GridPane.setMargin(anchorPane,new Insets(10));
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            voitures.addAll(getData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        myListener = new MyListener() {
            @Override
            public void onClickListener(Voiture voiture) {

            }
        };
        int column = 0;
        int raw = 1;
        try {
            for(int i=0; i<voitures.size(); i++)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Items.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemsController itemsController = fxmlLoader.getController();
                itemsController.setData(voitures.get(i),myListener);

                if(column == 2)
                {
                    column = 0;
                    raw++;
                }
                grid.add(anchorPane,column++,raw);
                GridPane.setMargin(anchorPane,new Insets(10));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    public void PriceFilter(ActionEvent e) throws SQLException, IOException {

        Primin = Integer.parseInt(prixmin.getText());
        Primax = Integer.parseInt(prixmax.getText());

      //  V.RechercherVoitureParPrix(Primin ,Primax);

            Node node = (Node) e.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Rentify");
            stage.setScene(scene);
            stage.show();


   }

   @FXML
    public void Login(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rentify");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SignUp(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rentify");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void ContactUs(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("About.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rentify");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private ImageView prev;
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



