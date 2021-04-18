package edu.brown.cs.student.stars.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDatabase {
  public static Connection conn = null;
  public static void connect() {
    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + "data/signup.sqlite3";
      conn = DriverManager.getConnection(urlToDB);
      Statement stat = conn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys=ON;");
    } catch (ClassNotFoundException e) {
      System.out.println("ERROR: Could not initialize MyDatabase");
    } catch (SQLException throwables) {
      System.out.println("ERROR: Could not do something with MyDatabase");
    }
  }
  public static Connection getConn() {
    return conn;
  }

}