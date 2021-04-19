package edu.brown.cs.student.stars.Database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Arrays;

public class Login {
  //copied from here:
  // https://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java
  public static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
              + Character.digit(s.charAt(i+1), 16));
    }
    return data;
  }
  public static Boolean log(String username, String password) {
    Boolean loggedIn = false;
    Connection conn = MyDatabase.conn;
    System.out.println(username);
    try {
      PreparedStatement prep;
      prep = conn.prepareStatement("SELECT * from users WHERE (username= ?)");
      prep.setString(1, username);
      ResultSet rs = prep.executeQuery();
      if(rs.next()) {
        String hashedPassword = rs.getString(3);
        String salt = rs.getString(5);
        byte[] byteSalt = hexStringToByteArray(salt);
        byte[] byteStoredPassword = hexStringToByteArray(hashedPassword);
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(byteSalt);
        byte[] attemptPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        if(Arrays.equals(attemptPassword, byteStoredPassword)) {
          System.out.println("logged in as " + username);
          return true;
        }
      }
    } catch(SQLException | NoSuchAlgorithmException e) {
      System.out.println(e);
    }
    System.out.println("Invalid username and password pair");
    return false;
  }
  public void logout() {
    System.out.println("logged out");
  }
}
