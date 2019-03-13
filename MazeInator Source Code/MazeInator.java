/** KEYS
   *Starting: 4
   *path already visisted: 3
   *End: 5
   *Wall: 0
   *path not yet visited: 2 
 */
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.io.*;

/**
 * Maze-Inator is a program that both generates random Maze and 
 * finds a path from the selected Starting Wall to the selected Ending Wall. 
 * The generated maze can be saved and loaded for future use. It utilizes JavaFX for Graphical User Interface(GUI). 
 * The program will make use of 2-Dimensional Array to represent the maze, 
 * each element in the maze is either a Cell or Wall object. 
 * The program uses Recursive Backtracking in its algorithm for creating the maze as well 
 * as finding the correct path from Start to End. 
 * 
 * <p>
 * After the Maze is created the key(data field) that walls and cells hold will represent what they are in the Maze. It serves like a legend in a map.
 * For a path that is not yet visited the key is 2, a closed wall is 0, the starting wall is 4, the ending wall is 5, 
 * and for a path that is already visited is 3. So if the program is to traverse to wall whose key is 2 then it's fine since it
 * is an open Wall which is part of the path.
 * If it is 0 then it cannot traverse to that wall because it is closed.
 * </p>
 * @author Jamsed Cabili, Ceon Rodriguez, Mariz Gabales
 */
public class MazeInator extends Application {
   /**
    * The 2-Dimensional Array that will store Wall and Cell. They are stored alternatively if the index of either row or column is 
    * divisible by 2 the na Wall is stored in that coordinate otherwise a Cell.
    */
   Component[][] matrix;
   /**
    * A layout from javafx.scene.layout.GirdPane which is perfect for GUI for prgorams that involves 2-Dimensional Array because
    * it has also cooridnates(x,y).
    */
   GridPane gridPane = new GridPane();
   BorderPane borderPane = new BorderPane();
   HBox hBox = new HBox(15);

   
   /** A textfield for the user to input the dimension of their Maze, as well as the filename for the maze to be saved or loaded */
   TextField text = new TextField();
   Text dimensionTxT = new Text("Dimension or File Name:");
   /**A button that will call the method generate() while passing the TextField text. */
   Button generateBtn = new Button("Generate");
   /**A button that will call the method create() in order to create the Maze.*/
   Button createBtn = new Button("Create");
   /**A button that will call the method decreaseSize() in order to decrease the pixel size of Wall and Cell twofold*/
   Button decBtn = new Button("Decrease Size");
   /**A button that will call the method increaseSize() in order to increase the pixel size of Wall and Cell twofold*/
   Button increaseBtn = new Button("Increase Size");
   /**A button that will call the method findPath() in order to find the path from Start to End*/
   Button findBtn = new Button("Find Path");
   /**A button that will call the method save() in order to save the maze currently in the scrren to the file inputted in the TextField.*/
   Button saveBtn = new Button("Save");
   /**A button that will call the method load() in order to load the Maze in the scrren from the file inputted in TextField*/
   Button loadBtn = new Button("Load");

   /**This array represent the  row traversal in the maze. Each element has a pair in array moveY, the pairing is their index.
    *<p>
    * So the element of moveX in index 0 which is 0 is paired to index 0 in moveY which is 2. 
    * This pair will perform the movement of traversing to right. The value is 0, 2, and -2 because for 0 it has to remain in that row while
    * performing the column movement. The 2 and -2 because it has to perform the row movement at the same time to skip the object wall, because this 
    * array will only be used for traversing Cells.
    *<P>
   */
   int[] moveX = {0, 2,  0, -2};

