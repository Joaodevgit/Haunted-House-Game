package HauntedHouse;

import ed.util.matrixGraph.UndirectedGraph;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Francisco Spínola
 * @author João Tiago Pereira
 */
public class Map extends UndirectedGraph<Room> {
    private String name;
    private long points;
    
    public Map() {
        super();
        
        try {
            // parsing file "Mapa.json" 
            Object obj = new JSONParser().parse(new FileReader("mapa.json")); 

            // typecasting obj to JSONObject 
            JSONObject jo = (JSONObject) obj; 
            
            this.name = (String) jo.get("nome");
            this.points = (long) jo.get("pontos");
            
            //Iterator<Map.Entry> itr1 = address.entrySet().iterator(); 
            
            // getting phoneNumbers 
            JSONArray rooms = (JSONArray) jo.get("mapa"); 
            int i = 0;
            while (i < rooms.size()) {
                JSONObject roomobj = (JSONObject) rooms.get(i);
                Room room = new Room();
                room.setName((String) roomobj.get("aposento"));
                room.setGhost((long) roomobj.get("fantasma"));
                super.addVertex(room);
                i++;
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("error: map not found.");
        } catch (IOException ex) {
            System.out.println("error: input/output error.");
        } catch (ParseException ex) {
            System.out.println("error: parsing from json failed.");
        }
    }
}
