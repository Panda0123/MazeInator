# Maze-nator
 Maze-inator is a program that both generates random Maze and finds a path from the selected Starting Wall to the selected Ending Wall. The generated maze can be saved and loaded for future use. It utilizes JavaFX for Graphical User Interface(GUI). The program will make use of 2-Dimensional Array to represent the maze, each element in the maze is either a Cell or Wall object.

### Scope and Limitation
  The Dimension inputted by the user must be an even number. The dimension of the Maze is at most 98 or else it will cause a StackOverFlow Error while searching for path. User cannot zoom in and out from the maze, but user can decrease the size(pixel) of the Maze.
