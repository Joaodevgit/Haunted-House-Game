package HauntedHouse;

import ed.exceptions.ElementNotFoundException;
import java.util.Scanner;

/**
 * @author Francisco Spínola
 * @author João Pereira
 */
public class TUI {

    public TUI() {
    }

    public TUI(Map map) throws ElementNotFoundException {
        Instance instance = new Instance(this, map.getPoints(), map.getEntranceRoom());
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
                    map.menuModoNormal(map.getEntranceRoom(), instance);
                    break;
                case 2:
                    this.Screen_SimulationGameMode();
                    break;
                case 3:
                    this.Screen_MapRanking();
                    break;
                case 4:
                    instance.changeDifficultyChoice(this);
                    break;
                default:
                    System.out.println("Saiu do jogo!");
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

    private void Screen_MapRanking() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|       Classficação do mapa (Por pontos de vida restantes)        | ");
        System.out.println("|__________________________________________________________________|");
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
        System.out.println("|            Perdeu as vidas todas ! Game Over                     | ");
        System.out.println("|__________________________________________________________________|");
    }

    protected void Screen_VictoryInfo() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|                  Parabéns chegou à saída!                        | ");
        System.out.println("|__________________________________________________________________|");
    }
}
