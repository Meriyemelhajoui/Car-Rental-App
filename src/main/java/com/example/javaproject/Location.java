package com.example.javaproject;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static com.example.javaproject.ConnexDB.*;
import static com.example.javaproject.Voiture.*;


import com.example.javaproject.Voiture;
public class Location {
    public static final String ETAT_DISPONIBLE = "disponible";
    public static final String ETAT_LOUE = "loue";
    private  String MatriculeV ;
    private  String CIN ;
    private  String datedeb;
    private   String dateFin;
    private  int Prix_Total;
    private   int Prix_Avance;
   // private static ArrayList<Location> locationlist ;

    public Location(String MatriculeV, String c, String datedeb, String dateFin, int Prix_Total, int Prix_Avance) throws ParseException {
        this.MatriculeV=MatriculeV;
        this.CIN= c;
        this.datedeb = datedeb;
        this.dateFin = dateFin;
        this.Prix_Total = Prix_Total;
        this.Prix_Avance = Prix_Avance;
    }


    public Location() throws ParseException {}
    public String getMatriculeV() {
        return  MatriculeV;
    }

    public void setMatriuleV(String MatriculeV) {
        this.MatriculeV = MatriculeV;
    }

    public String getCIN() {
        return this.CIN;
    }

    public void setC(String CIN) {
        this.CIN = CIN;
    } //

    public String getDatedeb() {
        return datedeb;
    }

