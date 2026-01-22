/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.libraryms.db;

import java.sql.Connection;
import java.sql.DriverManager;


public class DbConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    public Connection connect() throws Exception{
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
}
