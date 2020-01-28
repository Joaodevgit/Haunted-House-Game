package HauntedHouse;

import ed.adt.UnorderedListADT;
import ed.exceptions.EmptyCollectionException;
import ed.exceptions.NonComparableException;
import ed.util.ArrayUnorderedList;
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
    public void writePlayersRankingInfo(InstanceTUI instance, String mapName) throws IOException, 
            EmptyCollectionException, FileNotFoundException, NonComparableException {
        File file = new File("lib/rankings.txt");
        FileWriter fw = new FileWriter(file, false);
        BufferedWriter writer = new BufferedWriter(fw);
        UnorderedListADT load = this.loadPlayersRankingInfo();
        
        int lines = numLines();
        int[] scores = points();
        
        if (lines < 10 && lines > 0) {
            int i = 0;
            while (i < scores.length && scores[i] > instance.getScore()) i++;
            //append to rankings.txt
        } else if (lines == 0) {
            writer.write("Jogador: " + instance.getName() + " - Pontos: " + instance.getScore()
                    + " - Mapa: " + mapName);
            writer.newLine();
        } else /*lines == 10*/ {
            //append to rankings.txt
            writer.newLine();
            //delete last line of rankings.txt
        }
        writer.close();
    }

    public UnorderedListADT<String> loadPlayersRankingInfo() throws FileNotFoundException, IOException, NonComparableException {
        File file = new File("lib/rankings.txt");
        UnorderedListADT<String> rankingList = new ArrayUnorderedList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        int i = 0;
        String st;
        while ((st = br.readLine()) != null) {
            rankingList.addToRear(st);
            i++;
        }
        return rankingList;
    }
    
    private int numLines() throws IOException {
        File file = new File("lib/rankings.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int i = 0;
        String st;
        while ((st = br.readLine()) != null) i++;
        return i;
    }
    
    protected int[] points() throws EmptyCollectionException, IOException, FileNotFoundException, NonComparableException {
        int resultList[] = new int[numLines()];
        UnorderedListADT<String> list = this.loadPlayersRankingInfo();
        int i = 0;
        while (i < list.size()) {
            String[] scores = list.last().split("Pontos:");
            int index = 0;
            String score = "";
            while (index < scores[1].length() && (scores[1].charAt(index) < 48 || scores[1].charAt(index) > 57)) index++;
            while (scores[1].charAt(index) >= 48 && scores[1].charAt(index) <= 57) {
                score += list.last().charAt(index);
                index++;
            }
            resultList[i] = Integer.parseInt(score);
        }
        return resultList;
    }
}
