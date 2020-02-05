package HauntedHouse;

import ed.adt.OrderedListADT;
import ed.exceptions.ElementNotFoundException;
import ed.exceptions.EmptyCollectionException;
import ed.exceptions.NonComparableException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * <i>Text User Interface</i>
 *
 * @author João Pereira
 * @author Francisco Spínola
 * @deprecated Deixou de ser utiilizado desde que foi criada a <code>GUI</code>. Está incompleta.
 * @see GUI
 */
public class TUI {
    
    private InstanceTUI instance;
    private Files files;
    
    /**
     * Criação da <code>TUI</code>
     * 
     * @param map mapa do jogo
     */
    public TUI(Map map) throws ElementNotFoundException, EmptyCollectionException, IOException, 
            FileNotFoundException, NonComparableException {
        this.instance = new InstanceTUI(this, map.getPoints(), (Room) map.getEntranceRoom());
        this.files = new Files();
        this.MainMenu(map);
    }
    
    /**
     * Apresenta o menu principal do jogo.
     * 
     * @param map mapa do jogo
     */
    private void MainMenu(Map map) throws ElementNotFoundException, IOException, EmptyCollectionException, FileNotFoundException, NonComparableException {
        Scanner sc = new Scanner(System.in);
        int o;
        do {
            do {
                this.Screen_MainMenu(instance.getName(), getDifficulty(instance.getLevel()));
                System.out.println("Introduza opção: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Opção com formato inválido!");
                    sc.next();
                }
                o = sc.nextInt();
            } while (o < 0 || o > 4);
            switch (o) {
                case 1:
                    this.Screen_NormalGameMode();
                    short result = map.menuModoNormal(this.instance);
                    if (result == 0)
                        this.Screen_DeadInfo();
                    else if (result == 1) {
                        this.Screen_VictoryInfo(instance);
                        this.files.writePlayerRankingInfo(this.instance, map.getName());
                    }
                    instance.reset((Room)map.getEntranceRoom(), map.getPoints());
                    break;
                case 2:
                    this.Screen_SimulationGameMode();
                    instance.reset((Room)map.getEntranceRoom(), map.getPoints());
                    break;
                case 3:
                    this.Screen_MapRanking();
                    break;
                case 4:
                    instance.changeDifficultyChoice(this);
                    break;
                default:
                    System.out.println("Ohhhh! Adeus... :(");
                    break;
            }
        } while (o != 0);
    }

    /**
     * Apresenta visualmente o menu principal.
     * 
     * @param name nome do jogador
     * @param difficulty nível de dificuldade do jogador
     */
    private void Screen_MainMenu(String name, String difficulty) {
        System.out.println(" __________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|                     Casa Assombrada                              |");
        System.out.println("|                                                                  |");
        System.out.println("|                  Bem vindo " + name + "!                          |");
        System.out.println("|                                                                  |");
        System.out.println("|            Dificuldade de jogo atual: " + difficulty + "                     |");
        System.out.println("|                                                                  |");
        System.out.println("|     1 -> Modo Normal                                             |");
        System.out.println("|     2 -> Modo Simulação                                          |");
        System.out.println("|     3 -> Classificação por pontos de vida restantes              |");
        System.out.println("|     4 -> Alterar a dificuldade do jogo                           |");
        System.out.println("|                                                                  |");
        System.out.println("|     0 -> Sair                                                    |");
        System.out.println("|__________________________________________________________________|");
    }

    /**
     * Apresenta ecrã de escolha de dificuldade.
     */
    protected void Screen_DifficultyChoice() {
        System.out.println(" __________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|                       Dificuldade do jogo                        |");
        System.out.println("|                                                                  |");
        System.out.println("|         1 -> Básico                                              |");
        System.out.println("|         2 -> Normal                                              |");
        System.out.println("|         3 -> Díficil                                             |");
        System.out.println("|                                                                  |");
        System.out.println("|__________________________________________________________________|");
    }

    /**
     * Escolha de visualização das classificações dos jogadores, de acordo com um determinado nível de dificuldade.
     */
    protected void Screen_RankingChoice() {
        System.out.println(" __________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|         Classificação por pontos de vida restantes               |");
        System.out.println("|                                                                  |");
        System.out.println("|         1 -> Básico                                              |");
        System.out.println("|         2 -> Normal                                              |");
        System.out.println("|         3 -> Díficil                                             |");
        System.out.println("|                                                                  |");
        System.out.println("|         0 -> Sair                                                |");
        System.out.println("|__________________________________________________________________|");
    }

    /**
     * Representa em String o nível de dificuldade passado por argumento.
     * 
     * @param difficultyNumber nível de dificuldade
     * @return representação em String do nível de dificuldade
     */
    private String getDifficulty(int difficultyNumber) {
        String difficulty;
        switch (difficultyNumber) {
            case 1:
                difficulty = "Básico";
                break;
            case 2:
                difficulty = "Normal";
                break;
            case 3:
                difficulty = "Difícil";
                break;
            default:
                difficulty = "erro";
                System.out.println("Erro!");
        }
        return difficulty;
    }

    /**
     * Apresenta visualmente a secção do programa onde este pede ao jogador para introduzir o seu nome.
     */
    protected void Screen_PlayerName() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                       Nome do jogador                           |");
        System.out.println("|_________________________________________________________________|");
        System.out.println("Nome do jogador: ");
    }

    /**
     * Representa o jogo em modo simulação.
     */
    private void Screen_SimulationGameMode() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                       Modo Simulação                            |");
        System.out.println("|_________________________________________________________________|");
    }

    /**
     * Representa o início de um jogo em modo normal.
     */
    private void Screen_NormalGameMode() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                         Modo Normal                             |");
        System.out.println("|_________________________________________________________________|");
    }

    /**
     * Demonstra parte do ecrã das classificações do mapa
     */
    private void Screen_MapRanking() throws FileNotFoundException, IOException, NonComparableException {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|                       Classficação do mapa                       |");
        System.out.println("|__________________________________________________________________|");
        OrderedListADT<Instance> rankings = this.files.loadByDifficulty((short)this.instance.getLevel());
        Iterator<Instance> iter = rankings.iterator();
        int count = 0;
        while (iter.hasNext() && count < 10) {
            int pos = count+1;
            Instance instance = iter.next();
            System.out.println(pos + "º  -  " + instance.getName() + "  -  " + instance.getScore());
            count++;
        }
        pressEnter();
    }

    /**
     * Apresenta parte do ecrã das classificações dos jogadores.
     */
    protected void Screen_PlayerInfo() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                   Informação do jogador                         |");
        System.out.println("|_________________________________________________________________|");
    }

    /**
     * Apresenta o ecrã de derrota.
     */
    protected void Screen_DeadInfo() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|            Perdeu as vidas todas! Game Over                     | ");
        System.out.println("|__________________________________________________________________|");
        pressEnter();
    }

    /**
     * Apresenta o ecrã de vitória.
     * 
     * @param instance instância atual do jogo
     */
    protected void Screen_VictoryInfo(InstanceTUI instance) {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|                  Parabéns chegou à saída!                        | ");
        System.out.println("|__________________________________________________________________|");
        System.out.println("Pontos: " + instance.getScore());
        pressEnter();
    }

    /**
     * Pede ao utilizador que pressione Enter para continuar o programa.
     */
    private void pressEnter() {
        System.out.println("\nPressione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }
}
