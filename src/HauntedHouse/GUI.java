package HauntedHouse;

import ed.adt.OrderedListADT;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * <i>Graphical User Interface</i>
 *
 * @author Francisco Spínola
 * @author João Pereira
 */
public class GUI implements ActionListener {

    private boolean sound;
    private Map<Room> map;
    private final JButton easy;
    private final JButton normal;
    private final JButton hard;
    private JButton normalMode;
    private JButton simulationMode;
    private JButton close;
    private JButton highscore;
    private JButton levelChange;
    private JButton ok;
    private JButton soundB;
    private JTextField playerName = new JTextField(3);
    private JFrame level;
    private JFrame menu;
    private JFrame player;
    private Clip clip;

    //private OrderedListADT<Player> ranking;
    /**
     * <p>
     * <strong>Estado do programa</strong></p>
     * <p>
     * 0: Escolha do nível de dificuldade</p>
     * <p>
     * 1: Escolha do nome do jogador</p>
     * <p>
     * 2: Menu principal</p>
     */
    private short status;

    /**
     * Método construtor para a GUI. Chama o método responsável pela escolha do
     * nível de dificuldade.
     *
     * @param map mapa do jogo
     */
    public GUI(Map map) throws IOException {
        this.sound = true;
        this.easy = new JButton(new ImageIcon(ImageIO.read(new File("lib/easy.png"))));
        this.normal = new JButton(new ImageIcon(ImageIO.read(new File("lib/normal2.png"))));
        this.hard = new JButton(new ImageIcon(ImageIO.read(new File("lib/hard.png"))));
        this.map = map;
        this.status = 0;
        this.levelChanger();
    }

    /**
     * Método responsável por alterar o nível de dificuldade do jogo.
     */
    private void levelChanger() {
        //Creating the frame and panels
        this.level = new JFrame("Dificuldade");
        JPanel panel = new JPanel();
        JPanel text = new JPanel();
        JPanel main = new JPanel(new BorderLayout());
        this.easy.setBorderPainted(false);
        this.easy.setContentAreaFilled(false);
        this.easy.setMargin(new Insets(0, 0, 0, 0));
        this.easy.setOpaque(false);
        this.normal.setBorderPainted(false);
        this.normal.setContentAreaFilled(false);
        this.normal.setMargin(new Insets(0, 0, 0, 0));
        this.normal.setOpaque(false);
        this.hard.setBorderPainted(false);
        this.hard.setContentAreaFilled(false);
        this.hard.setMargin(new Insets(0, 0, 0, 0));
        this.hard.setOpaque(false);
        JLabel dif = new JLabel("DIFICULDADE");
        dif.setFont(new Font("font", Font.ITALIC, 20));
        text.add(dif);

        //Adding content to the frame
        panel.setLayout(new GridLayout(1, 3, 5, 10));
        panel.add(this.easy);
        panel.add(this.normal);
        panel.add(this.hard);
        panel.setBackground(Color.BLACK);
        text.setBackground(Color.BLACK);
        main.add(text, BorderLayout.NORTH);
        main.add(panel, BorderLayout.CENTER);
        this.level.getContentPane().add(main);

        //Configuring the frame
        this.level.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.level.setResizable(false);
        this.level.setUndecorated(true);
        this.level.setSize(300, 110);
        this.level.setLocationRelativeTo(null);
        this.level.setVisible(true);

        //Adding the listeners
        this.easy.addActionListener(this);
        this.normal.addActionListener(this);
        this.hard.addActionListener(this);
    }

    /**
     * Altera o estado da música (ligada/desligada).
     */
    private void sound() {
        if (this.sound) {
            this.sound = false;
            this.clip.stop();
        } else {
            this.sound = true;
            this.clip.start();
        }
    }

