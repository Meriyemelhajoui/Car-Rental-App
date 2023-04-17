package com.example.javaproject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.javaproject.Voiture.ETAT_DISPONIBLE;
import static com.example.javaproject.Voiture.sdformat;

public class AvailCarsController implements Initializable {

    @FXML
    private Button bookBtn;

    @FXML
    private ImageView carImg;


    @FXML
    private Label carNameLable;
    public String getCarNameLable() {
        return carNameLable.getText();
    }

    public String getCarPriceLabel() {
        return carPriceLabel.getText();
    }

    @FXML
    private Label carPriceLabel;

    @FXML
    private VBox chosencarCard;

    @FXML
    private GridPane grid;

    @FXML
    private Label marqueLabel;

    @FXML
    private ScrollPane scroll;

    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    private  MyListener myListener;


    @FXML
    private void receiveData(ActionEvent event) {
        String name = this.usernameIfLogLabel2.getText();

    }
    private List<Voiture> voitures = new ArrayList<>();


    private List<Voiture> getData(String dateReturn) throws SQLException, ParseException {
        List<Voiture> voitures = new ArrayList<>();
        Voiture voiture;


        Date DF = sdformat.parse(dateReturn);
        LocalDate d = LocalDate.parse(dateReturn);
        System.out.println(DF);
        System.out.println(dateReturn);
        connection =ConnexDB.GetConnexion();
        try {
            voitures.clear();

            query = "SELECT * FROM voiture WHERE etat= ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,ETAT_DISPONIBLE);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                String datevisi=resultSet.getString("DateVisite");
                String dateAssu=resultSet.getString("DateAssu");
                Date DV= sdformat.parse(datevisi);
                Date DA = sdformat.parse(dateAssu);
                if(DA.compareTo(DF)>0 && DV.compareTo(DF)>0)
                {
                    voitures.add(new Voiture(
                            resultSet.getString("MatriculeV"),
                            resultSet.getString("Marque"),
                            resultSet.getString("Modele"),
                            resultSet.getInt("PrixParJour"),
                            resultSet.getString("image")

                   ));
                }

            }
        } catch (SQLException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
        }

        return voitures;
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


   String dateR ;
    String matricule;

    public void setDateR(String dateR) {
        this.dateR = dateR;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bookBtn.setOnAction(event -> {
            try {
                bookNow();

            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
            Platform.runLater(() -> {
                try {
                    voitures.addAll(getData(dateR));

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if(voitures.size() > 0)
                {
                    System.out.println("hey");
                    setChosenCar(voitures.get(0));
                    myListener = new MyListener() {
                        @Override
                        public void onClickListener(Voiture voiture) {
                            setChosenCar(voiture);
                        }
                    };
                }
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

            });



    }

    private Image image;
    private void setChosenCar(Voiture voiture)
    {
        matricule = voiture.getMatriculeV();
        carNameLable.setText(voiture.getMarque());
        carPriceLabel.setText(voiture.getPrixParjr() + Main.CURRENCY);
        image = new Image(voiture.getImageV());
        chosencarCard.setStyle("-fx-background-radius: 30;");
        carImg.setImage(image);
        marqueLabel.setText(voiture.getModele());
    }

    //Function that takes datePickup from Home.fxml and send it to AvailCars.fxml



    private String DateP;
    public void setDateP(String dateP) {
        DateP = dateP;

    }
    public int CalculPrixTotal(int prix ) throws ParseException // dk le prix aykon howa getPrixParJour dial dk voiture
    {
        int jr =  Location.CalculerNbrJr(DateP,dateR); // par exemple user 5tar hd deux dates
        int somme = prix * jr ;
        System.out.println("Hle prix total de location entre ces dates est : " + somme);
        return somme;
    }
//    public static int CalculRestePrix(int PrixActuel) throws ParseException // prixActuel howa ali saisah client f textfield
//    {
//        int RestePrix=CalculPrixTotal(1000)-PrixActuel;
//        System.out.println("Le reste de prix est : " + RestePrix);
//        return RestePrix;
//    }

    private int Tprice;
    public  int getTprice() throws ParseException {
        System.out.println("hi");
        Tprice = CalculPrixTotal(Integer.parseInt(getCarPriceLabelSplit()));

        return Tprice;
    }





    // restruct MAD from getCarsPriceLabel to int
    private String getCarPriceLabelSplit() {
        String carPrice = carPriceLabel.getText();
        String[] carPriceArray = carPrice.split("MAD");
        return carPriceArray[0];
    }

    @FXML
    private Label usernameIfLogLabel2;

    public String getUserameIfLogLabel() {
        return usernameIfLogLabel2.getText();
    }

    String name;
    @FXML
    public void setUserameIfLogLabel(String name) {
        this.name = name;
        System.out.println("hana "+name);
        usernameIfLogLabel2.setText(name);
    }

    public void transferMessage(String username, String dateR, String dateP)
    {
        usernameIfLogLabel2.setText(username);
        this.dateR = dateR;
        this.DateP = dateP;
    }
    @FXML
    private void bookNow() throws IOException, ParseException {
        //recuprer les donnees a partir du login page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent pane = (Parent) loader.load();
        LoginForm state = loader.getController();
        LoginForm nom = loader.getController();
        nom.setN(getCarNameLable());

        //recuperer les donnees a partir du login page
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("Reservation.fxml"));
        Parent pane1 = (Parent) loader1.load();
        ReservationController carData= loader1.getController();
        carData.transferMessage(getCarNameLable(), String.valueOf(getTprice()), DateP,dateR,matricule);

        System.out.println(usernameIfLogLabel2.getText());
        System.out.println((usernameIfLogLabel2.getText()).equals("username"));

        if(!((usernameIfLogLabel2.getText()).equals("username")))
        {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
            Scene scene = new Scene(pane1);
            stage.setTitle("Book Now");
            stage.setScene(scene);
            stage.show();
        } else{

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Reservation.fxml"));
            Scene scene = new Scene(pane);
            stage.setTitle("Book Now");
            stage.setScene(scene);
            stage.show();
        }



    }
}
