package HauntedHouse;

/**
 *
 * @author Francisco Spínola
 * @author João Tiago Pereira
 */
public class Room {
    private String name;
    private int ghost;

    public Room(String name, int ghost) {
        this.name = name;
        this.ghost = ghost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGhost() {
        return ghost;
    }

    public void setGhost(int ghost) {
        this.ghost = ghost;
    }
}