   /**This array represent the  column traversal in the maze. Each element has a pair in array moveX, the pairing is their index.
    *<p>
    * So the element of moveX in index 0 which is 0 is paired to index 0 in moveY which is 2. 
    * This pair will perform the movement of traversing to right. The value is 0, 2, and -2 because for 0 it has to remain in that column while
    * performing the row movement. The 2 and -2 because it has to perform the column movement at the same time to skip the object wall, because this 
    * array will only be used for traversing Cells.
    *<P>
    * <p>
    * This array will only be used in the algorithm in creating the Maze.
    * </p>
   */
   int[] moveY = {2, 0, -2,  0};
   /**This array represent the row traversal in the maze. Each element has a pair in array revY, the pairing is their index.
    * *<p>
    * So the element of revX in index 0 which is 0 is paired to index 0 in revY which is 1. This will perfrom the traversal to the right.
    * This will be used in both traversing to Walls and Cells. 
    *<P>
    * <p>
    * The algorithm has to keep track on which neighbor cell that the current cell has visited (left, right, top or bottom neighbor cell). 
    * It needs to keep track of this for it to know which wall to open. It does it by the index of the chosen movement of
    * traversing to the neighbor cell. Let’s say the chosen movement to traverse to neighbor cell is the index 0 which is the pair 
    * (0,2) in (moveX, moveY), the index 0 will also be the movement to traverse to the wall between them which is the pair (0,1) in (revX, revY). 
    *</p>
   */
   int[] revX =  {0, 1,  0, -1};

   /**This array represent the column traversal in the maze. Each element has a pair in array revY, the pairing is their index.
    * *<p>
    * So the element of revX in index 0 which is 0 is paired to index 0 in revY which is 1. This will perfrom the traversal to the right.
    * This will be used in both traversing to Walls and Cells. 
    *<P>
    * <p>
    * The algorithm has to keep track on which neighbor cell that the current cell has visited (left, right, top or bottom neighbor cell). 
    * It needs to keep track of this for it to know which wall to open. It does it by the index of the chosen movement of
    * traversing to the neighbor cell. Let’s say the chosen movement to traverse to neighbor cell is the index 0 which is the pair 
    * (0,2) in (moveX, moveY), the index 0 will also be the movement to traverse to the wall between them which is the pair (0,1) in (revX, revY). 
    *</p>
   */
   int[] revY =  {1, 0, -1,  0};

   /**The Speed of animation */
   int timer = 1, speed = 80;
   /** The pixel size of Cell */
   int cellSize = 100;
   /**The pixel size of Wall */
   int wallSize = 8;
   /**Dimension of Maze */
   int dimension;
   /**The Starting row coordinate for creating the Maze */
   int startX = -1;
   /**The Starting column coordinate for creating the Maze */
   int startY = -1;
   /**The row coordinate of the Start of Maze*/
   int startXSol = -1;
    /**The coumn coordinate of the Start of Maze*/
   int startYSol = -1;
    /**The row coordinate of the End of Maze*/
   int endXSol = -1; 
    /**The column coordinate of the Start of Maze*/
   int endYSol = -1;

   //MAIN
   @Override
   public void start(Stage primary) {
      text.setAlignment(Pos.BOTTOM_RIGHT);
      text.setMaxWidth(100);
      hBox.getChildren().addAll(decBtn, increaseBtn, dimensionTxT, text, generateBtn, createBtn, findBtn, saveBtn, loadBtn);
      hBox.setAlignment(Pos.CENTER);

      //SETTING THE BUTTONS
      generateBtn.setOnAction(e -> generate(text));
      createBtn.setOnAction(e -> create());
      findBtn.setOnAction(e -> findPath());
      increaseBtn.setOnAction(e -> increaseSize());
      decBtn.setOnAction(e -> decreaseSize());
      saveBtn.setOnAction(e -> save());
      loadBtn.setOnAction(e -> load());

      //SETTING THE SCENE
      borderPane.setBottom(hBox);
      Scene sc = new Scene(borderPane, 1200, 600);
      primary.setTitle("Maze-Inator");
      primary.setScene(sc);
      primary.show();
   }

