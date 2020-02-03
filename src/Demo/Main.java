package Demo;

import HauntedHouse.Ficheiros;
import HauntedHouse.Map;
import HauntedHouse.Room;
import HauntedHouse.TUI;
import HauntedHouse.GUI;
import HauntedHouse.InstanceTUI;
import ed.adt.OrderedListADT;
import ed.adt.UnorderedListADT;
import ed.exceptions.ElementNotFoundException;
import ed.exceptions.EmptyCollectionException;
import ed.exceptions.NonComparableException;
import ed.util.ArrayOrderedList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
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
            Map<Room> map = new Map<>("mapa.json");
            //new TUI(map);
//            InstanceTUI instance1 = new InstanceTUI("ola", 2, map.getName());
//            InstanceTUI instance2 = new InstanceTUI("jota", 5, map.getName());
//            InstanceTUI instance3 = new InstanceTUI("asdqq", 1, map.getName());
//            InstanceTUI instance4 = new InstanceTUI("wowgood", 20, map.getName());
//            Ficheiros ficheiro = new Ficheiros();
//            OrderedListADT<InstanceTUI> tempEasyArray = new ArrayOrderedList<>();
//            OrderedListADT<InstanceTUI> tempNormalArray = new ArrayOrderedList<>();
//            OrderedListADT<InstanceTUI> tempHardArray = new ArrayOrderedList<>();
//            instance1.setLevel(instance1.EASY);
//            ficheiro.writePlayerRankingInfo(instance1, map.getName());
//            instance2.setLevel(instance2.HARD);
//            ficheiro.writePlayerRankingInfo(instance2, map.getName());
//            instance3.setLevel(instance3.NORMAL);
//            ficheiro.writePlayerRankingInfo(instance3, map.getName());
//            instance4.setLevel(instance4.NORMAL);
//            ficheiro.writePlayerRankingInfo(instance4, map.getName());
//            tempEasyArray = ficheiro.loadByDifficulty((short) 1);
//            tempNormalArray = ficheiro.loadByDifficulty((short) 2);
//            tempHardArray = ficheiro.loadByDifficulty((short) 3);
//            UnorderedListADT<String> lista = ficheiro.loadByDifficulty();
//            Iterator<String> iter = lista.iterator();
//            while(iter.hasNext()){
//                String resultado = iter.next();
//                System.out.println(resultado);
//            }
            //new GUI(map);
        } catch (FileNotFoundException | ElementNotFoundException | ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