    /**
     * Escolha do nome do jogador.
     */
    private void playerName() throws IOException {
        //Creating the frame, panels and button
        this.player = new JFrame("Jogador");
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel main = new JPanel();
        this.playerName = new JTextField(21);
        this.ok = new JButton(new ImageIcon(ImageIO.read(new File("lib/submit.png"))));
        this.ok.setBorderPainted(false);
        this.ok.setContentAreaFilled(false);
        this.ok.setMargin(new Insets(0, 0, 0, 0));
        this.ok.setOpaque(false);
        JLabel text = new JLabel("NOME DO JOGADOR");
        text.setFont(new Font("font", Font.ITALIC, 18));

        //Adding content to the frame
        main.setLayout(new BorderLayout());
        panel.add(this.playerName);
        panel2.add(this.ok, BorderLayout.CENTER);
        panel.setBackground(Color.BLACK);
        panel2.setBackground(Color.BLACK);
        main.add(text, BorderLayout.NORTH);
        main.add(panel, BorderLayout.CENTER);
        main.add(panel2, BorderLayout.SOUTH);
        main.setBackground(Color.BLACK);
        this.player.getContentPane().add(main);

        //Configuring the frame
        this.player.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.player.setResizable(false);
        this.player.setUndecorated(true);
        this.player.setSize(250, 140);
        this.player.setLocationRelativeTo(null);
        this.player.setVisible(true);

        //Adding the listener
        this.ok.addActionListener(this);
    }

    /**
     * Menu principal.
     */
    private void mainMenu() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        //Creating the frame
        this.menu = new JFrame("Casa Assombrada");
        this.close = new JButton(new ImageIcon(ImageIO.read(new File("lib/close.png"))));
        this.highscore = new JButton(new ImageIcon(ImageIO.read(new File("lib/highscore.png"))));
        this.levelChange = new JButton(new ImageIcon(ImageIO.read(new File("lib/level.png"))));
        this.normalMode = new JButton(new ImageIcon(ImageIO.read(new File("lib/normal.png"))));
        this.simulationMode = new JButton(new ImageIcon(ImageIO.read(new File("lib/simulation.png"))));
        this.soundB = new JButton(new ImageIcon(ImageIO.read(new File("lib/sound.png"))));

        //Adding content to the frame
        this.menu.add(new BackgroundPane());

