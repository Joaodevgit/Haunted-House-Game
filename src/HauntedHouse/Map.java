package HauntedHouse;

import ed.exceptions.ElementNotFoundException;
import ed.util.matrixGraph.DirectedNetwork;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Francisco Spínola
 * @author João Pereira
 */
public class Map<T> extends DirectedNetwork<T> {
    private String name;
    private long life;
    
    @Override
    public void addVertex(T vertex) {
        if (this.numVertices == this.vertices.length)
            this.expandCapacity();
        
        this.vertices[this.numVertices] = vertex;
        for (int i = 0; i < this.numVertices; i++) {
            this.adjMatrix[this.numVertices][i] = Double.POSITIVE_INFINITY;
            this.adjMatrix[i][this.numVertices] = Double.POSITIVE_INFINITY;
        }
        this.numVertices++;
    }
    
    public Map() throws ElementNotFoundException {
        super();
        
        try {
            // parsing file "Mapa.json" 
            Object obj = new JSONParser().parse(new FileReader("mapa.json")); 

            // typecasting obj to JSONObject 
            JSONObject jo = (JSONObject) obj; 
            
            this.name = (String) jo.get("nome");
            this.life = (long) jo.get("pontos");
            
            JSONArray rooms = (JSONArray) jo.get("mapa"); 
            
            //setting vertexes
            int i = 0;
            JSONObject roomobj;
            //rooms iterator
            while (i < rooms.size()) {
                roomobj = (JSONObject) rooms.get(i);
                Room room = new Room();
                room.setName((String) roomobj.get("aposento"));
                this.addVertex((T)room);
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
                        int z = findRoom((String) roomobj.get("aposento"));
                        room = (Room)super.vertices[z];
                        room.setType((short) 1);
                    } 
                    
                    // if the room is an exit
                    else if (type.equals("exterior")) {
                        int z = findRoom((String) roomobj.get("aposento"));
                        room = (Room) super.vertices[z];
                        room.setType((short) -1);
                    } else {
                        try {
                            int z = findRoom((String) roomobj.get("aposento"));
                            super.addEdge(super.vertices[findRoom(type)], super.vertices[z], (long) roomobj.get("fantasma"));
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
            Room room = (Room) super.vertices[i];
            if (room.getName().equals(name))
                return i;
            i++;
        }
        if (i == super.numVertices)
            throw new ElementNotFoundException();
        return i;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.numVertices; i++) {
            s += this.vertices[i] + "\t\t";
        }
        s += "\n";
        
        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                if (this.adjMatrix[i][j] == Double.POSITIVE_INFINITY)
                    s += this.adjMatrix[i][j] + "\t";
                else
                    s += this.adjMatrix[i][j] + "\t\t";
            }
            s += this.vertices[i];
            s += "\n";
        }
        
        return s;
    }
}
