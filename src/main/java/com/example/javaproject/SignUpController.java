package com.example.javaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.javaproject.ConnexDB.*;

public class SignUpController {
    @FXML
    private TextField prenomtxtfld;
    @FXML
    private TextField nomtxtfld;
    @FXML
    private TextField CINtxtfld;
    @FXML
    private TextField agetxtfld;
    @FXML
    private TextField teltxtfld;
    @FXML
    private TextField usernametxtfld;
    @FXML
    private PasswordField pwdtxtfld;
    @FXML
    private PasswordField pwdConftxtfld;
    @FXML
    private TextField Npermistxtfld;
    @FXML
    private TextField catPtxtfld;
    @FXML
    private DatePicker datePtxtfld;

    Users user = null;

    @FXML
    private void signUp(ActionEvent e) throws SQLException {
        GetConnexion();
        String username = usernametxtfld.getText();
        String CIN = CINtxtfld.getText();
        String prenom = prenomtxtfld.getText();
        String nom = nomtxtfld.getText();
        int age = Integer.parseInt(agetxtfld.getText());
        String tel = teltxtfld.getText();
        String pwd = pwdtxtfld.getText();
        String pwdC = pwdConftxtfld.getText();
        String Npermis = Npermistxtfld.getText();
        String categorie = catPtxtfld.getText();
        String dateVal = String.valueOf(datePtxtfld.getValue());

        if (username.isEmpty() || CIN.isEmpty() || prenom.isEmpty() || nom.isEmpty() || tel.isEmpty() || pwd.isEmpty() || pwdC.isEmpty() || Npermis.isEmpty() || categorie.isEmpty() || dateVal.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        } else {
            insert();
            clean();
        }
    }

    private void clean() {
        usernametxtfld.setText(null);
        CINtxtfld.setText(null);
        prenomtxtfld.setText(null);
        datePtxtfld.setValue(null);
        nomtxtfld.setText(null);
        agetxtfld.setText(null);
        pwdtxtfld.setText(null);
        pwdConftxtfld.setText(null);
        teltxtfld.setText(null);
        Npermistxtfld.setText(null);
        catPtxtfld.setText(null);
    }
    private void insert()  {
        try {
            GetConnexion();


            if(checkUser()==false){
                String qr="INSERT INTO `users`(`username`, `CIN`, `prenom`, `nom`, `age`,`tel`, `password`, `N_permis`, `Categorie_permis`, `date_finVali`, `isAdmin`) VALUES (?,?,?,?,?,?,?,?,?,?,0)";
                prep= mycnx.prepareStatement(qr);
                prep.setString(1,usernametxtfld.getText());
                prep.setString(2,CINtxtfld.getText());
                prep.setString(3,prenomtxtfld.getText());
                prep.setString(4,nomtxtfld.getText());
                prep.setInt(5, Integer.parseInt(agetxtfld.getText()));
                prep.setString(6,teltxtfld.getText());
                prep.setString(7,pwdtxtfld.getText());
                prep.setInt(8, Integer.parseInt(Npermistxtfld.getText()));
                prep.setString(9,catPtxtfld.getText());
                prep.setString(10, String.valueOf(datePtxtfld.getValue()));
                prep.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfuly Signed Up!");
                    alert.showAndWait();

            }
            // on doit s assurer si user n existe pas deja inscrits !
            else { // cad True user  existe  dans db
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("You already have account!");
                alert.showAndWait();
            }
            Deconnexion();
        }catch (SQLException eq){
            System.out.println(eq);
        }

    }

    public Boolean checkUser() throws SQLException {
        boolean check = false;
            try {
                GetConnexion();
                prep = mycnx.prepareStatement("SELECT * FROM users WHERE CIN =? ");
                //prep.clearParameters()
                prep.setString(1, CINtxtfld.getText());
                rs = prep.executeQuery();
                while (rs.next()) {
                    String CIN = rs.getString("CIN");
                    if (CIN.equals(CINtxtfld.getText())) // on compare les deux chaines de caracteres //
                    {
                        check = true;
                        return check;
                    } else {
                        check = false;
                        return check;
                    }
                }

            } catch (SQLException ex) {
                System.out.println("1"+ex);
            }


            return check;
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


