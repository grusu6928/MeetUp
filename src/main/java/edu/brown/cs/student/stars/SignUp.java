package edu.brown.cs.student.stars;
import edu.brown.cs.student.stars.MyDatabase;

import org.eclipse.jetty.util.Loader;

import java.sql.*;

public class SignUp {
  public void newUser(String username, String password, String email) {
    Connection conn = MyDatabase.conn;
    try {
      PreparedStatement prep;
      prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS users("
              + "number INTEGER,"
              + "username TEXT,"
              + "password TEXT,"
              + "email TEXT,"
              + "PRIMARY KEY (number));");
      prep.executeUpdate();
      prep = conn.prepareStatement("SELECT * FROM users WHERE username= ? OR email= ?" );
      prep.setString(1, username);
      prep.setString(2, email);
      ResultSet rs = prep.executeQuery();
      if(rs.next()) {
        System.out.println("ERROR: account with this username already exists");
      }
      else {
        prep = conn.prepareStatement("INSERT INTO users VALUES(NULL, ?, ?, ?);");
        prep.setString(1, username);
        prep.setString(2, password);
        prep.setString(3,email);
        prep.executeUpdate();
        System.out.println("created account successfully");
      }
    }  catch(SQLException e) {
      System.out.println(e);
    }
  }
}
