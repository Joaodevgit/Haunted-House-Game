package HauntedHouse;

import ed.exceptions.ElementNotFoundException;
import ed.exceptions.EmptyCollectionException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Spínola
 * @author João Tiago Pereira
 */
public class Main {
    public static void main(String[] args) {
        try {
            Map map = new Map();
            Room room = new Room("hall", 0);
            Iterator<Room> iter = map.iteratorBFS(room);
            while (iter.hasNext()) {
                System.out.println(iter.next().toString());
            }
        } catch (EmptyCollectionException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ElementNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        new TUI();
    }
}
