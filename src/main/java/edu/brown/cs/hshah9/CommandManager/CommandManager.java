package edu.brown.cs.hshah9.CommandManager;

/**
 * This interface binds implementing classes to implement an execute method, based on user input.
 */
public interface CommandManager {
  /**
   * Method to execute command.
   * @param input caller input
   * @return -1 on error; 0 o.w.
   */
  int execute(String[] input);

}
