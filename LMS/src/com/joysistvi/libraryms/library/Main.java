/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.libraryms.library;

import com.joysistvi.libraryms.db.DbConnection;
import java.util.Scanner;


public class Main {
    
    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();
        Book book = new Book(dbConnection);
        Scanner scanner = new Scanner(System.in);
        
//        System.out.println("* Restore Book *");
//        System.out.print("Enter Book ID that you want to restore: ");
//        int bookId = scanner.nextInt();
        User user = new User(dbConnection);
        System.out.println("Enter first name: ");
        String fname = scanner.nextLine();
        System.out.println("Enter email address: ");
        String email = scanner.nextLine();
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        user.registerUser(fname, email, username, password);
        
    }
}
