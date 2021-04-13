package edu.brown.cs.student.stars;

import java.time.LocalTime;

public interface GraphNode {

  int getId(); // unique
  String getEvent();
  String getStartTime();
  String getEndTime();
  boolean isStarter();
}