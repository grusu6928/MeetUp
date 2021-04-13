 package edu.brown.cs.student.stars;

 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.List;

 public class Events {
   Connection conn = MyDatabase.conn;
   //might convert location to longitude and latitude
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
               + "starter TEXT"
               + "numberOfPeople INTEGER,"
               + "PRIMARY KEY (number));");
       prep.executeUpdate();
       prep = conn.prepareStatement("INSERT INTO events VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)");
       prep.setString(1, eventType);
       prep.setString(2, activity);
       prep.setString(3, startTime);
       prep.setString(4, endTime);
       prep.setString(5, location);
       prep.setString(6, Login.user);
       prep.setString(7, Integer.toString(numberOfPeople));
     } catch(SQLException e) {
       System.out.println(e);
   }
   }
   public List<StarterNode> getAllEvents() {
     List<StarterNode> Events = new ArrayList<>();
     try {
       PreparedStatement prep;
       prep = conn.prepareStatement("SELECT * from events");
       ResultSet rs = prep.executeQuery();
       while(rs.next()) {
         Events.add(new StarterNode(rs.getString(2), rs.getString(3), rs.getString(4),
                 rs.getString(5), rs.getString(6), rs.getString(7),
                 rs.getInt(8)));
       }
     } catch (SQLException e) {
       System.out.println(e);
     }
     return Events;
   }
   public List<Event> getEventsOfType(String type) {
     List<Event> Events = new ArrayList<>();
     try {
       PreparedStatement prep;
       prep = conn.prepareStatement("SELECT * from events WHERE eventT= type");
       ResultSet rs = prep.executeQuery();
       while(rs.next()) {
         Events.add(new Event(rs.getString(2), rs.getString(3), rs.getString(4),
                 rs.getString(5), rs.getString(6), rs.getString(7),
                 rs.getInt(8)));
       }
     } catch (SQLException e) {
       System.out.println(e);
     }
     return Events;
   }
   //TODO: return events within time.
   //TODO: return events in radius r from current location.
 }
