package edu.brown.cs.student.stars;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class Logout implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    System.out.println("reached");
    request.session().removeAttribute("currentUser");
    response.redirect("/");
    return null;
  }
}
