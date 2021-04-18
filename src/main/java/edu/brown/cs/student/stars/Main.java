package edu.brown.cs.student.stars;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import edu.brown.cs.student.stars.Login;
import freemarker.template.Configuration;
import jdk.jfr.Event;
import org.json.JSONArray;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;
// new imports
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.common.collect.ImmutableMap;
import freemarker.template.Configuration;
import com.google.gson.Gson;
import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;
import freemarker.template.Configuration;
import org.json.JSONObject;
import com.google.gson.Gson;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * The Main class of our project. This is where execution begins.
 *
 */
public final class Main {

  private static final int DEFAULT_PORT = 4567;
  private static final Gson GSON = new Gson();

  public static final String USERID = "USERID";

  // every 1 minute -> check if these values are hit
      // if yes -> run the algorithm
  public static final int LOOKERS_THRESHOLD = 25;
  public static final int STARTERS_THRESHOLD = 3;


  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    runSparkServer(DEFAULT_PORT);
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n", templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  /**
   ** IMPLEMENT METHOD runSparkServer() HERE
   */
  private void runSparkServer(int port) {
    // TODO
    MyDatabase.connect();



    // TODO: Send updates from RSVP table to front-end.
    // TODO: When to clear the table. (maybe after each event finishes, delete all related data)

    Spark.port(port);
    // Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.options("/*", (request, response) -> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }

      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");

      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }

      return "OK";
});

Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
 
    Spark.exception(Exception.class, new ExceptionPrinter());
    FreeMarkerEngine freeMarker = createEngine();
    // Spark.get("/", new Home(), freeMarker);
    Spark.post("/login", new loginAuthHandler());
    Spark.post("/events", new eventsHandler());
    Spark.post("/looker", new lookerHandler());
    Spark.post("/attendees", new attendeesHandler());
    Spark.post("/friends", new friendsHandler());
    Spark.post("/signup", new signupHandler());
    Spark.post("/logout", new Logout(), freeMarker);
  }
  private static class loginAuthHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      JSONObject data = new JSONObject(request.body());
      return new Gson().toJson(Login.log(data.getString("email"), data.getString("pass")));
      // Map<String, Object> variables = ImmutableMap.of("checkin", isAuth);
      // return GSON.toJson(isAuth);
    }
  }
  private static class signupHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      System.out.println("a");
      JSONObject data = new JSONObject(request.body());
      System.out.println("b");
      SignUp s = new SignUp();
      System.out.println("c");
      System.out.println(data);
      s.newUser(data.getString("email"),data.getString("pass"),data.getString("email"));
      System.out.println(Login.log(data.getString("email"), data.getString("pass")));
      return new Gson().toJson(Login.log(data.getString("email"), data.getString("pass")));
      // Map<String, Object> variables = ImmutableMap.of("checkin", isAuth);
      // return GSON.toJson(isAuth);
    }
  }
  private static class eventsHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      JSONObject data = new JSONObject(request.body());
      String username = data.getString("user");
      String activity = data.getString("activity");
      String startTime = data.getString("startTime");
      String endTime = data.getString("endTime");
      JSONArray location = data.getJSONArray("location");

      double latitude = location.getJSONObject(0).getDouble("lat");
      double longitude = location.getJSONObject(1).getDouble("lng");

      int capacity = Integer.parseInt(data.getString("capacity"));

      Events.getInstance().createEvent(username, activity, startTime, endTime,
              latitude, longitude, capacity);
      return GSON.toJson("success");
    }
  }
  private static class friendsHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      JSONObject data = new JSONObject(request.body());
      System.out.println(data);
      String requestType = data.getString("requestType");
      String newFriend = data.getString("userToAdd");
      String userToRemove = data.getString("userToremove");
      switch (requestType) {
        case "query":
        case "insert":
        case "kill":
        // TODO: call each of teh necessary functions for friends  - return friends list

      }
      Events eventDB = Events.getInstance();
      //CHANGE: HardCoded type of event - firs tparameter. 
//      eventDB.createEvent("public", activity, startTime, endTime, location, numAttendees);
      return GSON.toJson("success");
    }
  }
  private static class lookerHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      JSONObject data = new JSONObject(request.body());
      String username = data.getString("user");
      String activity = data.getString("activity");
      String startTime = data.getString("startTime");
      String endTime = data.getString("endTime");
      JSONArray location = data.getJSONArray("location");

      double latitude = location.getJSONObject(0).getDouble("lat");
      double longitude = location.getJSONObject(1).getDouble("lng");

      Events.getInstance().addLooker(username, activity, startTime, endTime, latitude, longitude);
      return GSON.toJson("success");
    }
  }
  private static class attendeesHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      // request should be name of starter
      JSONObject data = new JSONObject(request.body());
      String starter = data.getString("user");
      System.out.println("starter user " + starter);

      Events database = Events.getInstance();

      if (database.getNumEvents() >= STARTERS_THRESHOLD &&
              database.getNumLookers() >= LOOKERS_THRESHOLD) {

      List<StarterNode> events = database.getAllEvents();
      List<LookerNode> lookers = database.getAllLookers();

      Graph graph = new Graph(lookers, events);
      Map<StarterNode, List<LookerNode>> result = graph.runAlgorithm();
      result.forEach((k,v) -> {
        for(LookerNode l : v) {
          database.addMatch(l.getUsername(), k.getUsername());
        }
      });

      // TODO: clear database tables
        database.clearTables();

      return GSON.toJson(database.getMatches(starter));
      } else {
        return GSON.toJson("We are working on matching you with other users!");
      }
    }
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
}