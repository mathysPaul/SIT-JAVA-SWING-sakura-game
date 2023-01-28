> **Student ID**: Z122266

> **Name**: Mathys Paul

# GAME

## Synopsis

In "Sakura: The Daily Life of a Ninja", player play the role of Sakura, a young ninja from the hidden village of Konoha. Under the guidance of Kakashi sensei, player must complete their mission to capture all of Naruto Uzumaki's clones before their rival Sasuke can capture them. The game offers an exciting and challenging experience as player navigate through different levels, using their stealth and ninja skills to outwit the prodigy, Sasuke, and his skills to perceive and track down any ninja who crosses his clone limit! With its fast-paced action and challenging gameplay, "Sakura: Everyday Life of a Ninja" is a must-have game for all fans of the ninja genre and for those who want to discover Sakura as a ninja.

##  Compile & Execute

* Open a command prompt or terminal.

* Navigate to the directory where this Readme.md and program files are located.

* Compile the file(s) by running the following command:
```shell
javac *.java
```
This will compile the .java files and create new .class files in the same directory.

* Run the program using the following command:
```shell
java Main <map.txt>
```

> **Note**
The map.txt is the file that contains the map information. You can therefore use your own maps or those in the *map* directory (e.g.: pacman.txt, tuto.txt).

* **Quick start**:
```shell
javac *.java && java Main map/pacman.txt
```

## Play

* Use the **directional arrows** to **move**.
* Capture all Naruto Uzumaki clones (orange and yellow) to win
* Beware of Sasuke's clones (in black) which can make you lose by catching you before you have had time to catch all the Naruto clones!
* Naruto is faster than Sasuke
* Sasuke can see you from 16 squares away but Naruto can only see you from 8!
* Sasuke has smarter moves than Naruto! 

## OOP Concept

* Class/Interface:
  * Interface *Animate*:
    * Class *Character* implements **Animate**
    * Class *Player* extends **Character**
    * abstract class *NPC* extends **Character**
      * class *Monster* extends **NPC**
      * class *Target* extends **NPC**
  * Animate can be used to move walls in a future version.

* Encapsulation/Abstraction/Polymophism
  * **Encapsulation**: I've used Encapsulation by hiding the implementation details of the Character and Player classes and exposing only the necessary information through public methods.
  * **Abstraction**: I've used Abstraction by creating an interface called Animate, which defines an abstract set of methods that must be implemented by any class that implements it, this allows different types of characters to be created and used interchangeably.
  * **Polymorphism**: I've used Polymorphism by allowing the move() method in the Character class to be overridden by subclasses like the Player class, this allows for different behavior while still maintaining a consistent interface.

* Collection/Generics
  * Collection: The use of List<Position> in the Character class to define a list of possible directions for the character to move for example.
  * I used the concept of generics to create a factory class that can create instances of any class that extends the *Animate* class. This makes the factory class flexible and reusable. To create future levels with spawn locations with regular spawns!
* Input/Output:
  * In this code, I utilized the Input/Output concept by using the Swing library to capture keyboard inputs and display the game on the screen. Additionally, the Map class reads an input file to generate the game map.
* Thread:
  * I find that using the thread in a game like mine is not necessary at this level but why not in a more advanced version.
* Design Patterns
  * I used the Factory Design Pattern to create a factory class that generates objects of any class that extends the Animate class, providing a unified interface for creating objects and encapsulating the object creation process. It makes the code more flexible and maintainable.

## Bonus
* The addition of the monster character (Sasuke)
* With unique AI behavior using the A* algorithm
* Implementation of variable speed for both monsters and targets for added gameplay dynamics
* The capability to create and customize maps using a .txt file input
* Enhanced visual experience with sprite animation for all in-game characters. 

### Map Creator
  1. First, create a new .txt file.
  2. Using a text editor, open the file and begin creating your map.
  3. To create a rectangular map, make sure to create a grid of numbers that is the same number of rows and columns. For example, a 16x12 map would have 16 rows and 12 columns of numbers.
  4. To place the player, use the number 16 in the desired location on the map. For example, to place the player in the top left corner of the map, you would type "16" at the beginning of the first row.
  5. To place monsters (Sasuke clones), use the number 17 in the desired location on the map. For example, to place a monster in the bottom right corner of the map, you would type "17" at the end of the last row.
  6. To place targets (Naruto clones), use the number 18 in the desired location on the map.
  7. To create the basic floor, use the number -1.
  8. To create the floor with decorations, use the numbers 0 to 11.
  9. To create walls, use the numbers 12 to 15.
  10. Once you have placed all of the characters and decorations in the desired locations, save the file and close the text editor.
  11. In your game code, use the Map class's read() method to read the "map.txt" file and create the map in the game.

*Example* of a 5x5 map:

```
16 -1 -1 12 15
12 -1 18 -1 12
-1 -1 -1 -1 -1
12 -1 17 -1 12
15 12 -1 -1 12
```

This map has a player in the top left corner, a target in the middle, a monster in the bottom right corner, and walls on the edges and in the middle.

> **Warning**
One or more spaces between each number /!\