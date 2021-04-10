package edu.brown.cs.student.stars;

import java.time.LocalTime;

public class StarterNode implements GraphNode {

  private int id;
  private String event;
  private LocalTime startTime;
  private LocalTime endTime;

  private String location; // modify data type
  private int capacity;
  private int numAttendees;


  public StarterNode(int id, String event, LocalTime startTime,
                     LocalTime endTime, String location, int capacity) {

    this.id = id;
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
  public String getEvent() {
    return this.event;
  }

  @Override
  public LocalTime getStartTime() {
    return this.startTime;
  }

  @Override
  public LocalTime getEndTime() {
    return this.endTime;
  }

  @Override
  public boolean isStarter() {
    return true;
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
