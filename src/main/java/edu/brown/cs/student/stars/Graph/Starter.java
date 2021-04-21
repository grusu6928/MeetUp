package edu.brown.cs.student.stars.Graph;

public class Starter implements FormSubmission {

  private final int id;
  private final String username;
  private final String activity;
  private final String startTime;
  private final String endTime;
  private final double latitude;
  private final double longitude;
  private final int capacity;
  private int numAttendees;

  public Starter(int id, String username, String activity, String startTime,
                 String endTime, double latitude, double longitude, int capacity) {

    this.id = id;
    this.username = username;
    this.activity = activity;
    this.startTime = startTime;
    this.endTime = endTime;
    this.latitude = latitude;
    this.longitude = longitude;
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
