package edu.brown.cs.student.stars;
import edu.brown.cs.student.stars.MyDatabase;
import org.eclipse.jetty.util.Loader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;

public class SignUp {
  //copied bytesToHex: https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
  private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
  public static String bytesToHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = HEX_ARRAY[v >>> 4];
      hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
    }
    return new String(hexChars);
  }
  //resources for hashing:
  //subscription.packtpub.com/book/application_development/9781849697767/1/
  // ch01lvl1sec10/adding-salt-to-a-hash-intermediate
  //https://www.baeldung.com/java-password-hashing
  public void newUser(String username, String password, String email) {
    Connection conn = MyDatabase.conn;
    try {
      SecureRandom random = new SecureRandom();
      byte[] salt = new byte[16];
      random.nextBytes(salt);
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      md.update(salt);
      byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
      String passwordString = bytesToHex(hashedPassword);
      String saltString = bytesToHex(salt);
      PreparedStatement prep;
      prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS users("
              + "number INTEGER,"
              + "username TEXT,"
              + "password TEXT,"
              + "email TEXT,"
              + "salt TEXT,"
              + "PRIMARY KEY (number));");
      prep.executeUpdate();
      System.out.println("reached here");
      prep = conn.prepareStatement("SELECT * FROM users WHERE username= ? OR email= ?" );
      prep.setString(1, username);
      prep.setString(2, email);
      ResultSet rs = prep.executeQuery();
      if(rs.next()) {
        System.out.println("ERROR: account with this username already exists");
      }
      else {
        prep = conn.prepareStatement("INSERT INTO users VALUES(NULL, ?, ?, ?, ?);");
        prep.setString(1, username);
        prep.setString(2, passwordString);
        prep.setString(3,email);
        prep.setString(4, saltString);
        prep.executeUpdate();
        System.out.println("created account successfully");
      }
    }  catch(SQLException | NoSuchAlgorithmException e) {
      System.out.println(e);
    }
  }
}
