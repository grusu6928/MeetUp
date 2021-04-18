package edu.brown.cs.student.stars;

public class LookerNode implements GraphNode {

  private final int id;
  private final String username;
  private final String activity;
  private final String startTime;
  private final String endTime;
  private final double latitude;
  private final double longitude;


  public LookerNode(int id, String username, String activity, String startTime,
                    String endTime, double latitude, double longitude) {
    this.id = id;
    this.username = username;
    this.activity = activity;
    this.startTime = startTime;
    this.endTime = endTime;
    this.latitude = latitude;
    this.longitude = longitude;
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
  public String getActivity() {
    return this.activity;
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
    return new double[]{this.latitude, this.longitude};
  }


}
