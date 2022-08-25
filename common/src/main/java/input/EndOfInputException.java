package input;

/**
 * Thrown when reading from user is not possible
 */
public class EndOfInputException extends Exception{
    public EndOfInputException(String message){
        super(message);
    }
}
