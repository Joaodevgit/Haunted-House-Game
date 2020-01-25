package HauntedHouse;

import ed.exceptions.ElementNotFoundException;
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
            Map<Room> map = new Map<>();
            
            /*
            Player jogador1 = new Player("Joao", 10, "Maze");
            Player jogador2 = new Player("Spino", 9, "Mazev2");
            Player jogador3 = new Player("Player", 12, "Mazev1");
            Player jogador4 = new Player("Qwerty", 10, "Maze");
            Player jogador5 = new Player("Hacker", 2, "Mazev2");
            Player jogador6 = new Player("Rato", 15, "Mazev2");
            OrderedListADT<Player> players = new ArrayOrderedList<>();
            players.add(jogador1);
            players.add(jogador2);
            players.add(jogador3);
            players.add(jogador4);
            players.add(jogador5);
            players.add(jogador6);
            */
            
            new GUI(map);
            
            /*
            Ficheiros ficheiro = new Ficheiros();
            ficheiro.writePlayersRankingInfo("ranking.txt", (ArrayOrderedList<Player>) players);
            */
        } catch (ElementNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //new TUI();
    }
}
