package com.example.javaproject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.javaproject.ConnexDB.*;


public class Voiture {
    public static final SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

    public static final String ETAT_DISPONIBLE = "disponible";
    public static final String ETAT_LOUE = "Loué";
    public static final String ETAT_EN_COURS = "En cours de réparation";
    private String MatriculeV;
    private Date dateVisite;
    private Date dateAssu;
    private String Marque;
    private String Modele;
    private int PrixParjr;
    private String imageV;
    private String etat;

    Voiture(String matricule, String marque, String modele, int price, String img) {
        this.MatriculeV = matricule;
        this.Marque = marque;
        this.Modele = modele;
        this.PrixParjr = price;
        this.imageV = img;
    }


    Voiture() {
    }

    // construc 1
    Voiture(String Mtv, String dv, String da, String marque, String Modele, int price, String img, String etat) throws ParseException {
        this.MatriculeV = Mtv;
        this.dateVisite = sdformat.parse(dv);
        this.dateAssu = sdformat.parse(da);
        this.Marque = marque;
        this.Modele = Modele;
        this.PrixParjr = price;
        this.imageV = img;
        this.etat = etat;
    }

    // constru 2
    Voiture(String Mtv, String dv, String da, String marque, String Modele, int price, String img) throws ParseException {
        this.MatriculeV = Mtv;
        this.dateVisite = sdformat.parse(dv);
        this.dateAssu = sdformat.parse(da);
        this.Marque = marque;
        this.Modele = Modele;
        this.PrixParjr = price;
        this.imageV = img;
        this.etat = ETAT_DISPONIBLE;
    }


    public String getDateAssu() {
        return sdformat.format(this.dateAssu);
    }

    public void setDateAssu(String dateAssu) throws ParseException {
        this.dateAssu = sdformat.parse(dateAssu);
    }

    public String getDateVisite() {


        return sdformat.format(this.dateVisite);
    }

    public void setDateVisite(String dateVisi) throws ParseException {
        this.dateAssu = sdformat.parse(dateVisi); // parse convertit de string vers date avec une forme decrite
    }

    public String getImageV() {
        return imageV;
    }

    public void setImageV(String imageV) {
        this.imageV = imageV;
    }

    public String getMarque() {
        return Marque;
    }

    public void setMarque(String marque) {
        Marque = marque;
    }

    public String getMatriculeV() {
        return MatriculeV;
    }

    public void setMatriculeV(String matriculeV) {
        this.MatriculeV = matriculeV;
    }

    public String getModele() {
        return Modele;
    }

    public void setModele(String modele) {
        Modele = modele;
    }

    public int getPrixParjr() {
        return PrixParjr;
    }

    public void setPrixParjr(int prixParjr) {
        PrixParjr = prixParjr;
    }

    public String getEtat() {
        return this.etat;
    }

    public void SetEtat(String e) {
        this.etat = e;
    }


    // methode pour supprimer Voiture  mzyana :) //
    public void Supprimer() throws SQLException {
        try {
            GetConnexion();
            String qr = "SELECT * FROM voiture WHERE MatriculeV=? ";
            prep = mycnx.prepareStatement(qr);
            prep.setString(1, getMatriculeV());
            rs = prep.executeQuery();

            while (rs.next()) {

                String matri = rs.getString("MatriculeV");
                if (matri.equals(getMatriculeV())) // si le maricule existe on va supprimerVoiture //
                {
                    String quee = "DELETE from voiture WHERE MatriculeV=?";
                    prep = mycnx.prepareStatement(quee);
                    prep.setString(1, matri);
                    prep.executeUpdate();
                    System.out.println("La voiture est supprime avec succes ! ");

                } else {
                    System.out.println("Matricule n existe pas , pas de suppression ! ");
                }
            }


        } catch (SQLException sq) {
            System.out.println(sq);
        }
        Deconnexion();
    }


    @Override
    public String toString() {
        return "Voiture{" +
                "MatriculeV='" + MatriculeV + '\'' +
                ", dateVisite=" + dateVisite +
                ", dateAssu=" + dateAssu +
                ", Marque='" + Marque + '\'' +
                ", Modele='" + Modele + '\'' +
                ", PrixParjr=" + PrixParjr +
                ", imageV='" + imageV + '\'' +
                ", etat='" + etat + '\'' +
                '}';
    }
}


