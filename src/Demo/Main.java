package Demo;

import HauntedHouse.Map;
import HauntedHouse.Room;
import HauntedHouse.GUI;
import HauntedHouse.TUI;
import ed.exceptions.ElementNotFoundException;
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
            Map<Room> map = new Map<>("lib/mapa.json");
            //new TUI(map);
            new GUI(map);
        } catch (FileNotFoundException | ElementNotFoundException | ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
