package HauntedHouse;

import java.util.Scanner;

/**
 *
 * @author Francisco Spínola
 * @author João Tiago Pereira
 */
public class TUI {

    public TUI() {
        Scanner sc = new Scanner(System.in);
        int o;
        int dc = getInitialDifficultyChoice();

        String playerName = getPlayerName();
        do {
            do {
                this.Screen_MainMenu(playerName, getDifficulty(dc));
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
                    break;
                case 2:
                    this.Screen_SimulationGameMode();
                    break;
                case 3:
                    this.Screen_MapRanking();
                    break;
                case 4:
                    dc = this.changeDifficultyChoice(dc);// Menu responsável por alterar a dificuldade returnando 
                    break;
                default:
                    break;
            }
        } while (o != 0);
    }

    private void Screen_MainMenu(String name, String difficulty) {
        System.out.println(" __________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|                     Casa Assombrada                              |");
        System.out.println("|                                                                  |");
        System.out.println("|                  Bem vindo " + name + " !                         |");
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

    private void Screen_DifficultyChoice() {
        System.out.println(" __________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|                       Dificuldade do jogo                        |");
        System.out.println("|                                                                  |");
        System.out.println("|         1 -> Básico                                              |");
        System.out.println("|         2 -> Normal                                              |");
        System.out.println("|         3 -> Díficil                                             |");
        System.out.println("|                                                                  |");
        System.out.println("|         0 -> Sair                                                |");
        System.out.println("|__________________________________________________________________|");
    }

    /**
     * Método responsável por definir a dificuldade inicial
     *
     * @return o valor da dificuldade (1 - Básico , 2 - Normal , 3 - Díficil,0 -
     * Termina o programa)
     */
    private int getInitialDifficultyChoice() {
        Scanner sc = new Scanner(System.in);
        int opt;
        int choice;
        do {
            do {
                Screen_DifficultyChoice();
                System.out.println("Escolha a dificuldade: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Opção de escolha com formato inválido!");
                    sc.next();
                }
                opt = sc.nextInt();
            } while (opt < 0 || opt > 3);
            switch (opt) {
                case 1:
                    choice = 1;
                    System.out.println("Escolheu a dificuldade Básico!");
                    break;
                case 2:
                    choice = 2;
                    System.out.println("Escolheu a dificuldade Normal!");
                    break;
                case 3:
                    choice = 3;
                    System.out.println("Escolheu a dificuldade Difícil!");
                    break;
                default:
                    choice = 0;
                    System.out.println("Escolheu sair do programa!");
                    System.exit(0);
            }
            return choice;
        } while (opt != 0);
    }

    /**
     * Método responsável por alterar (ou não) a dificuldade inicial
     *
     * @return o valor da dificuldade (1 - Básico , 2 - Normal , 3 - Díficil, 0
     * - Mantém a dificuldade que estava)
     */
    private int changeDifficultyChoice(int currentDifficulty) {
        Scanner sc = new Scanner(System.in);
        int opt;
        int choice = currentDifficulty;
        do {
            do {
                Screen_DifficultyChoice();
                System.out.println("Escolha a dificuldade: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Opção de escolha com formato inválido!");
                    sc.next();
                }
                opt = sc.nextInt();
            } while (opt < 0 || opt > 3);
            switch (opt) {
                case 1:
                    choice = 1;
                    System.out.println("Escolheu a dificuldade Básico!");
                    break;
                case 2:
                    choice = 2;
                    System.out.println("Escolheu a dificuldade Normal!");
                    break;
                case 3:
                    choice = 3;
                    System.out.println("Escolheu a dificuldade Difícil!");
                    break;
                default:
                    choice = currentDifficulty;
                    System.out.println("Escolheu voltar ao menu principal! (Dificuldade mantida)");
            }
            return choice;
        } while (opt != 0);
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

    private void Screen_PlayerName() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                       Nome do jogador                           |");
        System.out.println("|_________________________________________________________________|");
        System.out.println("Nome do jogador: ");
    }

    private String getPlayerName() {
        Scanner sc = new Scanner(System.in);
        Screen_PlayerName();
        String name = sc.nextLine();
        return name;
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

    private void Screen_PlayerInfo() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                   Informação do jogador                         |");
        System.out.println("|_________________________________________________________________|");
    }

    private void Screen_DeadInfo() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|            Perdeu as vidas todas ! Game Over                     | ");
        System.out.println("|__________________________________________________________________|");
    }

    private void Screen_VictoryInfo() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|                  Parabéns chegou à saída!                        | ");
        System.out.println("|__________________________________________________________________|");
    }

}
