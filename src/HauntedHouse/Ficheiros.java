package HauntedHouse;

import ed.adt.OrderedListADT;
import ed.exceptions.EmptyCollectionException;
import ed.exceptions.NonComparableException;
import ed.util.ArrayOrderedList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author João Pereira
 * @author Francisco Spínola
 */
public class Ficheiros {

    /**
     * Método responsável por escrever todas as informações necessárias (nome e
     * pontuação do jogador, o nome do mapa e a dificuldadeem que este jogou
     *
     * @param file ficheiro a ser escrito
     * @param list lista ordenada que irá conter a classificação atual de cada
     * dificuldade
     * @param mapName nome do mapa
     * @param level dificuldade do mapa
     * @throws IOException
     */
    private void writeToFile(File file, OrderedListADT<InstanceTUI> list, String mapName, short level) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            switch (level) {
                case 1:
                    writer.write("-----------Nível Básico-----------");
                    break;
                case 2:
                    writer.write("-----------Nível Normal-----------");
                    break;
                case 3:
                    writer.write("-----------Nível Díficil-----------");
                    break;
                default: break;
            }
                writer.newLine();
                Iterator<InstanceTUI> iter = list.iterator();
                while (iter.hasNext()) {
                    InstanceTUI instancia = iter.next();
                    writer.write("Jogador:" + instancia.getName() + "|Pontos:" + instancia.getScore()
                            + "|Mapa:" + mapName);
                    writer.newLine();
                }
                writer.flush();
                writer.close();
            }
        }

    /**
     * Método responsável por escrever os dados de um jogador para o ficheiro
     *
     * @param instance
     * @param mapName nome do mapa
     * @throws IOException caso exista algum erro na escrita para ficheiro
     * @throws EmptyCollectionException caso a lista esteja vazia
     * @throws FileNotFoundException caso o ficheiro não tenha sido encontrado
     * @throws NonComparableException caso o elemento a ser comparado , não seja
     * comparável
     */
    public void writePlayerRankingInfo(InstanceTUI instance, String mapName) throws IOException, EmptyCollectionException,
            FileNotFoundException, NonComparableException {
        File file;
        OrderedListADT<InstanceTUI> currentRankingList = this.loadByDifficulty(instance.getLevel());
        switch (instance.getLevel()) {
            case 1:
                file = new File("lib/BasicRankings.txt");
                break;
            case 2:
                file = new File("lib/NormalRankings.txt");
                break;
            case 3:
                file = new File("lib/HardRankings.txt");
                break;
            default: file = new File("lib/NormalRankings.txt");
        }
        if (currentRankingList.isEmpty() || !currentRankingList.contains(instance)) {
            currentRankingList.add(instance);
            writeToFile(file, currentRankingList, mapName, instance.getLevel());
        }
    }

    /**
     * Método responsável por carregar o ficheiro de classificação dos jogadores
     *
     * @param path caminho do ficheiro a ser carregado
     * @return o uma lista com o conteúdo que foi carregado
     *
     * @throws FileNotFoundException caso o ficheiro não tenha sido encontrado
     * @throws NonComparableException caso o elemento a ser comparado , não seja
     * possível compará-lo
     * @throws IOException caso exista algum erro na escrita para ficheiro
     */
    private OrderedListADT<InstanceTUI> loadPlayersRankingInfo(String path) throws FileNotFoundException,
            NonComparableException, IOException {
        File file = new File(path);
        OrderedListADT<InstanceTUI> newRank = new ArrayOrderedList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null && (st.contains("Jogador:") && st.contains("|Pontos:") && st.contains("|Mapa:"))) {
            String playerName;
            Long playerScore;
            String tempCharCount;
            String mapName;

            playerName = st.substring(8, st.indexOf("|P"));
            tempCharCount = st.substring(16 + playerName.length(), st.indexOf("|M"));
            playerScore = Long.parseLong(tempCharCount);
            mapName = st.substring(22 + playerName.length() + tempCharCount.length(), st.length() - 1);

            InstanceTUI testInstance = new InstanceTUI(playerName, playerScore, mapName);
            newRank.add(testInstance);
        }
        return newRank;
    }

    /**
     * Método responsável por carregar um ficheiro de classificação consoante a
     * dificuldade que se quer usar
     *
     * @param level nível do jogo
     * @return lista ordenada da classificação do nível correspondente
     * @throws FileNotFoundException caso o ficheiro não tenha sido encontrado
     * @throws IOException caso exista algum erro na escrita para ficheiro
     * @throws NonComparableException caso o elemento a ser comparado , não seja
     * possível compará-lo
     */
    public OrderedListADT<InstanceTUI> loadByDifficulty(short level) throws FileNotFoundException,
            IOException, NonComparableException {

        OrderedListADT<InstanceTUI> load = new ArrayOrderedList<>();

        switch (level) {
            case 1:
                load = this.loadPlayersRankingInfo("lib/BasicRankings.txt");
                break;
            case 2:
                load = this.loadPlayersRankingInfo("lib/NormalRankings.txt");
                break;
            case 3:
                load = this.loadPlayersRankingInfo("lib/HardRankings.txt");
                break;
            default:
                throw new FileNotFoundException();
        }
        return load;
    }
}
