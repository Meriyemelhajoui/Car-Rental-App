package com.example.javaproject;
import javax.xml.transform.Result;
import java.sql.*;

public   class ConnexDB {
    public static Connection mycnx;
    public static PreparedStatement prep;
    public static  Statement  state ;
    public static  ResultSet rs;
    public static void main(String[] args) throws SQLException {

            GetConnexion();
           Deconnexion();
        }

        // connexion avec la base de donne //
        public static Connection GetConnexion() throws SQLException{
            String url="jdbc:mysql://localhost:3306/rentify";
            String username ="root";
            String password="";
            try {
                Class.forName("java.sql.Driver"); //étape 1: charger la classe de driver
                mycnx = DriverManager.getConnection(url, username, password); //étape 2: créer l'objet de connexion
                System.out.println("Connected with database successfully");

                return mycnx;

            }

            catch(SQLException e ){
                System.out.println(e);

            } catch (ClassNotFoundException ex){
                System.out.println(ex);
            }
            return null;

    }

        // deconnexion avec la base de donnes//
        public static void  Deconnexion() throws SQLException {
        try{
          mycnx.close();
          System.out.println("Deconnecte avec succes ! ");

        } catch( Exception e){
            System.out.println(e);
        }
        finally {
            mycnx.close();
        }
        }





    }

