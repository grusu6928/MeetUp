package edu.brown.cs.student.stars;


import java.time.LocalTime;

public class LookerNode implements GraphNode {

  private int id;
  private String username;
  private String event;
  private String startTime;
  private String endTime;


  public LookerNode(int id, String username, String event, String startTime, String endTime) {
    this.id = id;
    this.username = username;
    this.event = event;
    this.startTime = startTime;
    this.endTime = endTime;
  }


  @Override
  public int getId() {
    return this.id;
  }

  public String getUsername(){return this.username;}

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

}
