package Demo;

import HauntedHouse.Map;
import HauntedHouse.Room;
import HauntedHouse.TUI;
import HauntedHouse.GUI;
import ed.exceptions.ElementNotFoundException;
import ed.exceptions.EmptyCollectionException;
import ed.exceptions.NonComparableException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Francisco Spínola
 * @author João Pereira
 */
public class Main {

    public static void main(String[] args) {
        try {
            Map<Room> map = new Map<>();
            new TUI(map);
            //new GUI(map);
        } catch (FileNotFoundException | ElementNotFoundException | ParseException | EmptyCollectionException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonComparableException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
