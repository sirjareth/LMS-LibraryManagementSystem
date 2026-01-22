/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.libraryms.library;



import com.joysistvi.libraryms.db.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;


public class User {
    
    private final DbConnection dbConnection;

    public User(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    
    public void registerUser(String userFName, String email, String username, String password){
        String query = "INSERT INTO tblusers(user_fname, email, username, password) "
                + "VALUES (?,?,?,?)";
        
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        
        try (Connection connection = dbConnection.connect(); // connect with the db
                PreparedStatement prep = connection.prepareStatement(query)){

            prep.setString(1, userFName);
            prep.setString(2, email);
            prep.setString(3, username);
            prep.setString(4, hashedPassword);
            
            prep.executeUpdate();
            System.out.println("User " + userFName + " added.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void loginUser(String username, String password){
        String query = "SELECT username, password FROM tblusers "
                + "WHERE username = ? AND password = ? AND isArchived = 0";
        
        try (Connection connection = dbConnection.connect(); // connect with the db
                PreparedStatement prep = connection.prepareStatement(query)){
            
            
            prep.setString(1, username);
            prep.setString(2, password);
            
            try (ResultSet result = prep.executeQuery();){
                if (result.next()) {
                    System.out.println("Logged In");
                } else {
                    System.out.println("Login Failed");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            
            
            
            
        } catch (Exception e) {
        }
        
    }
}
