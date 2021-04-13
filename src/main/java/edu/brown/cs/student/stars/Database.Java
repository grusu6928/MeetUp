package edu.brown.cs.student.stars;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
  public static Connection conn = null;
  public static void connect() {
    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + "data/signup.sqlite3";
      conn = DriverManager.getConnection(urlToDB);
      Statement stat = conn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys=ON;");
    } catch (ClassNotFoundException e) {
      System.out.println("ERROR: Could not initialize database");
    } catch (SQLException throwables) {
      System.out.println("ERROR: Could not initialize database");
    }
  }
  public static Connection getConn() {
    return conn;
  }

}