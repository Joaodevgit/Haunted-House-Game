package HauntedHouse;

import ed.adt.UnorderedListADT;
import ed.exceptions.ElementNotFoundException;
import ed.exceptions.EmptyCollectionException;
import ed.exceptions.NonComparableException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author Francisco Spínola
 * @author João Pereira
 */
public class TUI {
    public TUI(Map map) throws ElementNotFoundException, FileNotFoundException, 
            EmptyCollectionException, NonComparableException, IOException {
        InstanceTUI instance = new InstanceTUI(this, map.getPoints(), map.getEntranceRoom());
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
                    short result = map.menuModoNormal(instance);
                    if (result == 0)
                        this.Screen_DeadInfo();
                    else if (result == 1)
                        this.Screen_VictoryInfo(instance);
                    instance.highscore(map.getName());
                    instance.reset(map.getEntranceRoom(), map.getPoints());
                    break;
                case 2:
                    this.Screen_SimulationGameMode();
                    instance.reset(map.getEntranceRoom(), map.getPoints());
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

    protected void Screen_PlayerName() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                       Nome do jogador                           |");
        System.out.println("|_________________________________________________________________|");
        System.out.println("Nome do jogador: ");
    }

    private void Screen_LoadMap() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                 Mapa carregado com sucesso!                     |");
        System.out.println("|_________________________________________________________________|");
    }

    private void Screen_SimulationGameMode() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                       Modo Simulação                            |");
        System.out.println("|_________________________________________________________________|");
    }

    private void Screen_NormalGameMode() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                         Modo Normal                             |");
        System.out.println("|_________________________________________________________________|");
    }

    private void Screen_MapRanking() throws FileNotFoundException, IOException, NonComparableException {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|                       Classficação do mapa                       |");
        System.out.println("|__________________________________________________________________|");
        UnorderedListADT rankings = new Ficheiros().loadPlayersRankingInfo();
        Iterator<String> iter = rankings.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        pressEnter();
    }

    protected void Screen_PlayerInfo() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                   Informação do jogador                         |");
        System.out.println("|_________________________________________________________________|");
    }

    protected void Screen_DeadInfo() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|            Perdeu as vidas todas! Game Over                     | ");
        System.out.println("|__________________________________________________________________|");
        pressEnter();
    }

    protected void Screen_VictoryInfo(InstanceTUI instance) {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|                  Parabéns chegou à saída!                        | ");
        System.out.println("|__________________________________________________________________|");
        System.out.println("Pontos: " + instance.getScore());
        pressEnter();
    }
    
    private void pressEnter() {
        System.out.println("\nPressione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }
}