    public void setDatedeb(String datedeb) {
        this.datedeb = datedeb;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public int getPrix_Avance() {
        return Prix_Avance;
    }

    public void setPrix_Avance(int prix_Avance) {
        Prix_Avance = prix_Avance;
    }

    public int getPrix_Total() {
        return Prix_Total;
    }

    public void setPrix_Total(int prix_Total) {
        Prix_Total = prix_Total;
    }




    // methode qui calcule le nombre de jours entre deux dates //
    public static  int CalculerNbrJr(String datedeb , String datefin) throws ParseException {
        Date dateDebu= sdformat.parse(datedeb); // hna aykn dk datedebut ali 5tar user
        Date dateFin= sdformat.parse(datefin);// hna aykn dk datefin ali 5tar user
        long diff=dateFin.getTime()-dateDebu.getTime();
        float res=(diff/(1000*60*60*24));
        System.out.println("Le nombre de jours entre ces deux dates est : "+ (int) res);
        return (int) res;
    }
    // methode qui calcule le prix total de location d une voiture entre deux dates //
    public static int CalculPrixTotal(int prix) throws ParseException // le prix sera  getPrixParJour  de cette voiture
    {
        int jr =  CalculerNbrJr("2022-12-24","2022-12-30"); // par exemple user 5tar hd deux dates
        int somme = prix * jr ;
        System.out.println("le prix total de location entre ces dates est : " + somme);
        return somme;
    }

    // methode pour calculer le prix restant d une location //
    public static int CalculRestePrix(int PrixActuel ) throws ParseException // prixActuel c le prix qui entre Utilisateur
    {
        int RestePrix=CalculPrixTotal(1000)-PrixActuel;
        System.out.println("Le reste de prix est : " + RestePrix);
        return RestePrix;
    }


     //Methode pour ajouter une location ou bien faire une reservation //
    public void Faireunelocation( String Matriculev ,String cin ) throws ParseException {
        try
        {

            GetConnexion();
            if(checkDate()==true && checkEtat()==true ) {
                String query = "INSERT INTO location VALUES(?,?,?,?,?,?)";
                prep = mycnx.prepareStatement(query);
                prep.setString(1, Matriculev);
                prep.setString(2, cin);
                prep.setInt(3, CalculPrixTotal(1000));
                prep.setInt(4, 1000);
                prep.setString(5, "2022-12-24");
                prep.setString(6, "2022-12-30");
                int  rs=prep.executeUpdate();
                System.out.println("Hey");
               if(rs>0) {
                    System.out.println("Reservation good ");
                }
            }else {
                System.out.println("Cette voiture n est pas dispo , Reservation Echoue ! ");
            }
       }
        catch (SQLIntegrityConstraintViolationException e ){
            System.out.println(e);}
        catch (SQLException e){
            System.out.println(e);
        }
    }


// methode pr verifier la date de fin de reservation avec la date dass et la date de visite  dial whd matricule b dabt ! //
    public static  boolean checkDate() throws SQLException, ParseException,SQLIntegrityConstraintViolationException {
        boolean checkD= false;
        try {
            GetConnexion();
            String query = "SELECT *  FROM voiture WHERE MatriculeV=?  LIMIT 1";
            prep = mycnx.prepareStatement(query);
            prep.setString(1, "A12438-32");
            rs = prep.executeQuery();
            while (rs.next()) {
                String dateVisi = rs.getString("DateVisite");
                String dateAssu = rs.getString("DateAssu");
                Date DV = sdformat.parse(dateVisi);
                Date DA = sdformat.parse(dateAssu);
                String dateFinreservation = "2022-01-15";
                Date DR = sdformat.parse(dateFinreservation);
                if (DR.compareTo(DV) < 0 && DR.compareTo(DA) < 0) {
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
    // methode ali retourne  l etat d une Voiture !  si elle est disponible ou nn  //
    public  static boolean checkEtat() throws SQLException {
        boolean check=false;
        try {
            GetConnexion();
            String query ="SELECT etat FROM voiture WHERE MatriculeV=?";
            prep= mycnx.prepareStatement(query);
            prep.setString(1,"A12438-32");
            rs=prep.executeQuery();
            while(rs.next()){
                String state =rs.getString("etat");
                if(state.equals(ETAT_DISPONIBLE)){
                    check=true;
                }
            }

        }catch (SQLException s){
            System.out.println(s);
        }
        return  check;
    }
    
// methode qui retourne un tableau de matriculeV reserve//
    public static ArrayList GetTbMatriculeV() throws SQLException {
        ArrayList<String>tableauMatricule= new ArrayList<>();
        try {
            GetConnexion();
            state=mycnx.createStatement();
            rs=state.executeQuery("SELECT MatriculeV from location ");
            while (rs.next()){
                tableauMatricule.add(rs.getString("MatriculeV"));
                // pour chaque matricule de la table location on va set l etat a loue //
                for(int i = 0; i<tableauMatricule.size();i++){
                    System.out.println(tableauMatricule.get(i));}

            }

        }catch (SQLException e){
            System.out.println(e);
        }
        return tableauMatricule;
    }

// methode qui set etat-loue pour chaque matricule de voiture loue //
    public  static void SetEtat(ArrayList<String> tbmatricule) throws SQLException {
        try {

            for(int i = 0; i<tbmatricule.size();i++){
                String matri=tbmatricule.get(i);
                String query="UPDATE  voiture SET etat=? WHERE MatriculeV=?";
                prep=mycnx.prepareStatement(query);
                prep.setString(1,"loue");
                prep.setString(2,matri);
                int  rs=prep.executeUpdate();
                if(rs>0){
                    System.out.println("L'etat des voitures reserves est updated ");
                }
                }


        }catch (SQLException e){
            System.out.println(e);
        }
    }


    // methode qui retourne la liste des voitures reserves //
    public  static void ListerVoitureRese() throws SQLException {
        try {
            GetConnexion();
            String query="SELECT * from voiture V INNER JOIN location L ON V.MatriculeV=l.MatriculeV";
            prep=mycnx.prepareStatement(query);
            rs=prep.executeQuery();
            while (rs.next()){
                System.out.println("la liste des voitures reserves : ");
                String MatriculeV = rs.getString("MatriculeV");
                String datev= rs.getString("DateVisite");
                String dateAssu=rs.getString("DateAssu");
                String Marque =rs.getString("Marque");
                String Modele = rs.getString("Modele");
                int  prix = rs.getInt("PrixParJour");
                String image=rs.getString("image");
                System.out.println(" Modele " + Modele + "\n" + "Marque : "+ Marque +"\n"+  "Matricule "+ MatriculeV + "\n"+"Sa date de visite : "+ datev +"\n"+ "Sa date d assurance "+ dateAssu +"\n"+" Son prix : "+ prix );

            }

        }catch (SQLException e){
            System.out.println(e);
        }
    }



}
