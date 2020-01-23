/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HauntedHouse;

import ed.util.ArrayOrderedList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author Utilizador
 */
public class Ficheiros {

    public void writePlayersRankingInfo(String path, ArrayOrderedList<Player> playersRanking) throws IOException {

        File file = new File(path);

        Iterator<Player> iter = playersRanking.iterator();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            while (iter.hasNext()) {
                writer.write("Jogador: " + iter.next().getName() + " - Pontos: " + iter.next().getHighscore()
                        + " - Mapa: " + iter.next().getMapName());
                writer.newLine();
            }
            writer.flush();
            writer.close();
        }
    }
}
