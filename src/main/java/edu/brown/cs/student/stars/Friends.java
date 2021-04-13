// package edu.brown.cs.student.stars;

// import org.eclipse.jetty.util.log.Log;

// import java.nio.channels.SelectableChannel;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

// public class Friends {
//   Connection conn = MyDatabase.conn;
//   public Boolean sent(String username) {
//     try {
//       PreparedStatement prep;
//       prep = conn.prepareStatement("SELECT * from friendRequests WHERE (sender = ? AND receiver = ?) " +
//               "OR (sender = ? AND receiver = ?)");
//       prep.setString(1, username);
//       prep.setString(2, Login.user);
//       prep.setString(3, Login.user);
//       prep.setString(4, username);
//       ResultSet rs = prep.executeQuery();
//       if(rs.next()) {
//         return true;
//       }
//       return false;
//     } catch(SQLException e) {
//       System.out.println(e);
//       return false;
//     }
//   }
//   public void sendRequest(String username) {
//     try {
//       PreparedStatement prep;
//       prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS friendRequests("
//               + "number INTEGER,"
//               + "sender TEXT,"
//               + "receiver TEXT,"
//               + "PRIMARY KEY (number));");
//       prep.executeUpdate();
//       if(!sent(username) && !(checkFriendShip(Login.user, username))) {
//         prep = conn.prepareStatement("INSERT INTO friendRequests VALUES(NULL, ?, ?)");
//         prep.setString(1, Login.user);
//         prep.setString(2, username);
//         System.out.println("friend request sent");
//       }
//       else {
//         System.out.println("there is already a friend request/friendship between you");
//       }
//     } catch(SQLException e) {
//       System.out.println(e);
//     }
//   }
//   public void deleteFriend(String username) {
//     try {
//       PreparedStatement prep;
//       prep = conn.prepareStatement("SELECT * from friends WHERE (username1= ? AND username2= ?) " +
//               "OR (username1= ? AND username2= ?)");
//       prep.setString(1, Login.user);
//       prep.setString(2, username);
//       prep.setString(3, username);
//       prep.setString(4, Login.user);
//       ResultSet rs = prep.executeQuery();
//       if(rs.next()) {
//         prep = conn.prepareStatement("DELETE from friends WHERE (username1= ? AND username2= ?) " +
//                 "OR (username1= ? AND username2 = ?)");
//         prep.setString(1, Login.user);
//         prep.setString(2, username);
//         prep.setString(3, username);
//         prep.setString(4, Login.user);
//         prep.executeUpdate();
//         System.out.println("friend deleted");
//       }
//       else {
//         System.out.println("You are not friends with this person");
//       }
//     } catch(SQLException e) {
//       System.out.println(e);
//     }
//   }
//   public void declineFriendRequest(String username) {
//     try {
//       PreparedStatement prep;
//       prep = conn.prepareStatement("SELECT * from friendsRequests where sender = ? AND receiver= ?");
//       prep.setString(1, username);
//       prep.setString(2, Login.user);
//       ResultSet rs = prep.executeQuery();
//       if(rs.next()) {
//         prep = conn.prepareStatement("DELETE from friendRequests where sender= ? AND receiver= ?");
//         prep.executeUpdate();
//         System.out.println("Friend request deleted");
//       } else {
//         System.out.println("The user haven't sent you a friend request");
//       }
//     } catch(SQLException e) {
//       System.out.println(e);
//     }
//   }
//   public void acceptFriend(String username) {
//     try {
//       PreparedStatement prep;
//       prep = conn.prepareStatement("SELECT * from friendRequest WHERE sender= ? AND receiver= ?");
//       prep.setString(1, username);
//       prep.setString(2, Login.user);
//       ResultSet rs = prep.executeQuery();
//       if(rs.next()) {
//         prep = conn.prepareStatement("DELETE from friendRequest where sender= ? AND receiver= ?");
//         prep.setString(1, username);
//         prep.setString(2, Login.user);
//         prep.executeUpdate();
//         prep = conn.prepareStatement("INSERT INTO friends values(NULL, ?, ?)");
//         prep.setString(1, username);
//         prep.setString(2, Login.user);
//         prep.executeUpdate();
//       }
//       else{
//         System.out.println("the user haven't sent you a friend request");
//       }
//     } catch (SQLException e) {
//       System.out.println(e);
//     }
//   }
//   public List<String> currentFriendRequests() {
//     List<String> friendRequests = new ArrayList<>();
//     try {
//       PreparedStatement prep;
//       prep = conn.prepareStatement("SELECT * from friendRequests where receiver= ?");
//       prep.setString(1, Login.user);
//       ResultSet rs = prep.executeQuery();
//       while(rs.next()) {
//         friendRequests.add(rs.getString(2));
//       }
//     }
//     catch(SQLException e) {
//       System.out.println(e);
//     }
//     return friendRequests;
//   }
//   public Boolean checkFriendShip(String u1, String u2) {
//     try {
//       PreparedStatement prep;
//       prep = conn.prepareStatement("SELECT * from friends where (username1= ? AND username2= ?) " +
//               "OR (username1= ? AND username2= ?)");
//       prep.setString(1, u1);
//       prep.setString(2, u2);
//       prep.setString(3, u2);
//       prep.setString(4, u1);
//       ResultSet rs = prep.executeQuery();
//       if(rs.next()) {
//         return true;
//       }
//       return false;
//     } catch(SQLException e){
//       System.out.println(e);
//       return false;
//     }
//   }

//   public List<String> getFriendsList() {
//     List<String> friends = new ArrayList<>();
//     try {
//       PreparedStatement prep;
//       prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS friendRequests("
//               + "number INTEGER,"
//               + "username1 TEXT,"
//               + "username2 TEXT,"
//               + "PRIMARY KEY (number));");
//       prep.executeUpdate();
//       prep = conn.prepareStatement("SELECT * from friends where username1 = ? OR username2 = ?;");
//       prep.setString(1, Login.user);
//       prep.setString(2, Login.user);
//       ResultSet rs = prep.executeQuery();
//       while(rs.next()) {
//         String username1 = rs.getString(2);
//         String username2 = rs.getString(3);
//         if(!username1.equals(Login.user)) {
//           friends.add(username1);
//         }
//         else if(!username2.equals(Login.user)){
//           friends.add(username2);
//         }
//       }
//       return friends;
//     } catch(SQLException e) {
//       System.out.println(e);
//       return friends;
//     }
//   }
// }
