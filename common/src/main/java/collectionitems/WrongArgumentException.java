package collectionitems;

/**
 * Thrown if an argument of a command is incorrect
 */
public class WrongArgumentException extends Exception{
    public WrongArgumentException(){
        super();
    }

    public WrongArgumentException(String message){
        super(message);
    }
}
