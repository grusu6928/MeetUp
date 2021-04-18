package edu.brown.cs.student.stars;

import java.time.LocalTime;

public interface GraphNode {

  int getId(); // unique
  String getUsername();
  String getEvent();
  String getStartTime();
  String getEndTime();
  double[] getLocation();
}