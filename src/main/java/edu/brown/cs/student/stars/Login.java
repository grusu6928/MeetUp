package edu.brown.cs.student.stars;

import spark.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Login {
  public static Boolean log(String username, String password) {
    Boolean loggedIn = false;
    Connection conn = MyDatabase.conn;
    System.out.println(username);
    try {
      PreparedStatement prep;
      prep = conn.prepareStatement("SELECT * from users WHERE (username= ? AND password= ?)");
      prep.setString(1, username);
      prep.setString(2, password);
      ResultSet rs = prep.executeQuery();
      if(rs.next()) {
        System.out.println("logged in as " + username);
        loggedIn = true;
      }
      else {
        System.out.println("Invalid username and password pair");
      }
    } catch(SQLException e) {
      System.out.println(e);
    }
    return loggedIn;
  }
  public void logout() {
    System.out.println("logged out");
  }
  // @Override
  // public ModelAndView handle(Request request, Response response) throws Exception {
  //   System.out.println("Hi");
  //   QueryParamsMap form = request.queryMap();
  //   String username = form.value("username");
  //   String password = form.value("password");
  //   username = log(username, password);
  //   if(username != null) {
  //     response.redirect("/");
  //     return null;
  //   }
  //   request.session().attribute("currentUser", username);
  //   Map<String, String> variables = new HashMap<>();
  //   return new ModelAndView(variables, "main.ftl");
  // }
}
