package edu.brown.cs.hshah9.stars;

import edu.brown.cs.hshah9.CommandManager.CommandManager;
import edu.brown.cs.hshah9.Mock.MockCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**
 * This class represents the Stars program and houses functionaliy for command calls.
 */
public class StarsApplication {

  // the Stars program's preferences for parsing
  // regex (source: stackabuse.com)
  private String delim = " " + "(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
  // any negative number
  private Integer parseLimit = -1;

  // maps commands to actions
  private HashMap<String, CommandManager> commandsMap = this.createCommandsMap();

  /**
   * Instantiates stars application.
   */
  public StarsApplication() {
  }




  /**
   * For the GUI, prints out stars.
   * @param starsList list of stars
   * @return stars list to print out
   */
  public String getOutput(ArrayList<Star> starsList) {
    String output = "";
    for (int i = 0; i < starsList.size(); i++) {
      output = output +  (i + 1) + ". " + starsList.get(i).getName() + "\n";
    }
    return output;
  }

  /**
   * Runs the Star's program, using a HashMap to go from inputted command to action.
   * @param input caller input
   */
  public void run(String[] input) {
    String command = input[0];

    if (!validCommand(commandsMap, command) && !command.equals("")) {
      System.out.println("ERROR: Invalid command entered");
    } else {
      Set<String> setOfCommands = commandsMap.keySet();
      Iterator<String> it = setOfCommands.iterator();

      // DESIGN CHOICE: ALL COMMANDS PART OF COMMANDS INTERFACE
      // SO CAN ALL BE CALLED THE SAME WAY (VIA EXECUTE COMMAND)
      while (it.hasNext()) {
        if (it.next().equals(command)) {
          CommandManager commandInstance = commandsMap.get(command);
          commandInstance.execute(input);
        }
      }
    }

  }

  private HashMap<String, CommandManager> createCommandsMap() {
    // stores valid commands
    HashMap<String, CommandManager> commands = new HashMap<>();
    commands.put("stars", new StarsCommand());
    commands.put("naive_neighbors", new NaiveNeighborsCommand());
    commands.put("neighbors", new NeighborsCommand());
    commands.put("naive_radius", new NaiveRadiusCommand());
    commands.put("radius", new RadiusCommand());
    commands.put("mock", new MockCommand());

    return commands;
  }

  private boolean validCommand(HashMap<String, CommandManager> setOfCommands, String command) {
    return setOfCommands.keySet().contains(command);
  }

  /**
   * Gets Star program's delimiter preference for parsing.
   * @return delim preference
   */
  public String getDelim() {
    return this.delim;
  }

  /**
   * Gets Star program's parseLimit preference for parsing.
   * @return parseLimit preference
   */
  public Integer getParseLimit() {
    return this.parseLimit;
  }


}
