package com.example.javaproject;


import com.example.javaproject.ConnexDB;
import com.example.javaproject.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {


    Statement st;
     public void LoginControl(Users u) throws SQLException {
        try {
            if(u.isAdmin()){
                System.out.println("L admin existe dans la DB");
                System.out.println("Logged In ! ");
            }
            else{
                System.out.println("Invalid Login! Please try again");
            }
        } catch (Exception e) {
             throw new RuntimeException(e);
        }

     }

     public boolean isLogin(Users u) throws SQLException
     {
         st = ConnexDB.GetConnexion().createStatement();
         System.out.println(st);
         ResultSet res= st.executeQuery("select * from users where username ='" +u.getUsername()+ "' and password ='" +u.getPassword()+"'");
         if(res.next())
             return true;
         return false;
     }






}