   /**
    *This generate the maze according to the dimension inputted by the user.
    * This is also where the events for Wall and Cell will be initializes.
    * <p>
    * Keep it mind that if there is already a Maze in the screen it will be replaced.
    * </p>
    * @param txt this is a textfield to get the dimension of size
    */
   public void generate(TextField txt) {
      gridPane = new GridPane();
      borderPane.setCenter(null);
      dimension = Integer.parseInt(txt.getText());
      dimension = (dimension % 2 == 0) ? dimension + 1 : dimension;
      System.out.println(dimension);
      matrix = new Component[dimension][dimension];

      for(int i = 0; i < dimension; i++) {
         for(int j = 0; j < matrix[i].length; j++) {
            //IF TRUE MEANS THIS IS A WALL ELSE  A CELL
            if(i % 2 == 0 || j % 2 == 0){
               Wall wl = new Wall(i, j, wallSize, 0);
               matrix[i][j] = wl;
               gridPane.add(wl, j, i);

               //TO SET THE STARTING OF THE MAZE USING MOUSE CLICK
               wl.setOnMousePressed(e -> {
                  if(e.getButton() == MouseButton.PRIMARY) {
                     if(startXSol == -1) { //IF STARTXSOL == -1 means user did not still select a starting coordinate
                        startXSol = wl.getRowCor();
                        startYSol = wl.getColCor();
                        wl.setStyle("-fx-background-color: #20ff03");
                        wl.setKey(4);
                     } else {
                        ((Wall)matrix[startXSol][startYSol]).setStyle("-fx-background-color: black");
                        ((Wall)matrix[startXSol][startYSol]).setKey(0);
                        startXSol = wl.getRowCor();
                        startYSol = wl.getColCor();
                        wl.setStyle("-fx-background-color: #20ff03");
                        wl.setKey(4);
                     }
                  } else if(e.getButton() == MouseButton.SECONDARY) {
                     if(endXSol == -1) { //IF STARTXSOL == -1 means user did not still select a ending coordinate
                        endXSol = wl.getRowCor();
                        endYSol = wl.getColCor();
                        wl.setStyle("-fx-background-color: white");
                        wl.setKey(5);
                     } else {
                        ((Wall)matrix[endXSol][endYSol]).setStyle("-fx-background-color: black");
                        ((Wall)matrix[endXSol][endYSol]).setKey(0);
                        endXSol = wl.getRowCor();
                        endYSol = wl.getColCor();
                        wl.setStyle("-fx-background-color: white");
                        wl.setKey(5);
                     }
                  }
               });
            } else {
               Cell cl = new Cell(i, j, cellSize, 2);
               matrix[i][j] = cl;
               gridPane.add(cl, j, i);
               
               cl.setOnMousePressed(e -> {
                  if(startX == -1) {
                     startX = cl.getRowCor();
                     startY = cl.getColCor();
                     cl.setBol(true);
                     System.out.println(cl.getBol());
                     cl.setStyle("-fx-background-color: white");
                  } else {
                     Cell temp = ((Cell)matrix[startX][startY]);
                     temp.setBol(false);
                     temp.setStyle("-fx-background-color: #3C443F");
                     startX = cl.getRowCor();
                     startY = cl.getColCor();
                     cl.setBol(true);
                     cl.setStyle("-fx-background-color: white");
                  }
               });
            }   
         }
      }
      borderPane.setCenter(gridPane);
      gridPane.setAlignment(Pos.CENTER);
   } 

  /**
   * This will be called when the create button is clicked. 
   * This initializes the stack which will be used for backtracking method and also the starting cell.
   * It resets the coordinate of start of wall and cell, end, and timer after the recursive method.
   */
   public void create() {
      StackCell stck = new StackCell();
      Cell current = (Cell)matrix[startX][startY];
      createBT(current, stck);
      startXSol = startYSol = endXSol = endYSol = startX = startY = -1;
      timer = 1;
   } 

   /**
    * This method is the backtracking part of create.
    *<p>
    *Check if the current cell has unvisited neighbor.
    *Check if cells to be visited is possible.
    *Check if the cell to be visited is already visited or not.
    *If the cell to be visited is not visited then visit it and push it to the stack
    *then remove wall between current cell and the next cell.
    *Check if the stack is empty if not then pop and call the recursive method again.
    *</p>
    *@param current the cell that is currently being visited
    *@param stck a stack which will be used to store the visited cells
    */
   public void createBT(Cell current, StackCell stck) {
      if(!checkNeighbors(current))
         return;
      int index = (int)(Math.random() * 4); //0 TO 3 RANDOM
      int tempIn; 

      for(int i = 0; i < 4; i++) {
         tempIn = (index + i >= 4) ? index + i - 4 : index + i;
         int tempX = current.getRowCor() + moveX[tempIn];
         int tempY = current.getColCor() + moveY[tempIn];
         if(checkIfPossible(tempX, tempY)) {
            if(!((Cell)matrix[tempX][tempY]).getBol()) {
               stck.push(current);
               openWall(current, tempIn);
               current = (Cell)matrix[tempX][tempY];
               current.setBol(true);
               Cell tempCur = current;
               EventHandler<ActionEvent> eventHandler = e -> tempCur.changeStyle();
               Timeline animation = new Timeline(new KeyFrame(Duration.millis(timer++ * speed), eventHandler));
               animation.play();
               createBT(current, stck);
            }
         }
      }

      if(!stck.isEmpty()) {
         current = stck.pop();
         createBT(current, stck);
      }
   }

