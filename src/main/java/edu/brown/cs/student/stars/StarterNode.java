package edu.brown.cs.student.stars;

import java.time.LocalTime;

public class StarterNode implements GraphNode {

  private int id;
  private String username;
  private String event;
  private String startTime;
  private String endTime;
  private String location; // modify data type
  private int capacity;
  private int numAttendees;

  public StarterNode(int id, String event, String startTime,
                     String endTime, String location, int capacity, String username) {

    this.id = id;
    this.username = username;
    this.event = event;
    this.startTime = startTime;
    this.endTime = endTime;
    this.location = location;
    this.capacity = capacity;


    this.numAttendees = 0;

  }

  @Override
  public int getId() {
    return this.id;
  }

  public String getUsername() {return this.username;}

  @Override
  public String getEvent() {
    return this.event;
  }

  @Override
  public String getStartTime() {
    return this.startTime;
  }

  @Override
  public String getEndTime() {
    return this.endTime;
  }

  public String getLocation() {
    return this.location;
  }

  public int getCapacity() {
    return this.capacity;
  }

  public void incrementAttendees() {
    this.numAttendees ++;
  }

  public int getNumAttendees() {
    return this.numAttendees;
  }
}
