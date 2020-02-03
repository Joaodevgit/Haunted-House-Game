package HauntedHouse;

/**
 *
 * @author João Pereira
 * @author Francisco Spínola
 */
public class Instance implements Comparable<Instance> {

    public final short EASY = 1;
    public final short NORMAL = 2;
    public final short HARD = 3;
    protected String name;

    protected long score;
    protected short level;
    protected Room pos;
    protected String mapName;
    
    public Instance() {}

    public Instance(String name, long score, String mapName) {
        this.name = name;
        this.score = score;
        this.mapName = mapName;
    }
    
    public Instance(String name, long score, Room pos) {
        this.name = name;
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
    
    protected void setName(String name) {
        this.name = name;
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

    public String getMapName() {
        return mapName;
    }

    protected void reset(Room entrance, long points) {
        this.pos = entrance;
        this.score = points;
    }

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