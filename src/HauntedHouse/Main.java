package HauntedHouse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 

/**
 *
 * @author Francisco Spínola
 * @author João Tiago Pereira
 */
public class Main {
    public static void main(String[] args) {
        try {
            Object obj = new JSONParser().parse(new FileReader("Mapa.json"));
            JSONObject jo = (JSONObject) obj;
            new TUI();
        } catch (FileNotFoundException ex) {} catch (IOException ex) {} catch (ParseException ex) {}
    }
}
