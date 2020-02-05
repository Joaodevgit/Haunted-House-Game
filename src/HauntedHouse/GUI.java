package HauntedHouse;

import ed.adt.OrderedListADT;
import ed.adt.UnorderedListADT;
import ed.exceptions.ElementNotFoundException;
import ed.exceptions.EmptyCollectionException;
import ed.exceptions.NonAvailablePath;
import ed.exceptions.NonComparableException;
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
import java.io.File;
import java.io.FileNotFoundException;
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
import javax.swing.SwingConstants;

/**
 * <i>Graphical User Interface</i>
 *
 * @author Francisco Spínola
 */
public class GUI implements ActionListener {

    private boolean blocked;
    private Instance instance;
    private Files files;
    private boolean sound;
    private Map<Room> map;
    private JLabel points;
    private JLabel pos;
    private final JButton easy;
    private final JButton normal;
    private final JButton hard;
    private JButton okSimulation;
    private JButton normalMode;
    private JButton simulationMode;
    private JButton close;
    private JButton highscore;
    private JButton levelChange;
    private JButton ok;
    private JButton okHighscore;
    private JButton okDefeat;
    private JButton okVictory;
    private JButton soundB;
    private JButton menuB;
    private JButton okGhost;
    private JTextField playerName = new JTextField(3);
    private JFrame level;
    private JFrame game;
    private JFrame player;
    private JFrame highscoreFrame;
    private JFrame ghostFrame;
    private JFrame simulationFrame;
    private JFrame defeatFrame;
    private JFrame victoryFrame;
    private GamePane pane;
    private Clip clip;

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
    public GUI(Map map) throws IOException, ElementNotFoundException {
        this.blocked = false;
        this.instance = new Instance();
        this.instance.setPos((Room)map.getEntranceRoom());
        this.instance.setScore(map.getPoints());
        this.files = new Files();
        this.sound = true;
        this.easy = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/easy.png"))));
        this.normal = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/normal2.png"))));
        this.hard = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/hard.png"))));
        this.map = map;
        this.status = 0;
        this.levelChanger();
    }

    /**
     * Método responsável por alterar o nível de dificuldade do jogo.
     */
    private void levelChanger() {
        this.blocked = true;
        
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
        this.ok = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/submit.png"))));
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
        this.game = new JFrame("Casa Assombrada");
        this.close = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/close.png"))));
        this.highscore = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/highscore.png"))));
        this.levelChange = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/level.png"))));
        this.normalMode = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/normal.png"))));
        this.simulationMode = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/simulation.png"))));
        this.soundB = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/sound.png"))));

        //Adding content to the frame
        this.game.add(new BackgroundPane());

        //Configuring the frame
        this.game.setUndecorated(true);
        this.game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.game.setResizable(false);
        this.game.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDisplayMode().getWidth(), GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
                        getDisplayMode().getHeight());
        this.game.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.game.setLocationRelativeTo(null);
        this.game.setVisible(true);

