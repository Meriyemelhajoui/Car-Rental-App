package com.example.javaproject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.javaproject.Voiture.ETAT_DISPONIBLE;

public class LoginForm implements Initializable {

    @FXML
    TextField txtUser;
    @FXML
    TextField txtPass;
    @FXML
    Label invalid;
    @FXML
    Button loginButton;
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;

    Users u= new Users();
    LoginController lg = new LoginController();





    public LoginForm() throws SQLException {
    }
    int is;
    Statement st;
    public boolean isLogin() throws SQLException
    {
        st = ConnexDB.GetConnexion().createStatement();
        System.out.println(st);
        ResultSet res= st.executeQuery("select * from users where username ='" +txtUser.getText()+ "' and password ='" +txtPass.getText()+"'");
        if(res.next())
            return true;
        return false;
    }


    public void isSign(ActionEvent e) throws SQLException, IOException {
        connection = ConnexDB.GetConnexion();
        query = "SELECT * FROM users WHERE username= ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,txtUser.getText());
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
             is = Integer.parseInt(resultSet.getString("isAdmin"));
        }


        if(isLogin())
        {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent pane1 = (Parent) loader1.load();
            HomeController username= loader1.getController();
            //carName.setTextField(getCarNameLable(),getCarPriceLabel());
            username.transferMessage(txtUser.getText());
            Stage stage = new Stage();
            FXMLLoader fxmlLoader;
            //fxmlLoader = new FXMLLoader(Main.class.getResource("Dashboard.fxml"));
            System.out.println(is);
            if(is == 1){

                 fxmlLoader = new FXMLLoader(Main.class.getResource("Dashboard.fxml"));
                Parent pane = (Parent) fxmlLoader.load();
                Scene scene = new Scene(pane);
                stage.setTitle("Rentify");
                //stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
            }else{
                 fxmlLoader = new FXMLLoader(Main.class.getResource("Home.fxml"));
                Scene scene = new Scene(pane1);
                stage.setTitle("Rentify");
                //stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
            }

        }else
            invalid.setText("Invalid Login. Please try again");

    }
    @FXML
    private Hyperlink regist;
    @FXML
    private void gotToSignUp(MouseEvent event)
    {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("Registration.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage)regist.getParent().getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);

        }
    }
    private Label carN;
    public Label getCarN() {
        return carN;
    }

    public void setCarN(Label carN) {
        this.carN = carN;
    }

    private String n;
    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            //carN.setText(n);

        });
    }
}
