package HauntedHouse;

import ed.adt.UnorderedListADT;
import ed.exceptions.ElementNotFoundException;
import ed.exceptions.EmptyCollectionException;
import ed.exceptions.NonAvailablePath;
import ed.util.ArrayUnorderedList;
import ed.util.matrixGraph.DirectedNetwork;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
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
 * pontos por que cada fantasma, por aposento, é valorizado.</p>
 *
 * @author Francisco Spínola
 * @author João Pereira
 */
public class Map<T> extends DirectedNetwork<T> {

    private final String name;
    private final long points;

    /**
     * <p>
     * Parser de JSON para Java, carregando o mapa presente num ficheiro
     * <i>mapa.json</i>.</p>
     * <p>
     * Instancia também a rede direcionada com nós que simbolizam cada
     * aposento.</p>
     * 
     * @param path caminho para o ficheiro do mapa
     */
    public Map(String path) throws ElementNotFoundException, FileNotFoundException, IOException, ParseException {
        super();
        //parsing JSON map file
        Object obj = new JSONParser().parse(new FileReader(path));

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
            super.addVertex((T) room);
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
     * Método responsável por retornar o aposento de entrada.
     *
     * @return aposento de entrada
     * @throws ElementNotFoundException exceção lançada caso não exista um
     * aposento de entrada
     */
    public T getEntranceRoom() throws ElementNotFoundException {
        int i = 0;
        while (i < super.numVertices && ((Room) super.vertices[i]).getType() != 1) {
            i++;
        }
        if (i == super.numVertices) {
            throw new ElementNotFoundException();
        }
        return super.vertices[i];
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
     * Método responsável por retornar o custo entre 2 aposentos.
     *
     * @param srcEdge aposento de origem
     * @param destEdge aposento de destino
     * @return custo entre os 2 aposentos
     * @throws ElementNotFoundException caso um dos vértices não seja válido
     */
    public long getEdgeWeight(String srcEdge, String destEdge) throws ElementNotFoundException {
        int srcPos = findRoom(srcEdge);
        int destPos = findRoom(destEdge);

        return (long) super.adjMatrix[srcPos][destPos];
    }
    
    /**
     * Responsável por retornar uma lista não ordenada de aposentos de saída.
     *
     * @return lista não ordenada de aposentos de saída
     */
    public UnorderedListADT<T> getExitRooms() {
        UnorderedListADT<T> exitRooms = new ArrayUnorderedList<>();

        for (int i = 0; i < super.numVertices; i++) {
            if (((Room) super.vertices[i]).getType() == -1) {
                exitRooms.addToRear(super.vertices[i]);
            }
        }
        return exitRooms;
    }
    
    /**
     * Responsável por retornar o melhor aposento de saída para ser usado para a simulação.
     * 
     * @return melhor aposento de saída
     * @throws ElementNotFoundException caso o aposento não seja encontrado
     * @throws NonAvailablePath caso não exista um caminho
     * @throws EmptyCollectionException caso a coleção esteja vazia
     */
    public T getBestExitRoom() throws ElementNotFoundException, NonAvailablePath, EmptyCollectionException {
        T entranceRoom = getEntranceRoom();
        T bestExitRoom = null;
        int count = 0;
        int lowestSteps = Integer.MAX_VALUE;
        double lowest = Double.MAX_VALUE;
        Iterator<T> iterCosts = getExitRooms().iterator();
        while (iterCosts.hasNext()) {
            T currentExitRoom = iterCosts.next();
            double currentMinCost = super.shortestPathWeight(entranceRoom, currentExitRoom);
            Iterator<Room> iterPath = super.iteratorShortestPath(entranceRoom, currentExitRoom);
            while (iterPath.hasNext()) {
                count++;
                iterPath.next();
            }
            if (currentMinCost < lowest && count < lowestSteps) {
                lowest = currentMinCost;
                lowestSteps = count;
                bestExitRoom = currentExitRoom;
            }
            count = 0;
        }
        return bestExitRoom;
    }

    /**
     * Método responsável por retornar uma lista não ordenada de aposentos que
     * têm ligação com o aposento a ser passado por parâmetro.
     *
     * @param name nome do aposento que se pretende obter as ligações com outros
     * aposentos
     * @return lista não ordenada de aposentos ligados ao nome do aposento
     * @throws ElementNotFoundException caso o nome do aposento não seja
     * enontrado
     */
    public ArrayUnorderedList<T> getRoomEdges(String name) throws ElementNotFoundException {
        ArrayUnorderedList<T> commonRooms = new ArrayUnorderedList<>();
        int pos = findRoom(name);
        for (int i = 0; i < super.numVertices; i++) {
            if (super.adjMatrix[pos][i] != Double.POSITIVE_INFINITY) {
                commonRooms.addToRear(super.vertices[i]);
            }
        }
        return commonRooms;
    }

    /**
     * Lista as opções de escolha dos aposentos que se encontram ligados a um determinado aposento.
     *
     * @param room aposento a verificar ligações
     * @return lista de opções
     * @throws ElementNotFoundException aposento não encontrado
     * @deprecated era usado apenas em modo <code>TUI</code>
     */
    public ArrayUnorderedList<T> printCommonRooms(Room room) throws ElementNotFoundException {
        ArrayUnorderedList<T> rooms = getRoomEdges(room.getName());
        Iterator<T> iter = rooms.iterator();
        int i = 0;
        System.out.println("Opções: ");
        while (iter.hasNext()) {
            Room roomIter = (Room) iter.next();
            System.out.println((i + 1) + " - " + roomIter.getName());
            i++;
        }
        if (room.getType() == -1) {
            System.out.println((rooms.size() + 1) + " - Exterior");
        }
        return rooms;
    }

    /**
     * Método responsável por retornar o aposento escolhido pelo utilizador.
     *
     * @param room aposento a ser procurado na lista não ordenada
     * @param option opção que o utilizador escolheu
     * @return aposento em comun escolhido pelo utilizador
     * @throws ElementNotFoundException caso o aposento não exista
     * @throws ArrayIndexOutOfBoundsException caso o nº de option esteja fora do
     * intervalo
     * @deprecated era usado apenas em modo <code>TUI</code>.
     */
    public T findCommonRoom(Room room, int option) throws ElementNotFoundException, ArrayIndexOutOfBoundsException {

        ArrayUnorderedList<T> commonRooms = getRoomEdges(room.getName());
        if (option < 0 || option > commonRooms.size() - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Iterator<T> iter = commonRooms.iterator();
        int i = 0;
        while (iter.hasNext() && i != option) {
            i++;
            iter.next();
        }
        return iter.next();
    }

    /**
     * Método responsávelo por simular o modo de jogo "Normal".
     *
     * @param instance instância atual do jogo
     * @throws ElementNotFoundException caso o aposento não seja encontrado
     * @return -1 if the player choosed to exit early in the game, 0 if the
     * player lost the game and 1 if he won.
     * @deprecated era usado apenas em modo <code>TUI</code>
     */
    public short menuModoNormal(InstanceTUI instance) throws ElementNotFoundException {
        Scanner sc = new Scanner(System.in);
        ArrayUnorderedList<T> choiceRooms;
        int a;
        do {
            do {
                System.out.println("Pontos de jogador atuais: " + instance.getScore());
                System.out.println("Aposento atual: " + instance.getPos().getName());
                choiceRooms = this.printCommonRooms(instance.getPos());
                System.out.println("Escolha um aposento: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Opção com formato inválido!");
                    sc.next();
                }
                a = sc.nextInt();
            } while (a < 0 || a > choiceRooms.size());

            Room dstRoom = (Room) findCommonRoom(instance.getPos(), a - 1);
            long roomPoints = getEdgeWeight(instance.getPos().getName(), dstRoom.getName());

            //If the room contains a ghost
            if (roomPoints != 0) {
                System.out.println("Ups... Encontraste um fantasma. Perdeste " + (roomPoints * instance.getLevel()) + " pontos.");
                instance.setScore(instance.getScore() - (roomPoints * instance.getLevel()));
                if (instance.getScore() < 0)
                    instance.setScore(0);
            }

            instance.setPos(dstRoom);
        } while (a != 0 && instance.getPos().getType() != -1 && instance.getScore() > 0);

        //Player choosed to quit
        if (a == 0) {
            return -1;
        } 
        //Player has no life points remaining (game lost)
        else if (instance.getScore() == 0) {
            return 0;
        }
        //Player found the exit (game won)
        else {
            return 1;
        }
    }
}
