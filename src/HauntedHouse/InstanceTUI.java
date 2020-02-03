package HauntedHouse;

import java.util.Scanner;

/**
 *
 * @author João Pereira
 * @author Francisco Spínola
 */
public class InstanceTUI extends Instance {
    public InstanceTUI(String name, long score, String mapName) {
        super(name, score, mapName);
    }

    public InstanceTUI(TUI tui, long score, Room pos) {
        super("", score, pos);
        super.name = this.getPlayerName(tui);
        this.changeDifficultyChoice(tui);
    }
    
    private String getPlayerName(TUI tui) {
        Scanner sc = new Scanner(System.in);
        tui.Screen_PlayerName();
        String name = sc.nextLine();
        if (!name.matches("^[a-zA-Z0-9 ]+$")) {
            System.out.println("Nome inserido inválido. A sair...");
            System.exit(0);
        }
        return name;
    }

    /**
     * Método responsável por definir a dificuldade inicial
     *
     * @param tui
     */
    protected void changeDifficultyChoice(TUI tui) {
        Scanner sc = new Scanner(System.in);
        int opt;
        do {
            tui.Screen_DifficultyChoice();
            System.out.println("Escolha a dificuldade: ");
            while (!sc.hasNextInt()) {
                System.out.println("Opção de escolha com formato inválido!");
                sc.next();
            }
            opt = sc.nextInt();
        } while (opt < 1 || opt > 3);
        switch (opt) {
            case 1:
                this.level = EASY;
                System.out.println("Escolheu a dificuldade Básico!");
                break;
            case 2:
                this.level = NORMAL;
                System.out.println("Escolheu a dificuldade Normal!");
                break;
            case 3:
                this.level = HARD;
                System.out.println("Escolheu a dificuldade Difícil!");
                break;
            default:
                this.level = NORMAL;
        }
    }
}
