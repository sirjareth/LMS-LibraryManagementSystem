package com.joysistvi.libraryms.library;

import com.joysistvi.libraryms.db.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

// is-a relationship
// has-a relationship
public class Book {

    // Composition
    private final DbConnection dbConnection;

    // constructor injection
    public Book(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // CRUD
    // Read
    public void readAllBooks() {
        String query = "SELECT * FROM tblbooks WHERE isArchived = 0";

        // try-with resources
        try (Connection connection = dbConnection.connect(); // connect with the db
                Statement statement = connection.createStatement(); // create sql stmnt
                ResultSet result = statement.executeQuery(query); // execute query / sql stmnt
                ) {

            System.out.println("ID\tTITLE\t\t\t\tAUTH ID PUB ID\tPAGES\tEDITION");
            System.out.println("------------------------------------------------------------------------");
            while (result.next()) {
                int bookId = result.getInt("book_id");
                String title = result.getString("title");
                int authId = result.getInt("author_id");
                int pubId = result.getInt("pub_id");
                int pages = result.getInt("pages");
                String edition = result.getString("edition");

                System.out.println(bookId + "\t" + title + "\t\t" + authId + "\t" + pubId + "\t" + pages + "\t" + edition);
            }

        } catch (Exception e) {
            System.out.println("Read Books: " + e.getMessage());
        }

    }
    
    public void readAllArchivedBooks(){
        String query = "SELECT * FROM tblbooks WHERE isArchived = 1";

        // try-with resources
        try (Connection connection = dbConnection.connect(); // connect with the db
                Statement statement = connection.createStatement(); // create sql stmnt
                ResultSet result = statement.executeQuery(query); // execute query / sql stmnt
                ) {

            System.out.println("ID\tTITLE\t\t\t\tAUTH ID PUB ID\tPAGES\tEDITION");
            System.out.println("------------------------------------------------------------------------");
            while (result.next()) {
                int bookId = result.getInt("book_id");
                String title = result.getString("title");
                int authId = result.getInt("author_id");
                int pubId = result.getInt("pub_id");
                int pages = result.getInt("pages");
                String edition = result.getString("edition");

                System.out.println(bookId + "\t" + title + "\t\t" + authId + "\t" + pubId + "\t" + pages + "\t" + edition);
            }

        } catch (Exception e) {
            System.out.println("Read Books: " + e.getMessage());
        }
    }

    public void readAllBooksWithAthorAndPublisher() {
        String query = "SELECT book_id, title, author_name, publisher_name, pages, edition "
                + "FROM tblbooks "
                + "INNER JOIN tblauthors "
                + "ON tblbooks.author_id = tblauthors.author_id	"
                + "INNER JOIN tblpublishers "
                + "ON tblbooks.pub_id = tblpublishers.pub_id "
                + "WHERE isArchived = 0";

        // try-with resources
        try (Connection connection = dbConnection.connect(); // connect with the db
                Statement statement = connection.createStatement(); // create sql stmnt
                ResultSet result = statement.executeQuery(query); // execute query / sql stmnt
                ) {

            System.out.println("ID\tTITLE\t\t\t\tAUTHOR\t\t\tPUBLISHER\t\t\tPAGES\tEDITION"); // header
            while (result.next()) {
                int bookId = result.getInt("book_id");
                String title = result.getString("title");
                String authorName = result.getString("author_name");
                String pubName = result.getString("publisher_name");
                int pages = result.getInt("pages");
                String edition = result.getString("edition");

                System.out.println(bookId + "\t" + title + "\t\t" + authorName + "\t\t" + pubName + "\t\t" + pages + "\t" + edition);
            }

        } catch (Exception e) {
            System.out.println("Read Books: " + e.getMessage());
        }

    }

    public void createBook(String title, int authorId, int pubId, int pages, String edition) {
        String query = "INSERT INTO tblbooks (title, author_id, pub_id, pages, edition) "
                + "VALUES (?,?,?,?,?)"; // ANTI-SQL INJECTION / parametherized query
        // wild cards
        try (Connection connect = dbConnection.connect();
                PreparedStatement prep = connect.prepareStatement(query)) {

            // setting parameter wild cards
            prep.setString(1, title);
            prep.setInt(2, authorId);
            prep.setInt(3, pubId);
            prep.setInt(4, pages);
            prep.setString(5, edition);
            prep.executeUpdate();

            System.out.println("Book " + title + " added successfully!\n");
            readAllBooksWithAthorAndPublisher();

        } catch (Exception e) {
            System.out.println("Create Book: " + e.getMessage());
        }
    }

    public void updateBook(String title, int bookId) {
        String query = "UPDATE tblbooks SET title = ? WHERE book_id = ?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement prep = connect.prepareStatement(query);) {

            prep.setString(1, title);
            prep.setInt(2, bookId);
            prep.executeUpdate();

            System.out.println("Book " + title + " updated successfully!\n");
            readAllBooksWithAthorAndPublisher();
        } catch (Exception e) {
            System.out.println("Update Book: " + e.getMessage());
        }
    }

    public void deleteBook(int bookId) {
        String query = "DELETE FROM tblbooks WHERE book_id = ?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement prep = connect.prepareStatement(query);) {
            prep.setInt(1, bookId);
            prep.executeUpdate();
            System.out.println("Book " + bookId + " deleted successfully.");
            readAllBooksWithAthorAndPublisher();
        } catch (Exception e) {
            System.out.println("Delete Book Error: " + e.getMessage());
        }
    }

    public void archiveBook(int bookId) {
        String query = "UPDATE tblbooks SET isArchived = 1 WHERE book_id = ?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement prep = connect.prepareStatement(query);) {
            prep.setInt(1, bookId);
            prep.executeUpdate();
            System.out.println("Book " + bookId + " archived successfully.");
            readAllBooksWithAthorAndPublisher();
        } catch (Exception e) {
            System.out.println("Archive Book Error: " + e.getMessage());
        }
    }
    
    public void restoreBook(int bookId){
        String query = "UPDATE tblbooks SET isArchived = 0 WHERE book_id = ?";

        try (Connection connect = dbConnection.connect();
                PreparedStatement prep = connect.prepareStatement(query);) {
            prep.setInt(1, bookId);
            prep.executeUpdate();
            System.out.println("Book " + bookId + " restored successfully.");
            readAllBooksWithAthorAndPublisher();
        } catch (Exception e) {
            System.out.println("Archive Book Error: " + e.getMessage());
        }
    }
    
    
    

    public void dashBoard() {
        System.out.println("    [1] View All Books");
        System.out.println("    [2] Create Book");
        System.out.println("    [3] Update Book");
        System.out.println("    [4] Delete Book");
        System.out.println("    [5] Archive Book");

//        switch(){
//            
//        }
    }

}

/*
    Categories of SQL Statements

    DML Data Manipulation Language: CREATE, UPDATE, DELETE 
        Submit Query: prepareStatement() -> executeUpdate();
    DQL Data Query Language: READ
        Submit Query: createStatement() -> executeQuery()
 */
