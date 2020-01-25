package HauntedHouse;

import Exceptions.InvalidTypeException;
import java.util.Objects;

/**
 * Classe que simboliza um aposento na casa assombrada.
 *
 * @author Francisco Spínola
 * @author João Pereira
 */
public class Room {
    private String name;
    
    /**
     * Funcionamento da variável <i>type</i>:
     * 
     * -1: Exterior
     * 0: Normal
     * 1: Entrada
     */
    private short type;

    /**
     * Método construtor que define por defeito um aposento do tipo normal.
     * 
     * @param name nome do aposento
     */
    public Room(String name) {
        this.name = name;
        this.type = 0;
    }
    
    /**
     * Método construtor para definir especificamente o nome e tipo de um aposento.
     * 
     * @param name nome do aposento
     * @param type tipo de aposento, de acordo com a explicação do funcionamento da variável
     * @throws InvalidTypeException caso o valor do tipo seja diferente 
     */
    public Room(String name, short type) throws InvalidTypeException {
        this.name = name;
        if (type > 1 || type < -1)
            throw new InvalidTypeException(InvalidTypeException.message);
        this.type = type;
    }
    
    /**
     * Método construtor para um aposento por defeito. O tipo de aposento fica automaticamente a <i>normal</i>.
     */
    public Room() {
        this.name = "";
        this.type = 0;
    }

    /**
     * Obtenção do nome do aposento.
     * 
     * @return nome do aposento
     */
    public String getName() {
        return name;
    }

    /**
     * Definir o nome do aposento
     * 
     * @param name nome do aposento
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtenção do tipo de aposento.
     * 
     * @return tipo de aposento
     */
    public short getType() {
        return type;
    }

    /**
     * Definir o tipo de aposento.
     * 
     * @param type tipo de aposento
     */
    public void setType(short type) {
        this.type = type;
    }

    /**
     * Verificação de aposentos iguais (cujas variáveis tenham o mesmo valor).
     * 
     * @param obj o objeto a comparar com o atual
     * @return <i>true</i> se os aposentos forem iguais
     */
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

    /**
     * Representação em <i>String</i> de um aposento.
     * 
     * @return string que representa o objeto atual
     */
    @Override
    public String toString() {
        String status;
        switch (this.type) {
            case 1: status = "entrada";
                break;
            case 0: status = "normal";
                break;
            case -1: status = "exterior";
                break;
            default: status = "";
                break;
        }
        return name + " - " + status;
    }
}
