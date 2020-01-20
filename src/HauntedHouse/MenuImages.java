/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HauntedHouse;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Utilizador
 */
public class MenuImages {

    protected void Screen_MainMenu() {
        System.out.println(" __________________________________________________________________");
        System.out.println("|                                                                  |");
        System.out.println("|                     Casa Assombrada                              |");
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
        System.out.println("|                  Dificuldade do jogo                             |");
        System.out.println("|                                                                  |");
        System.out.println("|     1 -> Básico                                                  |");
        System.out.println("|     2 -> Normal                                                  |");
        System.out.println("|     3 -> Díficil                                                 |");
        System.out.println("|                                                                  |");
        System.out.println("|     0 -> Sair                                                    |");
        System.out.println("|__________________________________________________________________|");
    }

    /**
     * Ver onde colocar este método (obter a dificuldade pedida pelo utilizador)
     *
     * @return o valor da dificuldade (1 - Básico , 2 - Normal , 3 - Díficil)
     */
    public int getDifficultyChoice(int currentDifficulty) {
        Scanner sc = new Scanner(System.in);
        int opt;
        int choice = currentDifficulty;
        do {
            do {
                Screen_DifficultyChoice();
                System.out.println("Introduza opção: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Opção com formato inválido!");
                    sc.next();
                }
                opt = sc.nextInt();
            } while (opt < 0 || opt > 3);
            switch (opt) {
                case 1:
                    choice = 1;
                    break;
                case 2:
                    choice = 2;
                    break;
                case 3:
                    choice = 3;
                    break;
                default:
                    System.out.println("Escolheu voltar ao menu principal "
                            + "(Dificuldade atual mantida)");
            }
            return choice;
        } while (opt != 0);
    }

    protected void Screen_LoadMap() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                 Mapa carregado com sucesso!                     |");
        System.out.println("|_________________________________________________________________|");
    }

    protected void Screen_SimulationGameMode() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                       Modo Simulação                            |");
        System.out.println("|_________________________________________________________________|");
    }

    protected void Screen_NormalGameMode() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("|                         Modo Normal                             |");
        System.out.println("|_________________________________________________________________|");
    }

    protected void Screen_MapRanking() {
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