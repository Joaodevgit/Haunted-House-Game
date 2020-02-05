package Demo;

import HauntedHouse.GUI;
import HauntedHouse.Map;
import HauntedHouse.Room;
import ed.exceptions.ElementNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 * Classe que executa o programa.
 *
 * @author Francisco Spínola
 * @author João Pereira
 */
public class Execute {

    /**
     * @param args argumentos da linha de comandos
     */
    public static void main(String[] args) {
        try {
            Map<Room> map = new Map<>("lib/mapa.json");
            new GUI(map);
        } catch (IOException ex) {
            System.out.println("Erro na leitura de ficheiros.");
        } catch (ElementNotFoundException ex) {
            System.out.println("Elemento não encontrado.");
        } catch (ParseException ex) {
            System.out.println("Erro no parsing de JSON para Java.");
        }
    }
}
