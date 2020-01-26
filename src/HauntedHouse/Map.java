package HauntedHouse;

import Exceptions.InvalidTypeException;
import ed.exceptions.ElementNotFoundException;
import ed.util.matrixGraph.DirectedNetwork;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * <p>
 * Classe responsável por carregar o mapa, fazendo o parsing de um ficheiro
 * JSON, que contem as informações relativas a este.</p>
 * <p>
 * É uma rede direcionada devido ao peso, que neste caso é atribuído pelos
 * pontos por que fantasma, por aposento, é valorizado.</p>
 *
 * @author Francisco Spínola
 * @author João Pereira
 */
public class Map<T> extends DirectedNetwork<T> {

    private String name;
    private long points;

    /**
     * <p>
     * Parser de JSON para Java, carregando o mapa presente num ficheiro
     * <i>mapa.json</i>.</p>
     * <p>
     * Instancia também a rede direcionada com nós que simbolizam cada
     * aposento.</p>
     *
     * @throws ed.exceptions.ElementNotFoundException
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
                this.addVertex((T) room);
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
                        room = (Room) super.vertices[z];
                        room.setType((short) 1);
                    } // if the room is an exit
                    else if (type.equals("exterior")) {
                        int z = findRoom((String) roomobj.get("aposento"));
                        room = (Room) super.vertices[z];
                        room.setType((short) -1);
                    } else {
                        try {
                            int z = findRoom((String) roomobj.get("aposento"));
                            super.addEdge(super.vertices[findRoom(type)], super.vertices[z], (long) roomobj.get("fantasma"));
                        } catch (ElementNotFoundException ex) {
                        }
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
     * Método responsável por retornar o aposento de entrada
     *
     * @return aposento de entrada
     * @throws ElementNotFoundException exceção lançada caso não exista um
     * aposento de entrada
     */
    public Room getEntranceRoom() throws ElementNotFoundException {
        int i = 0;
        while (i < super.vertices.length && ((Room) super.vertices[i]).getType() == 0) {
            i++;
        }
        if (i == super.numVertices) {
            throw new ElementNotFoundException();
        }
        return (Room) super.vertices[i];
    }

    /**
     * Método responsável por encontrar, nos vértices/nós da rede (grafo), o
     * aposento que possua o nome passado por argumento.
     *
     * @param name nome do aposento
     * @return índice do aposento no <i>array</i> de vértices do grafo
     * @throws ElementNotFoundException caso não seja encontrado o aposento com
     * o nome dado
     */
    private int findRoom(String name) throws ElementNotFoundException {
        int i = 0;

        while (i < super.numVertices) {
            Room room = (Room) super.vertices[i];
            if (room.getName().equals(name)) {
                return i;
            }
            i++;
        }
        if (i == super.numVertices) {
            throw new ElementNotFoundException();
        }
        return i;
    }

    /**
     * Método responsável por retornar o custo entre 2 aposentos
     *
     * @param srcEdge aposento de origem
     * @param destEdge aposento de destino
     * @return custo entre os 2 aposentos
     * @throws ElementNotFoundException caso um dos vértices não seja válido
     */
    public double getEdgeWeight(String srcEdge, String destEdge) throws ElementNotFoundException {
        int srcPos = findRoom(srcEdge);
        int destPos = findRoom(destEdge);

        if (srcPos == super.numVertices && destPos == super.numVertices) {
            throw new ElementNotFoundException();
        } else {
            return super.adjMatrix[srcPos][destPos];
        }
    }

    /**
     * Método responsável por retornar um array de aposentos que têm ligação com
     * o aposento a ser passado por parâmetro
     *
     * @param name nome do aposento que se pretende obter as ligações com outros
     * aposentos
     * @return array de aposentos ligados ao nome
     * @throws ElementNotFoundException caso o nome do aposento não seja
     * enontrado
     */
    public Room[] getRoomEdges(String name) throws ElementNotFoundException {
        int pos = findRoom(name);
        Room[] temp_commonRooms = new Room[this.numVertices];
        int j = 0;
        if (pos == super.numVertices) {
            throw new ElementNotFoundException();
        } else {
            for (int i = 0; i < this.numVertices; i++) {
                if (super.adjMatrix[pos][i] != Double.POSITIVE_INFINITY) {
                    temp_commonRooms[j] = (Room) super.vertices[i];
                    j++;
                }
            }
            Room[] commonRoom = new Room[j];
            for (int i = 0; i < commonRoom.length; i++) {
                commonRoom[i] = temp_commonRooms[i];
            }
            return commonRoom;
        }
    }

    public Room[] printCommonRooms(Room room) throws ElementNotFoundException {
        Room[] rooms = getRoomEdges(room.getName());
        System.out.println("Escolha um aposento: ");
        for (int i = 0; i < rooms.length; i++) {
            System.out.println((i + 1) + " - " + rooms[i].getName());
        }
        if (room.getType() == -1) {
            System.out.println((rooms.length + 1) + " - Exterior");
        }
        return rooms;
    }

    public void menuModoNormal(Room room, Player player, int difficulty) throws ElementNotFoundException {
        Scanner sc = new Scanner(System.in);
        Room currentRoom = room;
        Room[] choiceRooms;
        int a;
        do {
            do {
                System.out.println("Pontos de jogador atuais: " + player.getHighscore());
                System.out.println("Aposento atual: " + currentRoom.getName());
                choiceRooms = this.printCommonRooms(currentRoom);
                System.out.println("Escolha um aposento: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Opção com formato inválido!");
                    sc.next();
                }
                a = sc.nextInt();
            } while (a < 0 || a > choiceRooms.length + 1);
            
            if (a != 0 && player.getHighscore() > 0) {
                double roomPoints = getEdgeWeight(currentRoom.getName(), choiceRooms[a - 1].getName());
                if (roomPoints != 0) {
                    points -= roomPoints * difficulty;
                    player.setHighscore(points);
                }
                currentRoom = choiceRooms[a - 1];
                if (player.getHighscore() < 0) {
                    System.out.println("Game Over Player Morto");
                    a = 0;
                }
            }
        } while (a != 0);
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
                if (this.adjMatrix[i][j] == Double.POSITIVE_INFINITY) {
                    s += this.adjMatrix[i][j] + "\t";
                } else {
                    s += this.adjMatrix[i][j] + "\t\t";
                }
            }
            s += this.vertices[i];
            s += "\n";
        }

        return s;
    }
}
