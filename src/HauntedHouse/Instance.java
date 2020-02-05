package HauntedHouse;

/**
 * Representa a instancia atual do jogo, ou seja, informação como o nome do jogador, 
 * o nível de dificuldade, a posição e os pontos atuais do mesmo.
 *
 * @author João Pereira
 * @author Francisco Spínola
 */
public class Instance implements Comparable<Instance> {
    
    public final short EASY = 1;
    public final short NORMAL = 2;
    public final short HARD = 3;
    
    /** nível de dificuldade do jogador varia desde 1 a 3, sendo que, 
     * quanto mais baixo este valor, maior a facilidade do jogo */
    protected short level;
    protected String name;
    protected long score;
    protected Room pos;
    
    /** mapName será utilizado apenas para escrita e leitura de ficheiros */
    protected String mapName;
    
    /**
     * Construtor para uma instância sem nenhuma informação, no momento em que este é chamado.
     */
    public Instance() {}

    /**
     * Criar uma instância, passando por argumento o nome e pontos do jogador e o mapa onde joga/jogou.
     * Este método construtor foi criado para ser usado paenas pela classe Files.
     * 
     * @param name nome do jogador
     * @param score pontos do jogador
     * @param mapName nome do mapa
     * @see Files
     */
    public Instance(String name, long score, String mapName) {
        this.name = name;
        this.score = score;
        this.mapName = mapName;
    }
    
    /**
     * Criar uma instância, passando o nome, pontos e posição atual do jogador.
     * 
     * @param name nome do jogador
     * @param score pontos do jogador
     * @param pos posição atual do jogador
     */
    public Instance(String name, long score, Room pos) {
        this.name = name;
        this.score = score;
        this.pos = pos;
    }

    /**
     * Obtenção dos pontos atuais do jogador.
     * 
     * @return pontos atuais do jogador
     */
    public long getScore() {
        return score;
    }

    /**
     * Alterar o valor dos pontos da instância atual.
     * 
     * @param score novo valor para score
     */
    public void setScore(long score) {
        this.score = score;
    }

    /**
     * Obtenção do nome do jogador
     * 
     * @return nome do jogador
     */
    public String getName() {
        return name;
    }
    
    /**
     * Modificar o nome do jogador.
     * 
     * @param name novo nome do jogador
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * Obtenção do nível de dificuldade do jogador.
     * 
     * @return nível de dificuldade do jogador
     */
    public short getLevel() {
        return level;
    }

    /**
     * Alterar o nível de dificuldade do jogador.
     * 
     * @param level novo nível de dificuldade
     */
    public void setLevel(short level) {
        this.level = level;
    }

    /**
     * Obtenção da posição atual do jogador.
     * 
     * @return a posição atual do jogador
     */
    public Room getPos() {
        return pos;
    }

    /**
     * Alterar a posição atual do jogador.
     * 
     * @param pos nova posição atual
     */
    public void setPos(Room pos) {
        this.pos = pos;
    }

    /**
     * Obtenção do nome do mapa em que a instância atual se encontra.
     * 
     * @return nome do mapa atual
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Método que faz <i>reset</i> da instância atual para preparar um novo jogo.
     * 
     * @param entrance entrada no mapa
     * @param points pontos do mapa
     */
    protected void reset(Room entrance, long points) {
        this.pos = entrance;
        this.score = points;
    }

    /**
     * Compara o objeto atual com outro, passado por argumento.
     * 
     * @param o objeto a comparar
     * @return o resultado da comparação
     * @see Comparable
     */
    @Override
    public int compareTo(Instance o) {
        if (this.score < o.score)
            return 1;
        else if (this.score == o.score)
            return 0;
        else
            return -1;
    }
}