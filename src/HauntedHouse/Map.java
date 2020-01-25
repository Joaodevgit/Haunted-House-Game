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
 * <p>Classe responsável por carregar o mapa, fazendo o parsing de um ficheiro JSON, que contem as informações
 * relativas a este.</p>
 * <p>É uma rede direcionada devido ao peso, que neste caso é atribuído pelos pontos por que fantasma, por
 * aposento, é valorizado.</p>
 *
 * @author Francisco Spínola
 */
public class Map<T> extends DirectedNetwork<T> {
    private String name;
    private long points;
    
    /**
     * <p>Parser de JSON para Java, carregando o mapa presente num ficheiro <i>mapa.json</i>.</p>
     * <p>Instancia também a rede direcionada com nós que simbolizam cada aposento.</p>
     */
    public Map() throws ElementNotFoundException {
        super();
        
        try {
            //parsing file "Mapa.json" 
            Object obj = new JSONParser().parse(new FileReader("lib/mapa.json")); 

            //typecasting obj to JSONObject 
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

    /**
     * Obtenção do nome do mapa
     * 
     * @return nome do mapa
     */
    public String getName() {
        return name;
    }

    /**
     * Obtenção dos pontos de vida iniciais do mapa
     * 
     * @return pontos de vida
     */
    public long getPoints() {
        return points;
    }
    
    /**
     * Método responsável por encontrar, nos vértices/nós da rede (grafo), o aposento que possua o nome passado 
     * por argumento.
     * 
     * @param name nome do aposento
     * @return índice do aposento no <i>array</i> de vértices do grafo
     * @throws ElementNotFoundException caso não seja encontrado o aposento com o nome dado
     */
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
