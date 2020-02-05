package HauntedHouse;

import ed.adt.OrderedListADT;
import ed.exceptions.EmptyCollectionException;
import ed.exceptions.NonComparableException;
import ed.util.LinkedOrderedList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Classe responsável por tratar da leitura e escrita de ficheiros.
 *
 * @author João Pereira
 * @author Francisco Spínola
 */
public class Files {

    /**
     * Método responsável por escrever todas as informações necessárias (nome e
     * pontuação do jogador, nome do mapa e a dificuldade em que este jogou).
     *
     * @param file ficheiro a ser escrito
     * @param list lista ordenada que irá conter a classificação atual de uma determinada dificuldade
     * @param mapName nome do mapa
     * @param level dificuldade do mapa
     * @throws IOException erro na escrita para ficheiro
     */
    private void writeToFile(File file, OrderedListADT<Instance> list, String mapName, short level) throws IOException {
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
                Iterator<Instance> iter = list.iterator();
                while (iter.hasNext()) {
                    Instance instance = iter.next();
                    writer.write("Jogador:" + instance.getName() + "|Pontos:" + instance.getScore()
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
     * @param instance instância atual do jogo
     * @param mapName nome do mapa
     * @throws IOException caso exista algum erro na escrita para ficheiro
     * @throws EmptyCollectionException caso a lista esteja vazia
     * @throws FileNotFoundException caso o ficheiro não tenha sido encontrado
     * @throws NonComparableException caso o elemento a ser comparado , não seja
     * comparável
     */
    public void writePlayerRankingInfo(Instance instance, String mapName) throws IOException, EmptyCollectionException,
            FileNotFoundException, NonComparableException {
        File file;
        OrderedListADT<Instance> currentRankingList = this.loadByDifficulty(instance.getLevel());
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
     * @return uma lista ordenada com o conteúdo que foi carregado
     * @throws FileNotFoundException caso o ficheiro não tenha sido encontrado
     * @throws NonComparableException caso o elemento a ser comparado , não seja
     * possível compará-lo
     * @throws IOException caso exista algum erro na escrita para ficheiro
     */
    private OrderedListADT<Instance> loadPlayersRankingInfo(String path) throws FileNotFoundException,
            NonComparableException, IOException {
        File file = new File(path);
        OrderedListADT<Instance> newRank = new LinkedOrderedList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        br.readLine();
        while ((st = br.readLine()) != null && (st.contains("Jogador:") && st.contains("|Pontos:") && st.contains("|Mapa:"))) {
            String playerName;
            Long playerScore;
            String tempCharCount;
            String mapName;

            playerName = st.substring(8, st.indexOf("|P"));
            tempCharCount = st.substring(16 + playerName.length(), st.indexOf("|M"));
            playerScore = Long.parseLong(tempCharCount);
            mapName = st.substring(22 + playerName.length() + tempCharCount.length(), st.length());

            Instance testInstance = new Instance(playerName, playerScore, mapName);
            newRank.add(testInstance);
        }
        return newRank;
    }

    /**
     * Método responsável por carregar um ficheiro de classificação consoante a
     * dificuldade que se quer usar
     *
     * @param level nível de dificuldade do jogo
     * @return lista ordenada da classificação do nível correspondente
     * @throws FileNotFoundException caso o ficheiro não tenha sido encontrado
     * @throws IOException caso exista algum erro na escrita para ficheiro
     * @throws NonComparableException caso o elemento a ser comparado , não seja
     * possível compará-lo
     */
    public OrderedListADT<Instance> loadByDifficulty(short level) throws FileNotFoundException,
            IOException, NonComparableException {

        OrderedListADT<Instance> load = new LinkedOrderedList<>();

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