        //Configuring the frame
        this.menu.setUndecorated(true);
        this.menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.menu.setResizable(false);
        this.menu.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDisplayMode().getWidth(), GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
                        getDisplayMode().getHeight());
        this.menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.menu.setLocationRelativeTo(null);
        this.menu.setVisible(true);

        this.clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("lib/Fable-Lychfield-Cemetary.wav"));
        this.clip.open(inputStream);
        if (this.sound) {
            this.clip.start();
        }

        //Adding the listeners
        this.close.addActionListener(this);
        this.normalMode.addActionListener(this);
        this.simulationMode.addActionListener(this);
        this.levelChange.addActionListener(this);
        this.highscore.addActionListener(this);
        this.soundB.addActionListener(this);
    }

    /**
     * Classe que estrutura o menu principal
     */
    class BackgroundPane extends JPanel {

        /**
         * Método construtor que estrutura o menu principal do jogo
         */
        public BackgroundPane() throws IOException {
            setLayout(new BorderLayout());
            JLabel label = new JLabel(new ImageIcon(
                    ImageIO.read(new File("lib/background.jpg")).getScaledInstance(GraphicsEnvironment.
                            getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth(),
                            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
                                    getDisplayMode().getHeight(), Image.SCALE_DEFAULT)));

            add(label);

            JPanel optionsPane = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.NORTH;

            optionsPane.setBackground(Color.BLACK);
            optionsPane.add(this.makeButton(GUI.this.normalMode), gbc);
            optionsPane.add(this.makeButton(GUI.this.simulationMode), gbc);
            optionsPane.add(this.makeButton(GUI.this.highscore), gbc);
            optionsPane.add(this.makeButton(GUI.this.levelChange), gbc);
            optionsPane.add(this.makeButton(GUI.this.soundB), gbc);
            optionsPane.add(this.makeButton(GUI.this.close), gbc);

            add(optionsPane, BorderLayout.LINE_END);
        }

        /**
         * Método que altera o formato visual de um <i>JButton</i>.
         *
         * @param btn botão a ser alterado
         * @return botão alterado
         */
        private JButton makeButton(JButton btn) {
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setMargin(new Insets(0, 0, 0, 0));
            btn.setOpaque(false);
            return btn;
        }
    }

    /**
     * Jogo em Modo Simulação.
     */
    private void simulation() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        this.clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("lib/gamemusic.wav"));
        this.clip.open(inputStream);
        if (this.sound) {
            this.clip.start();
        }
    }

    /**
     * Jogo em Modo Normal.
     */
    private void normal() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        this.clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("lib/gamemusic.wav"));
        this.clip.open(inputStream);
        if (this.sound) {
            this.clip.start();
        }
    }

    /**
     * Mostra as classificações do jogo.
     */
    private void highscore() {
        try {
            String[] rankingArray = loadRankingFile();
            String rankList = "";
            for (int j = 0; j < rankingArray.length; j++) {
                String playerInfo = rankingArray[j];
                rankList += playerInfo + "\n";
            }
            JOptionPane.showMessageDialog(null, rankList, "Classificação TOP10", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writePlayersRankingInfo(String path, OrderedListADT<Instance> playersRanking,String mapName) throws IOException {

        File file = new File(path);
        Iterator<Instance> iter = playersRanking.iterator();
        int i = 1;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            while (iter.hasNext()) {
                Instance instance = iter.next();
                writer.write(i + "º " + " - Jogador: " + instance.getName() + " - Pontos: " + instance.getScore()
                        + " - Mapa: " + mapName);
                writer.newLine();
                i++;
            }
            writer.flush();
            writer.close();
        }
    }

    public String[] loadRankingFile() throws FileNotFoundException {
        File file = new File("ranking.txt");
        String rankingArray[];
        if (lineRankingFileCounter() < 10) {
            rankingArray = new String[lineRankingFileCounter()];
        } else {
            rankingArray = new String[10];
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        int i = 0;
        String st;
        try {
            while ((st = br.readLine()) != null) {
                rankingArray[i] = st;
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Ficheiros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rankingArray;
    }

    public int lineRankingFileCounter() throws FileNotFoundException {
        File file = new File("ranking.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int i = 0;
        try {
            while (br.readLine() != null) {
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Ficheiros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    /**
     * Método responsável por definir o que se sucede aquando determinada ação é
     * executada.
     *
     * @param ev evento
     */
    @Override
    public void actionPerformed(ActionEvent ev) {
        String[] ok = {"OK"};
        if (ev.getSource().equals(this.easy)) {
            JOptionPane.showOptionDialog(this.level, "Dificuldade:\n" + "Básico", "Aviso",
                    JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ok, ok[0]);
            this.level.dispose();
            if (this.status == 0) {
                this.status = 1;
                try {
                    this.playerName();
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (ev.getSource().equals(this.normal)) {
            JOptionPane.showOptionDialog(this.level, "Dificuldade:\n" + "Normal", "Aviso",
                    JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ok, ok[0]);
            this.level.dispose();
            if (this.status == 0) {
                this.status = 1;
                try {
                    this.playerName();
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (ev.getSource().equals(this.hard)) {
            JOptionPane.showOptionDialog(this.level, "Dificuldade:\n" + "Difícil", "Aviso",
                    JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ok, ok[0]);
            this.level.dispose();
            if (this.status == 0) {
                this.status = 1;
                try {
                    this.playerName();
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (ev.getSource().equals(this.ok)) {
            if (this.playerName.getText().equals("")) {
                JOptionPane.showOptionDialog(this.player, "Campo vazio!", "Aviso",
                        JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, ok, ok[0]);
            } else if (!this.playerName.getText().matches("^[a-zA-Z0-9 ]+$")) {
                JOptionPane.showOptionDialog(this.player, "Insira apenas carateres alfanuméricos!", "Aviso",
                        JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, ok, ok[0]);
            } else {
                this.player.dispose();
                if (this.status == 1) {
                    this.status = 2;
                    try {
                        this.mainMenu();
                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else if (ev.getSource().equals(this.close)) {
            System.exit(0);
        } else if (ev.getSource().equals(this.levelChange)) {
            this.levelChanger();
        } else if (ev.getSource().equals(this.highscore)) {
            this.highscore();
        } else if (ev.getSource().equals(this.normalMode)) {
            this.clip.stop();
            try {
                this.normal();
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (ev.getSource().equals(this.simulationMode)) {
            this.clip.stop();
            try {
                this.simulation();
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (ev.getSource().equals(this.soundB)) {
            sound();
        }
    }
}
