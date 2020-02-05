package HauntedHouse;

import java.util.Scanner;

/**
 * Instância atual do programa.
 *
 * @author João Pereira
 * @author Francisco Spínola
 * @deprecated Deixou de ser utiilizado desde que foi criada a <code>GUI</code>, que consequentemente, originou
 * a classe <code>Instance</code>.
 * @see Instance
 */
public class InstanceTUI extends Instance {
    
    /**
     * Criar uma instância, passando por argumento o nome e pontos do jogador e o mapa onde joga/jogou.
     * Este método construtor foi criado para ser usado paenas pela classe Files.
     * 
     * @param name nome do jogador
     * @param score pontos do jogador
     * @param mapName nome do mapa
     * @see Files
     */
    public InstanceTUI(String name, long score, String mapName) {
        super(name, score, mapName);
    }

    /**
     * Cria uma instância, passando a interface textual, pontos e posição atual do jogador.
     * 
     * @param tui interface textual
     * @param score pontos do jogador
     * @param pos posição atual do jogador
     */
    public InstanceTUI(TUI tui, long score, Room pos) {
        super("", score, pos);
        super.name = this.getPlayerName(tui);
        this.changeDifficultyChoice(tui);
    }
    
    /**
     * Método responsável por pedir ao utilizador que insira o nome.
     * 
     * @param tui interface textual
     * @return nome do jogador
     */
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
     * @param tui <i>Text User Interface</i>
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
