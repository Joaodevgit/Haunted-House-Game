package HauntedHouse;

/**
 *
 * @author Francisco Spínola
 * @author João Tiago Pereira
 */
public class Room {
    private String name;
    private long ghost;

    public Room(String name, long ghost) {
        this.name = name;
        this.ghost = ghost;
    }
    
    public Room() {
        this.name = "";
        this.ghost = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGhost() {
        return ghost;
    }

    public void setGhost(long ghost) {
        this.ghost = ghost;
    }
}
