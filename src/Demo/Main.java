package Demo;

import Exceptions.InvalidTypeException;
import HauntedHouse.*;
import ed.adt.OrderedListADT;
import ed.exceptions.ElementNotFoundException;
import ed.exceptions.NonComparableException;
import ed.util.ArrayOrderedList;
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

    public static void main(String[] args) throws IOException {

        try {
            Room room = new Room("hall");
            Map<Room> map = new Map<>();
            //map.printCommonRooms(room);
            //new TUI(map);
            
            //Instance instance = new Instance(tui, map.getPoints(), map.getEntranceRoom());

            //map.getPoints();
//            System.out.println(map.toString());
            //Room[] rooms = map.getRoomEdges("hall");
            //map.printCommonRooms(room);
            //System.out.println(map.getEdgeWeight("hall", "cozinha"));
//            new GUI(map);
        } catch (FileNotFoundException | ElementNotFoundException | ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