   /**
    * Check if the cell passed on it has unvisited neighbor cells.
    * @param current the cell that will be checked if it has neighbors.
    * @return returns true if current has neighbor otherwise false.
    */
   public boolean checkNeighbors(Cell current) {
      for(int i = 0; i < 4; i++) {
         int tempX = current.getRowCor() + moveX[i];
         int tempY = current.getColCor() + moveY[i];
         if(checkIfPossible(tempX, tempY)) {
            if(!((Cell)matrix[tempX][tempY]).getBol()) {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Check if the coordinate passed on it is within the matrix.
    *  @param x row coordinate
    * @param y column coordinate
    * @return true if the coordinate passed on it lesser than dimension of matrix and greater than or equal to 0 otherwise false.
    */
   public boolean checkIfPossible(int x, int y) {
      if(x >= matrix.length || y >= matrix.length || x < 0 || y < 0)
         return false;

      return true;
   }
   /**
    * The wall between the current cell and next cell will open making it part of the maze path 
    * @param current the cell that is currently visited
    * @param i the index of which movement did current cell took
    */
   public void openWall(Cell current, int i) {
      ((Wall)(matrix[current.getRowCor() + revX[i]][current.getColCor() + revY[i]])).setBol(true);
      ((Wall)(matrix[current.getRowCor() + revX[i]][current.getColCor() + revY[i]])).setKey(2);
      EventHandler<ActionEvent> eventHandler = e ->  
         ((Wall)(matrix[current.getRowCor() + revX[i]][current.getColCor() + revY[i]])).changeStyle();
      ;

      Timeline animation = new Timeline(new KeyFrame(Duration.millis(timer++ * speed), eventHandler));
      animation.play();
   }

   /**
    * This method will run when the "Find Path" button is clicked.
    * It check if the maze has solution by calling findPathBT.
    * After looking for path it will reset the start and end coordinate of both cell and wall as well as the timer. 
    */
   public void findPath(){
      if(findPathBT(startXSol, startYSol)) {
         System.out.println("FOUND SOLUTION");
      } else
         System.out.println("NO SULUTION");

      startXSol = startYSol = endXSol = endYSol = startX = startY = -1;
      timer = 1;
   }

   /**
    * This is the backtracking algorithm for looking for path from Start to End.
    *<p>
    * Check the walls of current cells if it is the end.
    * If it is then return true
    * If not, then visit the next cell.
    * Continue visiting the neighbor cell until you find the End in that path.
    * If the path that the program took does not locate End, then it backtracks and goes for another path.
    * </p>
    * @param x the row coordinate of the current element being visited.
    * @param y the column coordinate of the current element being visited.
    * @return true if it was able to find the End of the Maze otherwise false.
    */
   public boolean findPathBT(int x, int y) {
      if(checkAllSides(x, y)) 
         return true;
      for(int i = 0; i < 4; i++) {
         int tempX = x + revX[i];
         int tempY = y + revY[i];
         if(checker(tempX, tempY)) {
            ((Component)matrix[tempX][tempY]).setKey(3);
            EventHandler<ActionEvent> eventHandler = e -> {
               ((Pane)matrix[tempX][tempY]).setStyle("-fx-background-color: #20ff03;");
            };
            Timeline animation = new Timeline(new KeyFrame(Duration.millis(timer++ * speed), eventHandler));
            animation.play();
            if(findPathBT(tempX, tempY))
               return true;
            else {
               ((Component)matrix[tempX][tempY]).setKey(2);
               EventHandler<ActionEvent> tempEventHandler = e -> {
                  ((Pane)matrix[tempX][tempY]).setStyle("-fx-background-color: white");
               };
               Timeline animate = new Timeline(new KeyFrame(Duration.millis(timer++ * speed), tempEventHandler));
               animate.play();
            }
         }
      }
      return false;
   }

   /**
    * Check if the coordinate or its neighbors is the End of the maze. The Key of End is 5.
    * @param x row coordinate
    * @param y column coordinate
    * @return true if the element in coordinate (x, y) or its neighbor is the End otherwise false.      
    */
   public boolean checkAllSides(int x, int y) {
      if(((Component)matrix[x][y]).getKey() == 5)
         return true;
      else if(matrix[x][y] instanceof Cell) {
         if(((Component)matrix[x + 1][y]).getKey() == 5)
            return true;
         else if(((Component)matrix[x - 1][y]).getKey() == 5)
            return true;
         else if(((Component)matrix[x][y + 1]).getKey() == 5)
            return true;
         else if(((Component)matrix[x][y - 1]).getKey() == 5)
            return true;
      }
     return false;

   }

   /**
    * Checks if the coordinate(x, y) to be visited is within the Maze. Greater than or equal to 0 but lesser than 
    * or equal to dimension of the maze. At the Same time it check if the element in coordiante(x, y) is not a closed wall.
    * @param x the row coordinate to be checked
    * @param y the column coordinate to be checked
    * @return false if it the coordinate is out of bounds from the matrix or the element in coordinate(x,y) is a close wall, part of path already,
    * or it is the starting wall otherwise returns true.
    */
   public boolean checker(int x, int y) {
      if(x >= dimension || y >= dimension || x < 0 || y < 0)
         return false;

      if(((Component)matrix[x][y]).getKey() == 0) //if it is a closed wall
         return false;
      if(((Component)matrix[x][y]).getKey() == 4) //if it is the previous cell that is already part of the path
         return false;
      if(((Component)matrix[x][y]).getKey() == 3) //if it is the starting
         return false;
      
      return true;
   }
 
   /**
    * This method is called when the "Decrease Dimension" button is clicked
    * which decreases the pixel size of both Wall and Cell twofold
    */
   public void decreaseSize() {
      this.cellSize /= 2;
      this.wallSize /= 2;

      for(int i = 0; i < dimension; i++) {
         for(int j = 0; j < matrix[i].length; j++) {
            if(matrix[i][j] instanceof Cell) 
               ((Cell)matrix[i][j]).setSize(cellSize);
             else 
              ((Wall)matrix[i][j]).setSize(wallSize);
            
         }
      }
   }

   /**
    * This method is called when the "Increase Dimension" button is clicked 
    * which increases the pixel size of both Wall and Cell twofold
    */
   public void increaseSize() {
      this.cellSize *= 2;
      this.wallSize *= 2;

      for(int i = 0; i < dimension; i++) {
         for(int j = 0; j < matrix[i].length; j++) {
            if(matrix[i][j] instanceof Cell) 
               ((Cell)matrix[i][j]).setSize(cellSize);
             else 
              ((Wall)matrix[i][j]).setSize(wallSize);
         }
      }
   }

   /** 
    * This method is called when "Save" button is clicked.
    * This saves the maze currently in the screen to the name file inputted by the user in the Text Field.
    * <p>
    * In order for Wall and Size to be saved while maintaining its data fields and methods the class ObjectOutputStream is used to 
    * write object to a file that has an extension .dat
    </p>
    */
   public void save() {
      String fileName = text.getText();

      try {  
         try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName + ".dat"))) {
            output.writeInt(dimension);
            output.writeInt(wallSize);
            output.writeInt(cellSize);

            for(int i = 0; i < dimension; i++) {
               for(int j = 0; j < dimension; j++) {
                  if(i % 2 == 0 || j % 2 == 0)
                     output.writeObject((Wall)matrix[i][j]);
                  else
                     output.writeObject((Cell)matrix[i][j]);
               }
            }
         }
      } catch(IOException ex) {
         ex.printStackTrace();
      } finally {
         System.out.println("Saved");
      }
   }

   /**
    * This method is called when the "Load" button is clicked.
    * This loads the maze from a file inputted by the user. Keep in mind that the user
    * does not need to type the extendsion of the file, all it need it its file name wihtout extension.
    * So if the file name is "Maze50.dat" then all you need to type in the textfield is Maze50 to load it.
    * <p>
    * In order to read the objects from a file the class ObjectInputStream is used.
    * Only the datafield is actually stored in the file so when the objects is loaded we need to set the events again 
    * as well as its style and size.
    *</p>
    */
   public void load() {
      String fileName = text.getText();
      gridPane = new GridPane();
      borderPane.setCenter(gridPane);
      gridPane.setAlignment(Pos.CENTER);

      try {
         try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName + ".dat"))) {
            dimension = input.readInt();
            wallSize = input.readInt();
            cellSize = input.readInt();
            matrix = new Component[dimension][dimension];
            for(int i = 0; i < dimension; i++) {
               for(int j = 0; j < dimension; j++) {
                  
                  if(i % 2 == 0 || j % 2 == 0) {
                    Wall wl = (Wall)(input.readObject());
                    matrix[i][j] = wl;
                    gridPane.add((Pane)matrix[i][j], j, i);
                    if(wl.getKey() == 2) 
                        wl.setStyle("-fx-background-color: white");
                     else if (wl.getKey() == 3) 
                        wl.setStyle("-fx-background-color: #20ff03");
                     else if(wl.getKey() == 4)
                        wl.setStyle("-fx-background-color: blue");
                     else if(wl.getKey() == 5)
                        wl.setStyle("-fx-background-color: red");
                     else
                        wl.setStyle("-fx-background-color: #3C443F");

                     wl.setPrefHeight(wallSize);
                     wl.setPrefWidth(wallSize);

                     wl.setOnMousePressed(e -> {
                        if(e.getButton() == MouseButton.PRIMARY) {
                           if(startXSol == -1) {
                              startXSol = wl.getRowCor();
                              startYSol = wl.getColCor();
                              wl.setStyle("-fx-background-color: #20ff03");
                              wl.setKey(4);
                           } else {
                              ((Wall)matrix[startXSol][startYSol]).setStyle("-fx-background-color: black");
                              ((Wall)matrix[startXSol][startYSol]).setKey(0);
                              startXSol = wl.getRowCor();
                              startYSol = wl.getColCor();
                              wl.setStyle("-fx-background-color: #20ff03");
                              wl.setKey(4);
                           }
                        } else if(e.getButton() == MouseButton.SECONDARY) {
                           if(endXSol == -1) {
                              endXSol = wl.getRowCor();
                              endYSol = wl.getColCor();
                              wl.setStyle("-fx-background-color: white");
                              wl.setKey(5);
                           } else {
                              ((Wall)matrix[endXSol][endYSol]).setStyle("-fx-background-color: black");
                              ((Wall)matrix[endXSol][endYSol]).setKey(0);
                              endXSol = wl.getRowCor();
                              endYSol = wl.getColCor();
                              wl.setStyle("-fx-background-color: white");
                              wl.setKey(5);
                           }
                        }
                     });
                  }
                  else {
                     Cell cl = (Cell)(input.readObject());
                     matrix[i][j] = cl;
                     gridPane.add((Pane)matrix[i][j], j, i);
                     if(cl.getKey() == 3) 
                        cl.setStyle("-fx-background-color: #04BBFD");
                     else 
                        cl.setStyle("-fx-background-color: white");
                     cl.setPrefHeight(cellSize);
                     cl.setPrefWidth(cellSize);

                     cl.setOnMousePressed(e -> {
                        if(startX == -1) {
                           startX = cl.getRowCor();
                           startY = cl.getColCor();
                           cl.setBol(true);
                           System.out.println(cl.getBol());
                           cl.setStyle("-fx-background-color: white");
                        } else {
                           Cell temp = ((Cell)matrix[startX][startY]);
                           temp.setBol(false);
                           temp.setStyle("-fx-background-color: #55FF9A");
                           startX = cl.getRowCor();
                           startY = cl.getColCor();
                           cl.setBol(true);
                           cl.setStyle("-fx-background-color: white");
                        }
                     });
                  }
               }
            }
         }
      } catch(IOException ex) {
         ex.printStackTrace();
      } catch(ClassNotFoundException ex) {
         ex.printStackTrace();
      } finally {
         System.out.println(((Wall)matrix[1][2]).getRowCor() + " " + ((Wall)matrix[1][2]).getColCor() + " " + ((Wall)matrix[1][2]).getBol() + " " + ((Wall)matrix[1][2]).getKey());
         System.out.println("Loaded");
      }
   }

}