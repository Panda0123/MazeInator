/**
 *This class
 */
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.io.Serializable;
/**
 *Will serve as the wall object in the Maze.
 * After creating the Maze it some wall will be part track of the Maze.
 * <p>
 * This class may be used in different board games that requires Walls.
 * </p>
 * <p>
 * It extends the class Pane a layout pane from javafx.scene.layout.Pane.
 * It Implements the interfeace Component and Serializable from java.io.Serializable.
 * </p>
 * 
 * Its Data Fields are
 * <pre>
 * open: boolean
 * key: int
 * row: int
 * col: int
 * size: int
 * </pre>
 * @author Jamsed Cabili, Ceon Rodriguez, Mariz Gabales
 */
public class Wall extends Pane implements Component, Serializable{
      public boolean open;
      int row;
      int col;
      int size = 8;
      int key;

      /**
      * Constructor that specifies its coordinate, size, and the key it holds 
      * @param row row coordinate
      * @param col column coordinate
      * @param size its pixel size
      * @param data the key it holds
      */
      public Wall(int row, int col, int size, int data) {
         this.open = false;
         this.row = row;
         this.col = col;
         this.size = size;
         this.key = data;
         setStyle("-fx-background-color: #3C443F;");
         setPrefHeight(size);
         setPrefWidth(size);
         setBorder(null);
         getChildren().add(new Pane());
      }

   /**
    * A setter method that sets the data field open of  this Wall to what ever bl is.
    * @param bl is boolean that will be the value of open of this Wall.
    */
   @Override
   public void setBol(boolean bl) {
      this.open = bl;
   }

   /**
    * A setter method that sets the legend of this Wall to data
    * @param data  the legend that this Wall will be representing
    */
   @Override 
   public void setKey(int data) {
      this.key = data;
   }

   /**
    * A setter method that sets the pixel size of this Wall.
    * @param size this will be initializes to the pixel size of this Wall.
    */
   @Override
   public void setSize(int size) {
      this.size = size;
      setPrefHeight(size);
      setPrefWidth(size);
   }
   
   /**
    * A getter method that returns the value of the data field open
    * @return returns true if this wall  is open oterwise false
    */
   @Override
   public boolean getBol() {
      return this.open;
   }

   /**
    * A getter method that returns the value of the data field row
    * @return returns the row coordinate of this Wall
    */
   @Override
   public int getRowCor() {
      return this.row;
   }

   /**
    * A getter method that returns the value of the data field col
    * @return returns the column coordinate of this Wall
    */
   @Override
   public int getColCor() {
      return this.col;
   }
   
   /**
    * A getter method that returns the value of the data field key
    * @return returns the current key of this Wall
    */
   @Override
   public int getKey() {
      return this.key;
   }

   /**
    * A void method that changes the color of this Wall to white
    */
   @Override
   public void changeStyle() {
      setStyle("-fx-background-color: white");
   }
}