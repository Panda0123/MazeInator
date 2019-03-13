/**
 * A stack that holds Cell
 * Size is static
 * Its data field
 * <pre>
 *  head: Node
 * </pre>
 * @author Cabili, Rodriguez, Gabales
 */
public class StackCell {
   private Node head;

   /**
    * Adds the parameter data to the head of the stack
    * @param data the Cell that will be pushed in the stack
    */
   public void push(Cell data) {
      Node added = new Node(data);
      if(isEmpty())
         head =  added;
      else {
         added.next = head;
         head = added;
      } 
   }
   /**
    * Removes the Cell in the head and returns it
    * @return It returns Cell that is on the head of Stack
    */
   public Cell pop() {
      Node current = head;
      Cell temp = head.data;
      head = head.next;
      return temp;
   }

   /**
    * Identifies if the stack is Empty
    * @return true if stack is emprt otherwise dalse
    */
   public boolean isEmpty(){
      return this.head == null;
   }
}