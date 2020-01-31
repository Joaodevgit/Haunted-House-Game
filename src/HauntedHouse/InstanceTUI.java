package HauntedHouse;

import java.util.Scanner;

/**
 *
 * @author João Pereira
 * @author Francisco Spínola
 */
public class InstanceTUI implements Comparable<InstanceTUI> {

    public final short EASY = 1;
    public final short NORMAL = 2;
    public final short HARD = 3;
    private final String name;

    private long score;
    private short level;
    private Room pos;
    private String mapName;

    public InstanceTUI(String name, long score, String mapName) {
        this.name = name;
        this.score = score;
        this.mapName = mapName;
    }

    public InstanceTUI(TUI tui, long score, Room pos) {
        this.name = this.getPlayerName(tui);
        this.changeDifficultyChoice(tui);
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

    protected void reset(Room entrance, long points) {
        this.pos = entrance;
        this.score = points;
    }

    @Override
    public int compareTo(InstanceTUI o) {
        if (this.score < o.score) {
            return 1;
        } else if (this.score == o.score) {
            return 0;
        } else {
            return -1;
        }
    }
}
