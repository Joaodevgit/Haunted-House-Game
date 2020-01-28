package HauntedHouse;

import ed.adt.OrderedListADT;
import ed.exceptions.NonComparableException;
import ed.util.LinkedOrderedList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author João Pereira
 * @author Francisco Spínola
 */
public class Ficheiros {
    public void writePlayersRankingInfo(Instance instance, String mapName) 
            throws IOException {
        File file = new File("ranking.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(i + "º" + " - Jogador: " + instance.getName() + " - Pontos: " + instance.getScore()
                    + " - Mapa: " + mapName);
            writer.newLine();
            writer.flush();
            writer.close();
        }
    }

    public OrderedListADT<String> loadPlayersRankingInfo() throws FileNotFoundException, IOException, NonComparableException {
        File file = new File("ranking.txt");
        OrderedListADT<String> rankingList = new LinkedOrderedList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        int i = 0;
        String st;
        while ((st = br.readLine()) != null) {
            rankingList.add(st);
            i++;
        }
        return rankingList;
    }
}
