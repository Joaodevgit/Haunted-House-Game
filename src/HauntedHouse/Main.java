package HauntedHouse;

import Exceptions.InvalidTypeException;
import ed.adt.OrderedListADT;
import ed.exceptions.ElementNotFoundException;
import ed.exceptions.NonComparableException;
import ed.util.ArrayOrderedList;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Spínola
 * @author João Pereira
 */
public class Main {

    public static void main(String[] args) {

        try {
            Room room = new Room("hall", (short) 1);
            Map<Room> map = new Map<>();
            Player jogador = new Player(map.getPoints());
            //map.getPoints();
//            System.out.println(map.toString());
            //Room[] rooms = map.getRoomEdges("hall");
            //map.printCommonRooms(room);
            //System.out.println(map.getEdgeWeight("hall", "cozinha"));
//            Player jogador1 = new Player("Joao", 10, "Maze");
//            Player jogador2 = new Player("Spino", 9, "Mazev2");
//            Player jogador3 = new Player("Player", 12, "Mazev1");
//            Player jogador4 = new Player("Qwerty", 10, "Maze");
//            Player jogador5 = new Player("Hacker", 2, "Mazev2");
//            Player jogador6 = new Player("Rato", 15, "Mazev2");
//            OrderedListADT<Player> players = new ArrayOrderedList<>();
//            players.add(jogador1);
//            players.add(jogador2);
//            players.add(jogador3);
//            players.add(jogador4);
//            players.add(jogador5);
//            players.add(jogador6);
//
            new TUI(map,jogador);
//            new GUI(map);
//            Ficheiros ficheiro = new Ficheiros();
//            ficheiro.writePlayersRankingInfo("ranking.txt", players);
//            String array[] = ficheiro.loadPlayersRankingInfo();
//            for (String array1 : array) {
//                if (array1 != null) {
//                    System.out.println(array1);
//                }
//            }
        } catch (ElementNotFoundException | InvalidTypeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
