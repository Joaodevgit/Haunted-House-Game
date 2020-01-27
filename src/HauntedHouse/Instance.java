package HauntedHouse;

import java.util.Scanner;

/**
 *
 * @author João Pereira
 */
public class Instance implements Comparable<Instance> {

    public final int EASY = 1;
    public final int NORMAL = 2;
    public final int HARD = 3;
    private final String name;

    private long score;
    private short level;
    private Room pos;
    
    public Instance(TUI tui, long score, Room pos) {
        this.name = this.getPlayerName(tui);
        this.level = this.changeDifficultyChoice(tui);
        this.score = score;
        this.pos = pos;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }
    
    public Room getPos() {
        return pos;
    }

    public void setPos(Room pos) {
        this.pos = pos;
    }
    
    private String getPlayerName(TUI tui) {
        Scanner sc = new Scanner(System.in);
        tui.Screen_PlayerName();
        String name = sc.nextLine();
        return name;
    }

    /**
     * Método responsável por definir a dificuldade inicial
     *
     * @param tui
     * @return o valor da dificuldade (1 - Básico , 2 - Normal , 3 - Díficil)
     */
    protected short changeDifficultyChoice(TUI tui) {
        Scanner sc = new Scanner(System.in);
        int opt;
        short choice;
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
                choice = EASY;
                System.out.println("Escolheu a dificuldade Básico!");
                break;
            case 2:
                choice = NORMAL;
                System.out.println("Escolheu a dificuldade Normal!");
                break;
            case 3:
                choice = HARD;
                System.out.println("Escolheu a dificuldade Difícil!");
                break;
            default:
                choice = NORMAL;
        }
        return choice;
    }

    @Override
    public int compareTo(Instance o) {
        if (this.score < o.score) {
            return 1;
        } else if (this.score == o.score) {
            return 0;
        } else {
            return -1;
        }
    }
}
