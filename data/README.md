# Data Files

Keep data files for each project here, under separate subdirectories named by project. Try to avoid storing large files (~1GB or more) in Git by adding them to your `.gitignore` file.


Design Question:
In this scenaro, my current way of evaluating which command was inputted (by using individual if statements) is inefficent. Instead, it would be better design to have Map of input commands -> the relevant object that could execute such a command. This way, instead of a bunch of for loops, we can easily search our map for what command the user inputted and get out the object we need to execute such a command. In order for this to work properly, our design would also have to ensure that each command (regardless of the object) can be started in the same way (with the same function call). Otherwise, we would have to incorporate more information into our mapping, namely all of the information we need to run any given command (i.e. function name, parameters, etc).


I structured my code into 3 main packages: stars, Parser, and CSVReader.

Parser package:
Classes: Parser.java
The Parser class is where the main functionaality of my parsing user input is. I chose to isolate this parsing into its own class for extensibility purposes, and because it is pretty separate from the Stars project itself. I took advantage of generics in this class to let the user specify what they wanted to use as their delimiter.

CSVReader package:
Classes: CsvReader.java
Like the parser class, I separated my CSV reading/loading logic into a separate class because its pretty independent from the Stars project.

Stars package:
Interface: CommandManager -> NeighborsCommand.java & RadiusCommand.java
Other classes: Star.java, StarComparator.java, StarsCommand.java, Main.java

Currently most of my reading logic is in Main.java, but I hope to change this in future Stars iterations.

CommandManager interface: I chose to model the Neighbors and Radius Commands in an interface because of common methods: errorCheck and execute. However, I often found myself using Neighbors commands in my Radius class (because I took advantage of a lot of similar logic), so I want to explore a better way to organize these two classes.

Star.java -> This class represents a Star, which is very conveneint for keeping track of Star properties and using setters and getters

StarComparator.java -> I used this to set a Star's distance as the comparison metric

StarsCommand.java -> This class processes the user's star command




Known bugs: N/A
