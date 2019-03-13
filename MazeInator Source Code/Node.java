/**
 * A storage for Cell
 * that has 2 data fields 
 * data: Cell & next: Node
 * @author Jamsed Cabili, Ceon Rodriguez, Mariz Gabales
 */
public class Node {
   Cell data;
   Node next;

   /**
    * Empty Constructor
    */
   public Node() {
      this(null);
   }

   /**
    * Construct and initalizes a Cell as its data
    * @param cl will be the cell that this node hold
    */
   public Node(Cell cl) {
      this.data = cl;
      this.next = null;
   }
}