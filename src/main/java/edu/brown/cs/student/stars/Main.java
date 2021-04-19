package edu.brown.cs.student.stars;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import edu.brown.cs.student.stars.Database.*;
import edu.brown.cs.student.stars.Graph.Graph;
import edu.brown.cs.student.stars.Graph.Looker;
import edu.brown.cs.student.stars.Graph.Starter;
import freemarker.template.Configuration;
import org.json.JSONArray;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import spark.Route;
// new imports

import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
  public static final int LOOKERS_THRESHOLD = 6;
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
    Spark.post("/endtime", new endTimeHandler());
    Spark.post("/rsvp", new rsvpQueryHandler());
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
      JSONObject data = new JSONObject(request.body());
      SignUp s = new SignUp();
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


      double latitude = location.getDouble(0);
      double longitude = location.getDouble(1);

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
      String currUser = data.getString("userID");
      String newFriend = data.getString("userToAdd");
      String userToRemove = data.getString("userToremove");
      Friends friendObj = Friends.getInstance();
      switch (requestType) {
        case "query":
        return GSON.toJson(friendObj.getFriendsList(currUser));
        case "insert":
        if (friendObj.sent(currUser, newFriend)) {
          return GSON.toJson("already sent a pending friend request"); 
        } else if (friendObj.checkFriendShip(currUser, newFriend)) {
          return GSON.toJson("already friends with this user!"); 
        } else {
            friendObj.sendRequest(currUser, newFriend);
            return GSON.toJson("success");
        }
        case "kill":
        if (friendObj.checkFriendShip(currUser, userToRemove)) {
        friendObj.deleteFriend(currUser, userToRemove);
        return GSON.toJson("successfullu deleted user");
        } else {
          return GSON.toJson("can't delete user you are not friends with");
        }
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

      System.out.println("IN LOOKER HANDLER");


      JSONObject data = new JSONObject(request.body());
      String username = data.getString("user");
      String activity = data.getString("activity");
      String startTime = data.getString("startTime");
      String endTime = data.getString("endTime");
      JSONArray location = data.getJSONArray("location");

      double latitude = location.getDouble(0);
      double longitude = location.getDouble(1);

      System.out.println("RIGHT BEFORE ADDING LOOKER");
      System.out.println(username);
      System.out.println(activity);
      System.out.println(startTime);
      System.out.println(endTime);
      System.out.println(latitude);
      System.out.println(longitude);
      Events.getInstance().addLooker(username, activity, startTime, endTime, latitude, longitude);

      System.out.println("AFTER ADDING LOOKSER");

      return GSON.toJson("success");
    }
  }
  private static class endTimeHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      JSONObject data = new JSONObject(request.body());
      System.out.println("data " + data);
      String requestType = data.getString("requestType");
      System.out.println("requestType " + requestType);
      String username = data.getString("user");
      System.out.println("username " + username);
      String endTime = data.getString("endTime");
      System.out.println("endTime " + endTime);
      if(requestType.equals("get")) {
        System.out.println("inside");

        System.out.println("RETURN: " + Events.getInstance().getEndTime(username));

        return GSON.toJson(Events.getInstance().getEndTime(username));
      }
      if(requestType.equals("set")) {
        Events.getInstance().setEndTime(endTime, username);
        return GSON.toJson("sucess");
      }
      return GSON.toJson("Invalid request");
    }
  }
  private static class rsvpQueryHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      JSONObject data = new JSONObject(request.body());
      System.out.println("data " + data);
      String requestType = data.getString("requestType");
      System.out.println("requestType " + requestType);
      String username = data.getString("user");
      System.out.println("username " + username);
      String endTime = data.getString("endTime");
      System.out.println("endTime " + endTime);
      if(requestType.equals("get")) {
        System.out.println("inside");

        System.out.println("RETURN: " + Events.getInstance().getEndTime(username));

        return GSON.toJson(Events.getInstance().getEndTime(username));
      }
      if(requestType.equals("set")) {
        Events.getInstance().setEndTime(endTime, username);
        return GSON.toJson("sucess");
      }
      return GSON.toJson("Invalid request");
    }
  }

  private static class attendeesHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
      // request should be name of starter
      JSONObject data = new JSONObject(request.body());
      String starter = data.getString("user");

      Events database = Events.getInstance();

      System.out.println("BEFORE CHECKING THRESHOLD");
      if (database.getNumEvents() >= STARTERS_THRESHOLD &&
              database.getNumLookers() >= LOOKERS_THRESHOLD) {
      List<Starter> events = database.getAllEvents();
      List<Looker> lookers = database.getAllLookers();

      System.out.println("BEFORE Creating graph");
      Graph graph = new Graph(lookers, events);
      System.out.println("before algoh");
      Map<Starter, List<Looker>> result = graph.runAlgorithm();
      System.out.println("before adding to RSVP");
      result.forEach((k,v) -> {
        for(Looker l : v) {
          database.addMatch(l.getUsername(), k.getUsername());
        }
      });

      System.out.println("before returning");
//        database.clearTables();

        System.out.println("MATCHES" + database.getMatches(starter));

        return GSON.toJson(database.getMatches(starter));
      } else {
        return GSON.toJson(new ArrayList<>());
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