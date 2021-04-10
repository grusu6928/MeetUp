# README

## Stars

### Code Structure

#### Program Flow
In Main, I instantiate a REPL object and call runREPL. I instantiate a starsApplication (which runs the logic of my program) in my REPL class, and in every iteration of my REPL, I read user input by calling a parse method in a separate Parser class, check for EOF, and call starsApplication.run(input).

#### Command Manager Interface / Main Command Classes
CommandManager interface: all of the commands for this project must implement this interface (one required method -> execute(input)); extensibility -> no need for a bunch of if/elses to run commands, just command.execute
    Implementing classes: StarsCommand, NaiveNeighborsCommand, NeighborsCommand, NaiveRadiusCommand, RadiusCommand
    In short -> each command = diff class

#### Stars Application
StarsApplication: site of command execution

#### CSV Reader
CSVReader package/class: reads in CSV; stores as List of Lists of strings; separate class for extensibility
Parser package/class: parses a string based on delimiter; for extensibility: user specifies delimiter + when instaniate Parser object, caller specifies readerType

#### KDTree
KDTree package:
  KDNode class: extensibility -> can hold any object that implements HasCoordinates interface
  KDTree class: stores KDNodes; extensibility -> caller specifies totalDimensions of tree; before each recursive call -> dim = (dim+1) % totalDimensions
  NodeComparator: extensibility -> caller specifies on which dimension to compare
  
#### Shared Data
Shared Data class (Singleton class): allows key data to be set 1x and then accessed across classes (list of Stars, HashMap star name -> star, KD tree root node)
  Why use HashMap -> often I performed look ups of stars based on name to get their coordinates, etc. so HashMap allowed for quick search; this also works because we assume no  legitimate star names will be repeated

#### Stars 
Stars class: separate class b/c easy to store attributes and have getters/setters; implements HasCoordinates interface -> extensibility: KDNode can hold anything that implements HasCoordinates (in this case, a Star)

#### MainFourCommands superclass
MainFourCommands superclass: extended by naive and non-naive neighbors and radius; lots of shared methods (including error checking) between the 4 commands, so this saved lots of code

### How to Run
./run in the command line

### System Tests

#### Location
/tests/student/stars -> Applies to whole Stars project
/tests/student/stars/stars1 -> Stars 1 specific
/tests/student/stars/stars2 -> Stars 2 specific

Note: the Stars 1 and 2 system tests are the same, except for switching "naive_neighbors" and "naive_radius" to "neighbors" and "radius" respectively

#### Notable Tests
##### /stars
- improper_CSV_header: wrong CSV header format
- stars_incorrect_num_args
- stars_invalid_args: various invalid argument cases
##### /stars2
- exclude_provided_name: running name version of commands -> ensure specified star isn't part of output
- incorrect_num_args
- invalid_args
- k_geq_n: reuqesting more neighbors than exist stars -> output as much as can
- large_data_n_cor (n_name, r_cor, r_name): verified correct output on stardata.csv 
- no_file_loaded: can't run commands without loaded csv
- request_0_neighbors: print nothing
- star_name_empty_string: don't run commands on such a star
- star_name_no_quotes: errpr
- zero_radius_corVersion (nameVersion)


### Design Questions

#### Stars 1
1. In this scenaro, my current way of evaluating which command was inputted (by using individual if statements) is inefficent. Instead, it would be better design to have Map of input commands -> the relevant object that could execute such a command. This way, instead of a bunch of for loops, we can easily search our map for what command the user inputted and get out the object we need to execute such a command. In order for this to work properly, our design would also have to ensure that each command (regardless of the object) can be started in the same way (with the same function call). Otherwise, we would have to incorporate more information into our mapping, namely all of the information we need to run any given command (i.e. function name, parameters, etc).

Update: in Stars 3, I updated my code such that each command implements a CommandManager interface and there is a HashMap mapping command name -> command object, so that I can simply query the caller's specified command for the appropriate object and call command_object.execute(input). This got rid of the need for separate if/elses.

#### Stars 2
1. Latitudes/Longditudes are extremely precise, and it is often the case that two places with just minor differences in far down decimal places are in actuality not that close to each other (relatively speaking, i.e. one difference in the ten-thousandths place of a coordinate makes a huge difference). As such, it might be difficult for our KDTree to pick up on such nuanced differences in location which could result in imprecise results.

2. not sure

  
  

### Known Bugs
N/A

