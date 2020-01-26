package HauntedHouse;

import ed.adt.OrderedListADT;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Pereira
 */
public class Ficheiros {

    public void writePlayersRankingInfo(String path, OrderedListADT<Player> playersRanking) throws IOException {

        File file = new File(path);
        Iterator<Player> iter = playersRanking.iterator();
        int i = 1;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            while (iter.hasNext()) {
                writer.write(i + "º " + " - Jogador: " + iter.next().getName() + " - Pontos: " + iter.next().getHighscore()
                        + " - Mapa: " + iter.next().getMapName());
                writer.newLine();
                i++;
            }
            writer.flush();
            writer.close();
        }
    }

    public String[] loadPlayersRankingInfo() throws FileNotFoundException {
        File file = new File("ranking.txt");
        String rankingArray[] = new String[10];
        BufferedReader br = new BufferedReader(new FileReader(file));
        int i = 0;
        String st;
        try {
            while ((st = br.readLine()) != null) {
                rankingArray[i] = st;
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Ficheiros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rankingArray;
    }
}
