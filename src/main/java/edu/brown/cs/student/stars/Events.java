 package edu.brown.cs.student.stars;

 import java.sql.*;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;

 // NOTE: CHANGED TO SINGLETON CLASS
 public final class Events {

     private static Events events = null;
     private Connection conn;

     Events() {
       conn = MyDatabase.conn;
     }


     public static Events getInstance() {
       if (events == null) {
         events = new Events();
       }
       return events;
     }

   //might convert location to longitude and latitude


   public void addMatch(String username, int eventId) {
     try {
       PreparedStatement prep;
       prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS RSVP("
               + "number INTEGER,"
               + "username TEXT UNIQUE," // CHANGED: added UNIQUE
               + "eventId INTEGER,"
               + "response TEXT,"
               + "FOREIGN KEY (username) REFERENCES lookers(username)"
               + "ON DELETE CASCADE ON UPDATE CASCADE,"
               + "FOREIGN KEY (eventId) REFERENCES events(number)"
               + "ON DELETE CASCADE ON UPDATE CASCADE,"
               + "PRIMARY KEY (number));");
       prep.executeUpdate();
       prep = conn.prepareStatement("INSERT INTO RSVP VALUES(NULL, ?, ?, ?);");
       prep.setString(1, username);
       prep.setInt(2, eventId);
       prep.setString(3, "No response");
       prep.executeUpdate();
     } catch(SQLException e) {
       System.out.println(e);
     }

   }
   public void addLooker(String eventType, String activityType, String startTime, String endTime, String loc) {
     try {
       PreparedStatement prep;
       prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS lookers("
               + "number INTEGER,"
               + "eventT TEXT,"
               + "activityType TEXT,"
               + "startT TEXT,"
               + "endT TEXT,"
               + "loc TEXT,"
               + "username TEXT UNIQUE," // CHANGED: added UNIQUE
               + "FOREIGN KEY (username) REFERENCES users(username)"
               + "ON DELETE CASCADE ON UPDATE CASCADE,"
               + "PRIMARY KEY (number));");
       prep.executeUpdate();
       prep = conn.prepareStatement("INSERT INTO lookers VALUES(NULL, ?, ?, ?, ?, ?, ?);");
       prep.setString(1, eventType);
       prep.setString(2, activityType);
       prep.setString(3, startTime);
       prep.setString(4, endTime);
       prep.setString(5, loc);
       prep.setString(6, "a@brown.edu");
       prep.executeUpdate();
     } catch(SQLException e) {
       System.out.println(e);
     }
   }
   public List<LookerNode> getAllLookers() {
     List<LookerNode> lookers = new ArrayList<>();
     try {
       PreparedStatement prep;
       prep = conn.prepareStatement("SELECT * from lookers");
       ResultSet rs = prep.executeQuery();
       while(rs.next()) {
         lookers.add(new LookerNode(rs.getInt(1),rs.getString(3), rs.getString(4), rs.getString(5), ""));
       }
     } catch (SQLException e) {
       System.out.println(e);
     }
     return lookers;
   }

   public void createEvent(String eventType, String activity, String startTime, String endTime, String location,
                           int numberOfPeople) {
     try {
       PreparedStatement prep;
       prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS events("
               + "number INTEGER,"
               + "eventT TEXT,"
               + "activityType TEXT,"
               + "startT TEXT,"
               + "endT TEXT,"
               + "loc TEXT,"
               + "starter TEXT,"
               + "numberOfPeople INTEGER,"
               + "PRIMARY KEY (number));");
       prep.executeUpdate();
       prep = conn.prepareStatement("INSERT INTO events VALUES(NULL, ?, ?, ?, ?, ?, ?, ?);");
       prep.setString(1, eventType);
       prep.setString(2, activity);
       prep.setString(3, startTime);
       prep.setString(4, endTime);
       prep.setString(5, location);
       prep.setString(6, "");
       prep.setString(7, Integer.toString(numberOfPeople));
       prep.executeUpdate();
     } catch(SQLException e) {
       System.out.println(e);
   }
   }
//   private int id;
//   private String event;
//   private String startTime;
//   private String endTime;
//
//   private String location; // modify data type
//   private int capacity;

   public List<StarterNode> getAllEvents() {
     List<StarterNode> Events = new ArrayList<>();
     try {
       PreparedStatement prep;
       prep = conn.prepareStatement("SELECT * from events");
       ResultSet rs = prep.executeQuery();
       while(rs.next()) {
         //TODO: add usernames after sessions are ready
         int id = rs.getInt(1);
         String username = rs.getString(7);
         String event = rs.getString(3);
         String startTime = rs.getString(4);
         String endTime = rs.getString(5);
         String location = rs.getString(6);
         int capacity = rs.getInt(8);

         StarterNode starter = new StarterNode(id, username, event, startTime, endTime, location, capacity);
         Events.add(starter);
       }
     } catch (SQLException e) {
       System.out.println(e);
     }
     return Events;
   }
//   public List<Event> getEventsOfType(String type) {
//     List<Event> Events = new ArrayList<>();
//     try {
//       PreparedStatement prep;
//       prep = conn.prepareStatement("SELECT * from events WHERE eventT= type");
//       ResultSet rs = prep.executeQuery();
//       while(rs.next()) {
//         Events.add(new Event(rs.getString(2), rs.getString(3), rs.getString(4),
//                 rs.getString(5), rs.getString(6), rs.getString(7),
//                 rs.getInt(8)));
//       }
//     } catch (SQLException e) {
//       System.out.println(e);
//     }
//     return Events;
//   }
   //TODO: return events within time.
   //TODO: return events in radius r from current location.
 }

