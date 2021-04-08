package edu.brown.cs.student.stars;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

public class Home implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Map<String, String> variables = new HashMap<>();
    if(request.session().attribute("currentUser") == null) {
      return new ModelAndView(variables, "main.ftl");
    }
    else {
      System.out.println("Loggedin");
      variables.put("user", request.session().attribute("currentUser"));
      return new ModelAndView(variables, "login.ftl");
    }
  }
}
