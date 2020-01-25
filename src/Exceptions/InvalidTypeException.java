package Exceptions;

/**
 * Excepção para o caso de o tipo de aposento ser inválido
 *
 * @author Francisco Spínola
 * @author João Pereira
 */
public class InvalidTypeException extends Exception {
    
    public static final String message = "Tipo de aposento inválido.";

    /**
     * Cria uma nova instância de <code>InvalidTypeException</code> sem
     * uma mensagem específica.
     */
    public InvalidTypeException() {
    }

    /**
     * Cria uma nova instância de <code>InvalidTypeException</code> com
     * uma mensagem específica.
     *
     * @param msg mensagem específica
     */
    public InvalidTypeException(String msg) {
        super(msg);
    }
}
