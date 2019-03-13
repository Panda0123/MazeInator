import java.io.Serializable;
import javafx.scene.layout.Pane;
/**
 *Will serve as the square object in the Maze.
 * After creating the Maze it will be the track of the Maze.
 * <p>
 * This class may be used in different board games that requires Cells like Chess and others.
 * </p>
 * <p>
 * It extends the class Pane a layout pane from javafx.scene.layout.Pane.
 * It Implements the interfeace Component and Serializable from java.io.Serializable.
 * </p>
 * 
 * Its Data Fields are
 * <pre>
 * visited: boolean
 * key: int
 * row: int
 * col: int
 * size: int
 * </pre>
 * @author Jamsed Cabili, Ceon Rodriguez, Mariz Gabales
 */
public class Cell extends Pane implements Component, Serializable {
   boolean visited;
   int key;
   int row;
   int col;
   int size = 50;
   /**
    * Empty Constructor
    */
   public Cell() {
      
   }
   
   /**
    * Constructor that specifies its coordinate, size, and the key it holds 
    * @param row row coordinate
    * @param col column coordinate
    * @param size its pixel size
    * @param data the key it holds
   */
   public Cell(int row, int col, int size, int data) {
      this.visited = false;
      this.row = row;
      this.col = col;
      this.size = size;
      this.key = data;
      setPrefHeight(size);
      setPrefWidth(size);
      setStyle("-fx-background-color: #3C443F");
      setBorder(null);
      getChildren().add(new Pane());
   }

   /**
    * A setter method that sets the data field visited of Cell to what ever bl is.
    * @param bl is boolean that will be the value of visited of this Cell.
    */
   @Override
   public void setBol(boolean bl) {
      this.visited = bl;
   }

   /**
    * A setter method that sets the key of Cell to data
    * @param data  the legend that this Cell will be representing
    */
   @Override 
   public void setKey(int data) {
      this.key = data;
   }

   /**
    * A setter method that sets the pixel size of this Cell.
    * @param size this will be initializes to the pixel size of this Cell.
    */
   @Override
   public void setSize(int size) {
      this.size = size;
      setPrefHeight(size);
      setPrefWidth(size);
   }
   
   /**
    * A getter method that returns the value of the data field visited
    * @return returns true if this Cell is visited oterwise false
    */
   @Override
   public boolean getBol() {
      return this.visited;
   }

   /**
    * A getter method that returns the value of the data field row
    * @return returns the row coordinate of this Cell
    */
   @Override
   public int getRowCor() {
      return this.row;
   }

   /**
    * A getter method that returns the value of the data field col
    * @return returns the column coordinate of this Cell
    */
   @Override
   public int getColCor() {
      return this.col;
   }
   
   /**
    * A getter method that returns the value of the data field key
    * @return returns the current key of this Cell
    */
   @Override
   public int getKey() {
      return this.key;
   }

   /**
    * A void method that changes the color of this Cell to white
    */
   @Override
   public void changeStyle() {
      setStyle("-fx-background-color: white");
   }
 }