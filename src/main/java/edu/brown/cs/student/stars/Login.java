package edu.brown.cs.student.stars;

import java.sql.*;

public class Login {
  public static String user;
  public void log(String username, String password) {
    Connection conn = DataBase.conn;
    try {
      PreparedStatement prep;
      prep = conn.prepareStatement("SELECT * from users WHERE username= ? AND password= ?");
      prep.setString(1, username);
      prep.setString(2, password);
      ResultSet rs = prep.executeQuery();
      if(rs.next()) {
        System.out.println("logged in as " + username);
        user = username;
      }
      else {
        System.out.println("Invalid username and password pair");
      }
    } catch(SQLException e) {
      System.out.println(e);
    }
  }
  public void logout() {
    user = null;
    System.out.println("logged out");
  }
}