        this.clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("lib/sounds/Fable-Lychfield-Cemetary.wav"));
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
                    ImageIO.read(new File("lib/images/background.jpg")).getScaledInstance(GraphicsEnvironment.
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
     * <p>Apresentação das classificações do melhores jogadores, em termos de pontuação, 
     * de acordo com o nível de dificuldade atual. </p>
     * <p>Este método carrega, de acordo com o nível de dificuldade da instância atual, 
     * o ficheiro que contem as classificações a que corresponde os jogadores para esse nível.</p>
     * 
     * @throws IOException Erro na leitura do ficheiro de classificações
     * @throws NonComparableException Objeto não comparável
     */
    public void highscore() throws IOException, NonComparableException {
        this.blocked = true;
        
        this.highscoreFrame = new JFrame("Classificações");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel1.setLayout(new GridLayout(0, 4));
        panel3.setLayout(new BorderLayout(10, 10));
        
        this.highscoreFrame.setSize(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth()/* - 500*/, 
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight()/* - 200*/
        );
        JLabel label = new JLabel(new ImageIcon(ImageIO.read(new File("lib/images/TOP25.jpg")).
                            getScaledInstance(this.highscoreFrame.getWidth(), this.highscoreFrame.getHeight(), Image.SCALE_DEFAULT)));

        //Button config
        this.okHighscore = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/ok.png"))));
        this.okHighscore.setBorderPainted(false);
        this.okHighscore.setContentAreaFilled(false);
        this.okHighscore.setMargin(new Insets(0, 0, 0, 0));
        this.okHighscore.setOpaque(false);
        
        //Add content to the frame
        int i = 0;
        OrderedListADT<Instance> list = this.files.loadByDifficulty(this.instance.getLevel());
        Iterator<Instance> iter = list.iterator();
        panel1.setBackground(Color.BLACK);
        panel1.add(new JLabel("<html><strong><font color=\"white\">COLOCAÇÃO</font></strong></html>"));
        panel1.add(new JLabel("<html><strong><font color=\"white\">NOME</font></strong></html>"));
        panel1.add(new JLabel("<html><strong><font color=\"white\">PONTOS</font></strong></html>"));
        panel1.add(new JLabel("<html><strong><font color=\"white\">MAPA</font></strong></html>"));
        while (iter.hasNext() && i < 25) {
            Instance instanceTemp = iter.next();
            int pos = ++i;
            panel1.add(new JLabel("<html><font color=\"white\">" + pos + "º " + "</font></html>"));
            panel1.add(new JLabel("<html><font color=\"white\">" + instanceTemp.getName() + "</font></html>"));
            panel1.add(new JLabel("<html><font color=\"white\">" + String.valueOf(instanceTemp.getScore() + "</font></html>")));
            panel1.add(new JLabel("<html><font color=\"white\">" + instanceTemp.getMapName() + "</font></html>"));
        }
        panel2.setBackground(Color.BLACK);
        panel2.add(this.okHighscore, BorderLayout.SOUTH);
        String levelS;
        switch (this.instance.getLevel()) {
            case 1:
                levelS = "Básico";
                break;
            case 2:
                levelS = "Normal";
                break;
            case 3:
                levelS = "Difícil";
                break;
            default:
                levelS = "Normal";
        }
        panel3.add(new JLabel("<html><font color=\"red\">DIFICULDADE: " + levelS + "</font></html>"), BorderLayout.EAST);
        panel3.add(panel1, BorderLayout.SOUTH);
        panel3.setBackground(Color.BLACK);
        this.highscoreFrame.add(label);
        this.highscoreFrame.getContentPane().add(panel3, BorderLayout.NORTH);
        this.highscoreFrame.getContentPane().add(panel2, BorderLayout.SOUTH);
        
        //Frame config
        this.highscoreFrame.setLocationRelativeTo(null);
        this.highscoreFrame.setUndecorated(true);
        this.highscoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.highscoreFrame.setResizable(false);
        this.highscoreFrame.setVisible(true);
        
        //Action listeners
        this.okHighscore.addActionListener(this);
    }
    
    /**
     * Jogo em Modo Simulação.
     */
    private void simulation() throws ElementNotFoundException, NonAvailablePath, EmptyCollectionException, IOException {
        this.blocked = true;

        // Start the variables
        this.simulationFrame = new JFrame("Simulação");
        this.okSimulation = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/ok.png"))));
        this.okSimulation.setBorderPainted(false);
        this.okSimulation.setContentAreaFilled(false);
        this.okSimulation.setMargin(new Insets(0, 0, 0, 0));
        this.okSimulation.setOpaque(false);
        JPanel geral = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        
        int cost = (int) this.map.shortestPathWeight(this.map.getEntranceRoom(), this.map.getBestExitRoom()) * this.instance.getLevel();
        JLabel label1 = new JLabel("<html><font size=\"50\" color=\"white\">MODO SIMULAÇÃO</font></html>", SwingConstants.CENTER);
        geral.setLayout(new BorderLayout(50, 50));
        
        //Layout config and Add content to panels
        panel1.setLayout(new BorderLayout());
        panel1.add(label1, BorderLayout.NORTH);
        panel1.setBackground(Color.BLACK);
        panel3.setLayout(new BorderLayout());

        Iterator<Room> iter = this.map.iteratorShortestPath(this.map.getEntranceRoom(), this.map.getBestExitRoom());
        String resultLifePoints = "O jogador perderia " + cost + " pontos de vida.</font></html>";
        String resultPath = "<html><h1><font color=\"white\">";
        while (iter.hasNext()) {
            Room res = iter.next();
            if (iter.hasNext()) {
                resultPath += res.getName().toUpperCase() + " --> ";
            } else {
                resultPath += res.getName().toUpperCase();
            }
        }
        resultPath += "<br/><br/>";
        String def = resultPath.concat(resultLifePoints);
        JLabel resultPointsLabel = new JLabel(def, SwingConstants.CENTER);
        resultPointsLabel.setAlignmentY(java.awt.Component.CENTER_ALIGNMENT);
        
        panel3.add(resultPointsLabel);
        panel3.setLocation(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth(),
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight()
        );
        panel3.setBackground(Color.BLACK);
        
        panel2.setBackground(Color.BLACK);
        panel2.add(this.okSimulation, BorderLayout.SOUTH);
        
        geral.add(panel1, BorderLayout.NORTH);
        geral.add(panel2, BorderLayout.SOUTH);
        geral.add(panel3, BorderLayout.CENTER);
        geral.setBackground(Color.BLACK);

        //Add content to the frame
        this.simulationFrame.getContentPane().add(geral, BorderLayout.CENTER);

        //Frame config
        this.simulationFrame.setSize(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth(),
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight()
        );
        this.simulationFrame.setLocationRelativeTo(null);
        this.simulationFrame.setUndecorated(true);
        this.simulationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.simulationFrame.setResizable(false);
        this.simulationFrame.setVisible(true);

        this.okSimulation.addActionListener(this);
    }
    
    /**
     * Apresentação do ecrã de derrota.
     * 
     * @throws IOException erro na leitura do ficheiro de imagem para o botão OK.
     */
    private void defeat() throws IOException {
        //Start the variables
        this.defeatFrame = new JFrame("Derrota");
        this.okDefeat = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/ok.png"))));
        this.okDefeat.setBorderPainted(false);
        this.okDefeat.setContentAreaFilled(false);
        this.okDefeat.setMargin(new Insets(0, 0, 0, 0));
        this.okDefeat.setOpaque(false);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JLabel label = new JLabel(new ImageIcon(ImageIO.read(
                        new File("lib/images/defeatGhost.png")).getScaledInstance(GraphicsEnvironment.
                            getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth() / 5 + 80, 
                                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
                                    getDisplayMode().getHeight() / 2, Image.SCALE_DEFAULT)));
        JLabel label2 = new JLabel(
                "<html>"
                 + "<h1>"
                     + "<font color=\"red\">DERROTA<br/></font>"
                 + "</h1>"
             + "</html>", SwingConstants.CENTER);
        
        //Layout config and Add content to panels
        panel1.setLayout(new BorderLayout());
        panel1.add(label2, BorderLayout.NORTH);
        panel1.add(label, BorderLayout.SOUTH);
        panel1.setBackground(Color.BLACK);
        
        panel2.add(new JLabel("<html><p><h2><font color=\"white\">Parece que foste derrotado...</p><br/><p>Tenta outra vez!</font></h2></p></html>"));
        panel2.setBackground(Color.BLACK);
        
        panel3.add(this.okDefeat);
        panel3.setBackground(Color.BLACK);
        
        //Add content to the frame
        this.defeatFrame.getContentPane().add(panel1, BorderLayout.NORTH);
        this.defeatFrame.getContentPane().add(panel2, BorderLayout.CENTER);
        this.defeatFrame.getContentPane().add(panel3, BorderLayout.SOUTH);
        
        //Frame config
        this.defeatFrame.setSize(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth(), 
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight()
        );
        this.defeatFrame.setLocationRelativeTo(null);
        this.defeatFrame.setUndecorated(true);
        this.defeatFrame.setResizable(false);
        this.defeatFrame.setVisible(true);
        
        //Action listener
        this.okDefeat.addActionListener(this);
    }
    
    /**
     * Apresentação do ecrã de vitória.
     * 
     * @throws IOException erro na leitura de ficheiros
     * @throws EmptyCollectionException coleção vazia
     * @throws FileNotFoundException ficheiro não encontrado
     * @throws NonComparableException objeto não comparável
     */
    private void victory() throws IOException, EmptyCollectionException, FileNotFoundException, NonComparableException {
        //Start the variables
        this.files.writePlayerRankingInfo(this.instance, this.map.getName());
        this.victoryFrame = new JFrame("Vitoria");
        this.okVictory = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/ok.png"))));
        this.okVictory.setBorderPainted(false);
        this.okVictory.setContentAreaFilled(false);
        this.okVictory.setMargin(new Insets(0, 0, 0, 0));
        this.okVictory.setOpaque(false);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JLabel label = new JLabel(new ImageIcon(ImageIO.read(
                        new File("lib/images/exitDoor.jpg")).getScaledInstance(GraphicsEnvironment.
                            getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth() / 5, 
                                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
                                    getDisplayMode().getHeight() / 2+150, Image.SCALE_DEFAULT)));
        JLabel label2 = new JLabel(
                "<html>"
                 + "<h1>"
                     + "<font color=\"green\">VITORIA<br/></font>"
                 + "</h1>"
             + "</html>", SwingConstants.CENTER);
        
        //Layout config and Add content to panels
        panel1.setLayout(new BorderLayout());
        panel1.add(label2, BorderLayout.NORTH);
        panel1.add(label, BorderLayout.SOUTH);
        panel1.setBackground(Color.BLACK);
        
        panel2.add(new JLabel("<html><p><h2><font color=\"white\">Parabéns! Chegaste à saída.</p><br/><p>Acabaste o jogo com " + this.instance.getScore() + " pontos.</font></h2></p></html>"));
        panel2.setBackground(Color.BLACK);
        
        panel3.add(this.okVictory);
        panel3.setBackground(Color.BLACK);
        
        //Add content to the frame
        this.victoryFrame.getContentPane().add(panel1, BorderLayout.NORTH);
        this.victoryFrame.getContentPane().add(panel2, BorderLayout.CENTER);
        this.victoryFrame.getContentPane().add(panel3, BorderLayout.SOUTH);
        
        //Frame config
        this.victoryFrame.setSize(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth(),
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight()
        );
        this.victoryFrame.setLocationRelativeTo(null);
        this.victoryFrame.setUndecorated(true);
        this.victoryFrame.setResizable(false);
        this.victoryFrame.setVisible(true);
        
        //Action listener
        this.okVictory.addActionListener(this);
    }

    /**
     * Jogo em Modo Normal.
     */
    private void normal(boolean first) throws LineUnavailableException, IOException, UnsupportedAudioFileException, ElementNotFoundException, FileNotFoundException, EmptyCollectionException, NonComparableException {
        if (first) {
            this.instance.setPos(this.map.getEntranceRoom());
            this.instance.setScore(this.map.getPoints());
            this.game = new JFrame("Casa Assombrada");
            this.menuB = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/menu.png"))));
            this.menuB.setBorderPainted(false);
            this.menuB.setContentAreaFilled(false);
            this.menuB.setMargin(new Insets(0, 0, 0, 0));
            this.menuB.setOpaque(false);
            JPanel panel = new JPanel(new GridBagLayout());
            JPanel panel2 = new JPanel(new BorderLayout(0, -30));
            GridBagConstraints gbc = new GridBagConstraints();

            this.points = new JLabel("<html><h1><font color=\"white\">PONTOS: " + this.instance.getScore() + "</font></h1></html>");
            this.pos = new JLabel("<html><h3><font color=\"white\">APOSENTO ATUAL: " + this.instance.getPos().getName().toUpperCase() + "</font></h3></html>");

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.NORTH;

            panel.add(this.points, gbc);
            gbc.gridy = 1;
            panel.add(this.pos, gbc);
            panel.setBackground(Color.BLACK);
            this.pane = new GamePane();
            panel2.add(this.menuB);

            panel2.add(panel, BorderLayout.NORTH);
            panel2.setBackground(Color.BLACK);
            this.game.add(panel2, BorderLayout.EAST);
            
            //Configuring audio
            this.clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("lib/sounds/gamemusic.wav"));
            this.clip.open(inputStream);
            if (this.sound) {
                this.clip.start();
            }
            
            //Configuring the frame
            this.game.setUndecorated(true);
            this.game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.game.setResizable(false);
            this.game.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
                    getDisplayMode().getWidth(), GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
                            getDisplayMode().getHeight());
            this.game.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.game.setLocationRelativeTo(null);
            this.game.setVisible(true);

            this.menuB.addActionListener(this);
        } else {
            if (this.instance.getScore() == 0) {
                this.defeat();
                return;
            } else if (this.instance.getPos().getType() == -1) {
                this.victory();
                return;
            }
            this.game.remove(this.pane);
            this.pane = new GamePane();
        }
        this.game.add(this.pane);
    }
    
    /**
     * Classe que estrutura visualmente o jogo.
     */
    class GamePane extends JPanel implements ActionListener {
        
        private JButton[] doors;
        private UnorderedListADT<Room> rooms;
                
        /**
         * Construtor para a classe que estrutura visualmente o jogo.
         * 
         * @throws IOException
         * @throws ElementNotFoundException 
         */
        public GamePane() throws IOException, ElementNotFoundException {
            setLayout(new BorderLayout());
            JPanel panel = new JPanel();
            JPanel panel2 = new JPanel();
            panel2.setLayout(new BorderLayout());
            panel2.add(new JLabel(new ImageIcon("lib/images/ok.png")));
            
            this.rooms = GUI.this.map.getRoomEdges(GUI.this.instance.getPos().getName());
            Iterator<Room> iter = this.rooms.iterator();
            panel.setLayout(new GridLayout(2, this.rooms.size(), 20, 20));
            while (iter.hasNext()) {
                JLabel jlabel = new JLabel("<html><h1><strong><font color=\"white\">" + iter.next().getName().toUpperCase() + "</font></strong></h1></html>", (int)JLabel.CENTER_ALIGNMENT);
                panel.add(jlabel);
            }
            
            this.doors = new JButton[this.rooms.size()];
            
            int i = 0;
            while (i < this.rooms.size()) {
                this.doors[i] = this.makeButton(new JButton(new ImageIcon(ImageIO.read(
                        new File("lib/images/closedDoor.png")).getScaledInstance(GraphicsEnvironment.
                            getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth() / 8, 
                                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
                                    getDisplayMode().getHeight() / 2, Image.SCALE_DEFAULT))));
                panel.add(this.doors[i]);
                this.doors[i].addActionListener(this);
                i++;
            }
            panel.setBackground(Color.BLACK);
            
            add(panel, BorderLayout.SOUTH);
            add(panel2, BorderLayout.NORTH);
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

        /**
         * Define o que sucede após um determinado evento.
         * 
         * @param ev evento
         */
        @Override
        public void actionPerformed(ActionEvent ev) {
            try {
                Iterator<Room> iter = this.rooms.iterator();
                Room room = new Room();
                if (ev.getSource().equals(this.doors[0])) {
                    room = iter.next();
                } else if (ev.getSource().equals(this.doors[1])) {
                    iter.next();
                    room = iter.next();
                } else if (ev.getSource().equals(this.doors[2])) {
                    iter.next();
                    iter.next();
                    room = iter.next();
                } else if (ev.getSource().equals(this.doors[3])) {
                    iter.next();
                    iter.next();
                    iter.next();
                    room = iter.next();
                } else if (ev.getSource().equals(this.doors[4])) {
                    iter.next();
                    iter.next();
                    iter.next();
                    iter.next();
                    room = iter.next();
                } else if (ev.getSource().equals(this.doors[5])) {
                    iter.next();
                    iter.next();
                    iter.next();
                    iter.next();
                    iter.next();
                    room = iter.next();
                } else if (ev.getSource().equals(this.doors[6])) {
                    iter.next();
                    iter.next();
                    iter.next();
                    iter.next();
                    iter.next();
                    iter.next();
                    room = iter.next();
                }
                long remove = GUI.this.instance.getScore() - (GUI.this.map.getEdgeWeight(GUI.this.instance.getPos().getName(), room.getName()) * GUI.this.instance.getLevel());
                long previous = GUI.this.instance.getScore();
                if (remove < 0)
                    remove = 0;
                
                GUI.this.instance.setScore(remove);
                if (GUI.this.instance.getScore() != previous && GUI.this.instance.getScore() != 0 && room.getType() != -1)
                    GUI.this.ghost(GUI.this.map.getEdgeWeight(GUI.this.instance.getPos().getName(), room.getName()) * GUI.this.instance.getLevel());
                GUI.this.points.setText("<html><h1><font color=\"white\">PONTOS: " + GUI.this.instance.getScore() + "</font></h1></html>");
                GUI.this.instance.setPos(room);
                GUI.this.pos.setText("<html><h3><font color=\"white\">APOSENTO ATUAL: " + GUI.this.instance.getPos().getName().toUpperCase() + "</font></h3></html>");
                GUI.this.normal(false);
            } catch (ElementNotFoundException | IOException | LineUnavailableException | UnsupportedAudioFileException | EmptyCollectionException | NonComparableException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Método que apresenta a janela que demonstra os pontos a serem retirados por encontrar um fantasma.
     * 
     * @param points pontos a serem retirados ao encontrar um determinado fantasma
     * @throws IOException erro ao procurar a biblioteca de imagens
     */
    private void ghost(long points) throws IOException {
        //Start the variables
        this.ghostFrame = new JFrame("Fantasma");
        this.okGhost = new JButton(new ImageIcon(ImageIO.read(new File("lib/images/ok.png"))));
        this.okGhost.setBorderPainted(false);
        this.okGhost.setContentAreaFilled(false);
        this.okGhost.setMargin(new Insets(0, 0, 0, 0));
        this.okGhost.setOpaque(false);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JLabel label = new JLabel(new ImageIcon(ImageIO.read(
                        new File("lib/images/ghostroom.jpg")).getScaledInstance(GraphicsEnvironment.
                            getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth() / 5, 
                                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
                                    getDisplayMode().getHeight() / 2, Image.SCALE_DEFAULT)));
        JLabel label2 = new JLabel(
                "<html>"
                 + "<h1>"
                     + "<font color=\"white\">FANTASMA ENCONTRADO!!!<br/></font>"
                 + "</h1>"
             + "</html>", SwingConstants.CENTER);
        
        //Layout config and Add content to panels
        panel1.setLayout(new BorderLayout());
        panel1.add(label2, BorderLayout.NORTH);
        panel1.add(label, BorderLayout.SOUTH);
        panel1.setBackground(Color.BLACK);
        
        panel2.add(new JLabel("<html><p><h2><font color=\"white\">Ups! Encontraste um fantasma...</p><br/><p>Perdeste " + points + " pontos</font></h2></p></html>"));
        panel2.setBackground(Color.BLACK);
        
        panel3.add(this.okGhost);
        panel3.setBackground(Color.BLACK);
        
        //Add content to the frame
        this.ghostFrame.getContentPane().add(panel1, BorderLayout.NORTH);
        this.ghostFrame.getContentPane().add(panel2, BorderLayout.CENTER);
        this.ghostFrame.getContentPane().add(panel3, BorderLayout.SOUTH);
        
        //Frame config
        this.ghostFrame.setSize(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth()/* - 500*/, 
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight()/* - 200*/
        );
        this.ghostFrame.setLocationRelativeTo(null);
        this.ghostFrame.setUndecorated(true);
        this.ghostFrame.setResizable(false);
        this.ghostFrame.setVisible(true);
        
        //Action listener
        this.okGhost.addActionListener(this);
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
            this.level.dispose();
            this.instance.setLevel(this.instance.EASY);
            this.blocked = false;
            if (this.status == 0) {
                this.status = 1;
                try {
                    this.playerName();
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (ev.getSource().equals(this.normal)) {
            this.level.dispose();
            this.instance.setLevel(this.instance.NORMAL);
            this.blocked = false;
            if (this.status == 0) {
                this.status = 1;
                try {
                    this.playerName();
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (ev.getSource().equals(this.hard)) {
            this.level.dispose();
            this.instance.setLevel(this.instance.HARD);
            this.blocked = false;
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
                if (this.status == 1) {
                    this.status = 2;
                    this.instance.setName(this.playerName.getText());
                    try {
                        this.mainMenu();
                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.player.dispose();
            }
        } else if (ev.getSource().equals(this.okHighscore)) {
            this.highscoreFrame.dispose();
            this.blocked = false;
        } else if (ev.getSource().equals(this.okSimulation)) {
            this.simulationFrame.dispose();
            this.blocked = false;
        } else if (ev.getSource().equals(this.close) && !this.blocked) {
            System.exit(0);
        } else if (ev.getSource().equals(this.levelChange) && !this.blocked) {
            this.levelChanger();
        } else if (ev.getSource().equals(this.highscore) && !this.blocked) {
            try {
                this.highscore();
            } catch (IOException | NonComparableException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (ev.getSource().equals(this.normalMode) && !this.blocked) {
            this.clip.stop();
            this.game.dispose();
            try {
                this.normal(true);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | ElementNotFoundException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EmptyCollectionException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NonComparableException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (ev.getSource().equals(this.simulationMode) && !this.blocked) {
            try {
                this.simulation();
            } catch (ElementNotFoundException | NonAvailablePath | EmptyCollectionException | IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (ev.getSource().equals(this.soundB) && !this.blocked) {
            this.sound();
        } else if (ev.getSource().equals(this.menuB)) {
            this.clip.stop();
            this.game.dispose();
            try {
                this.mainMenu();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (ev.getSource().equals(this.okGhost)) {
            this.ghostFrame.dispose();
        } else if (ev.getSource().equals(this.okDefeat)) {
            this.defeatFrame.dispose();
            this.game.dispose();
            try {
                this.mainMenu();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (ev.getSource().equals(this.okVictory)) {
            this.victoryFrame.dispose();
            this.game.dispose();
            try {
                this.mainMenu();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}