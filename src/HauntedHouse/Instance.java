package HauntedHouse;

import ed.adt.OrderedListADT;
import ed.exceptions.EmptyCollectionException;
import ed.exceptions.NonComparableException;
import ed.util.ArrayOrderedList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author João Pereira
 * @author Francisco Spínola
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
    
    protected void highscore(String mapName) throws FileNotFoundException, EmptyCollectionException, 
            NonComparableException, IOException {
        OrderedListADT<String> list = new Ficheiros().loadPlayersRankingInfo();
        if (!list.isEmpty() || list.size() < 10) {
            int index = 18;
            String lastScore = "";
            while (index < list.last().length() && (list.last().charAt(index) < 48 || list.last().charAt(index) > 57)) index++;
            while (list.last().charAt(index) >= 48 && list.last().charAt(index) <= 57) {
                lastScore += list.last().charAt(index);
                index++;
            }
            if (this.score > Integer.parseInt(lastScore)) {
                new Ficheiros().writePlayersRankingInfo(this, mapName);
            }
        } else {
            OrderedListADT ordered = new ArrayOrderedList();
            ordered.add(this);
            new Ficheiros().writePlayersRankingInfo(this, mapName);
        }
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