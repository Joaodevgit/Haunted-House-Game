package HauntedHouse;

/**
 *
 * @author João Pereira
 */
public class Player implements Comparable<Player> {

    private long highscore;
    private String name;
    private String mapName;

    public Player(String name, long highscore, String mapName) {
        this.name = name;
        this.highscore = highscore;
        this.mapName = mapName;
    }
    public Player(long highscore){
        this.highscore = highscore;
    }

    public long getHighscore() {
        return highscore;
    }

    public void setHighscore(long highscore) {
        this.highscore = highscore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    @Override
    public int compareTo(Player o) {
        if (this.highscore < o.highscore) {
            return 1;
        } else if (this.highscore == o.highscore) {
            return 0;
        } else
            return -1;
    }
}
