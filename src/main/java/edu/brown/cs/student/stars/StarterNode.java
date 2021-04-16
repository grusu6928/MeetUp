package edu.brown.cs.student.stars;


public class StarterNode implements GraphNode {

  private final int id;
  private final String username;
  private final String event;
  private final String startTime;
  private final String endTime;
  private final String location; // modify data type
  private final int capacity;
  private int numAttendees;

  public StarterNode(int id, String username, String event, String startTime,
                     String endTime, String location, int capacity) {

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

  @Override
  public String getUsername() {
    return this.username;
  }

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

  public void setNumAttendees(int numAttendees) {
    this.numAttendees = numAttendees;
  }
}
