package HauntedHouse;

import ed.exceptions.ElementNotFoundException;
import ed.util.matrixGraph.UndirectedGraph;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Francisco Spínola
 * @author João Pereira
 */
public class Map extends UndirectedGraph<Room> {
    private String name;
    private long points;
    
    public Map() throws ElementNotFoundException {
        super();
        
        try {
            // parsing file "Mapa.json" 
            Object obj = new JSONParser().parse(new FileReader("mapa.json")); 

            // typecasting obj to JSONObject 
            JSONObject jo = (JSONObject) obj; 
            
            this.name = (String) jo.get("nome");
            this.points = (long) jo.get("pontos");
            
            JSONArray rooms = (JSONArray) jo.get("mapa"); 
            
            //setting vertexes
            int i = 0;
            JSONObject roomobj;
            //rooms iterator
            while (i < rooms.size()) {
                roomobj = (JSONObject) rooms.get(i);
                Room room = new Room();
                room.setName((String) roomobj.get("aposento"));
                room.setGhost((long) roomobj.get("fantasma"));
                super.addVertex(room);
                i++;
            }
            
            //setting edges
            i = 0;
            
            //rooms iterator
            while (i < rooms.size()) {
                roomobj = (JSONObject) rooms.get(i);
                JSONArray ligacoes = (JSONArray) roomobj.get("ligacoes");
                int j = 0;
                Room room = new Room();
                room.setName((String) roomobj.get("aposento"));
                
                //connections iterator
                while (j < ligacoes.size()) {
                    String type = (String) ligacoes.get(j);
                    
                    //if the room is an entrance
                    if (type.equals("entrada")) {
                        System.out.println(roomobj.get("aposento"));
                        int z = findRoom((String) roomobj.get("aposento"));
                        room = super.vertices[z];
                        room.setType((short) 1);
                    } 
                    
                    // if the room is an exit
                    else if (type.equals("exterior")) {
                        room = super.vertices[findRoom((String) roomobj.get("aposento"))];
                        room.setType((short) -1);
                    } else {
                        int k = 0;
                        while (k < super.numVertices && !super.vertices[k].getName().equals(room.getName())) k++;
                        room.setGhost(super.vertices[k].getGhost());

                        try {
                            super.addEdge(super.vertices[i], room);
                        } catch (ElementNotFoundException ex) {}
                    }
                    j++;
                }
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
    
    private int findRoom (String name) throws ElementNotFoundException {
        int i = 0;
        
        while (i < super.numVertices) {
            Room room = super.vertices[i];
            if (room.getName().equals(name))
                return i;
            i++;
        }
        if (i == super.numVertices)
            throw new ElementNotFoundException();
        return i;
    }
}
