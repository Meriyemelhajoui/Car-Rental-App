package com.example.javaproject;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.javaproject.ConnexDB.*;


public class Users {
    private String username; // if username==root -> admin else user
    private String  password;
    private String CIN;
    private String prenom;
    private String nom ;
    private int age ;
    private String tel;
    private int  N_permis;
    private String Catergorie;
    private Date date_finVali;
    private boolean isAdmin=false;
    public static final SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

    Users(){
        this.isAdmin=false;
    }
    Users(String username , String CIN , String prenom , String nom, int  age , String password , int  N_permis , String Categorie , String date_finV) throws ParseException {
        this.username=username;
        this.CIN=CIN;
        this.prenom=prenom;
        this.nom=nom;
        this.age=age;
        this.password=password;
        this.N_permis=N_permis;
        this.Catergorie=Categorie;
        this.date_finVali= sdformat.parse(date_finV);
        this.isAdmin=false;
    }
    //constructeur pour l'affichage des donnees du client
    Users(String username , String CIN , String prenom , String nom, int  age , int  N_permis , String Categorie, String t ) throws ParseException {
        this.username=username;
        this.CIN=CIN;
        this.prenom=prenom;
        this.nom=nom;
        this.age=age;
        this.N_permis=N_permis;
        this.Catergorie=Categorie;
        this.tel=t;
        this.isAdmin=false;
    }
    // constructeur d admin
    Users(String username , String password){
        if(username =="root" && password=="root"){
            this.username="root";
            this.password="root";
            this.prenom="";
            this.CIN="";
            this.N_permis= 0;
            this.date_finVali=null;
            this.isAdmin=true; // if true --> admin
        }

    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(isAdmin==false){
            if(age>18)
            {
            this.age = age;
            }else
                this.age=18;
        }
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        if(isAdmin==false)
            this.CIN = CIN;
    }


    public String getDateVisite() {
        return sdformat.format(this.date_finVali);
    }
    public void setDate_finVali(String date_finVali) throws ParseException{
        if(isAdmin==false ) { // uen autre condition sur la date
            this.date_finVali=sdformat.parse(date_finVali);
    }}

    public int getN_permis() {

        return N_permis;
    }

    public void setN_permis(int n_permis) {
        if(isAdmin==false) // une autre condiiton :  selon la date de permis si il est perime
            N_permis = n_permis;
    }

    public String getPassword() {
        return password; // une recherche dans la DB si le mot de passe existe
    }

    public void setPassword(String password) {
        if(isAdmin==false )
            this.password = password;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        if(isAdmin==false)
            this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getCatergorie() {
        return Catergorie;
    }

    public void setCatergorie(String catergorie) {
        Catergorie = catergorie;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    // fct qui retourne l ensemble des infos //
    public  static void ReturnInfos() throws SQLException{
        String username=rs.getString("username");
        String CIN = rs.getString("CIN");
        String prenom = rs.getString("prenom");
        String nom = rs.getString("nom");
        int age = rs.getInt("age");
        String password = rs.getString("password");
        String N_permis = rs.getString("N_permis");
        Date date_finVali = rs.getDate("date_finVali");
        System.out.println( "le nom du client : " + nom +"\n"+ " le Prenom du client : " + prenom + "\n"+ "Le Cin du Client :" + CIN + "\n"+ "L'age du client :  "+ age+  "\n" + " Le numero de permis : "+ N_permis +"\n" +" La date de fin de validite du permis :"  + date_finVali);
        System.out.println();
    }

// methode d admin pour voir tous les clients de l application : mzyana :) //
    public  void DisplayClients() throws SQLException {
        if(isAdmin) // cad isAdmin==true//
        {
            try {
                GetConnexion();//cnx avec la base de donne
                String query="Select * from users WHERE username != 'root' AND password != 'root' "; // pr ne pas selectionner l admin
                // open statement//
                state=mycnx.createStatement();
                rs = state.executeQuery(query);
                while(rs.next()){
                    ReturnInfos();
                }
            }catch (SQLException ex){
                System.out.println(ex);
            }

        }
        Deconnexion();
    }

    // methode d admin pour rechercher un client par CIN mzyana :) //

    public void SearchClient (String cin ) throws SQLException{
        try {
            if(isAdmin)
            {
            GetConnexion(); // faire la cnx avec DB
            String query = ("SELECT * FROM users WHERE CIN = ? ");

            prep = mycnx.prepareStatement(query);
            prep.setString(1, cin);
            rs = prep.executeQuery();
            while (rs.next())
            {
                String CIN = rs.getString("CIN");
                if (CIN.equals(cin)) // on compare les deux chaines de caracteres //
                {
                    ReturnInfos();
                }
                else
                {
                    System.out.println("Le Cin que vous avez entrez ne correspond a aucun client ! ");
                }
            }
            }
        } catch (SQLException sq )
        {
            System.out.println(sq);
        }
        Deconnexion();


    }





}





