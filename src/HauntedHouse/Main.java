/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HauntedHouse;

import java.util.Scanner;

/**
 *
 * @author Utilizador
 */
public class Main {

    public static void main(String[] args) {

        MenuImages outputImages = new MenuImages();
        Scanner sc = new Scanner(System.in);
        int o;
        // Ecrã de escolha da dificuldade
        //outputImages.Screen_DifficultyChoice();

        do {
            do {
                outputImages.Screen_MainMenu();
                System.out.println("Introduza opção: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Opção com formato inválido!");
                    sc.next(); // this is important!
                }
                o = sc.nextInt();
            } while (o < 0 || o > 3);
            switch (o) {
                case 1:
                    outputImages.Screen_NormalGameMode();
                    break;
                case 2:
                    outputImages.Screen_SimulationGameMode();
                    break;
                case 3:
                    outputImages.Screen_MapRanking();
                    break;
                default:
                    System.out.println("Escolheu sair do programa");
            }
        } while (o != 0);
    }
}
