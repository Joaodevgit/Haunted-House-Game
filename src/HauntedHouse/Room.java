package HauntedHouse;

import java.util.Objects;

/**
 *
 * @author Francisco Spínola
 * @author João Pereira
 */
public class Room {
    private String name;
    
    /**
     * How variable <i>type</i> works:
     * 
     * -1: Exit
     * 0: Normal
     * 1: Entrance
     */
    private short type;

    public Room(String name) {
        this.name = name;
        this.type = 0;
    }
    
    public Room(String name, short type) throws Exception {
        this.name = name;
        if (type > 1 || type < -1)
            throw new Exception("invalid type for the room");
        this.type = type;
    }
    
    public Room() {
        this.name = "";
        this.type = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Room other = (Room) obj;
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String status;
        switch (this.type) {
            case 1: status = "entrada";
                break;
            case 0: status = "intermedio";
                break;
            case -1: status = "exterior";
                break;
            default: status = "";
                break;
        }
        return name;
    }
}
