package HauntedHouse;

import ed.adt.OrderedListADT;
import ed.exceptions.ElementNotFoundException;
import ed.exceptions.EmptyCollectionException;
import ed.util.ArrayOrderedList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Spínola
 * @author João Pereira
 */
public class Main {
    public static void main(String[] args) {
        try {
            Map<Room> map = new Map<>();
            Room room1 = new Room("hall", (short) 1);
            Room room2 = new Room("sala de estar", (short) -1);
            Iterator<Room> iter = map.iteratorBFS(room1);
            
            OrderedListADT<Integer> list = new ArrayOrderedList<>();
            list.add(3);
            list.add(4);
            list.add(0);
            list.add(25);
            list.add(-4);
            list.add(1);
            System.out.println(list.toString());
            while (iter.hasNext()) {
                //System.out.println(iter.next().toString());
            }
        } catch (EmptyCollectionException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ElementNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //new TUI();
    }
}
