package edu.brown.cs.student.term_project;

import java.time.LocalTime;

public class LookerNode implements GraphNode {

  private int id;
  private String event;
  private LocalTime startTime;
  private LocalTime endTime;

  public LookerNode(int id, String event, LocalTime startTime, LocalTime endTime) {
    this.id = id;
    this.event = event;
    this.startTime = startTime;
    this.endTime = endTime;
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
    return false;
  }
}
