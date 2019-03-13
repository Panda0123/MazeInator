/**
 * A base interface that is implemented by both Wall and Cell 
 * in order for them to share common methods while extending the class Pane for GUI.
 * At the same time for them to be stored in the 2-Dimensional Array.
 * @author Jamsed Cabili, Ceon Rodriguez, Mariz Gabales
 */
interface Component{
   abstract void setBol(boolean bl);
   abstract void setKey(int data);
   abstract void setSize(int size);
   abstract boolean getBol();
   abstract int getRowCor();
   abstract int getColCor();
   abstract void changeStyle();
   abstract int getKey();
}