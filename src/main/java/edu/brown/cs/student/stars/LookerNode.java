package edu.brown.cs.student.stars;


import java.time.LocalTime;

public class LookerNode implements GraphNode {

  private int id;
  private String event;
  private String startTime;
  private String endTime;
  private String username;

  public LookerNode(int id, String event, String startTime, String endTime, String username) {
    this.id = id;
    this.event = event;
    this.startTime = startTime;
    this.endTime = endTime;
    this.username =username;
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
