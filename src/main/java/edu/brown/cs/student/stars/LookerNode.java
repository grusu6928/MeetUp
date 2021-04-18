package edu.brown.cs.student.stars;

public class LookerNode implements GraphNode {

  private final int id;
  private final String username;
  private final String event;
  private final String startTime;
  private final String endTime;


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

  @Override
  public double[] getLocation() {
    return new double[0];
  }


}
