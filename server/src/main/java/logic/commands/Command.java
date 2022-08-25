package logic.commands;

import collectionitems.WrongArgumentException;

import java.io.IOException;

/**
 * Interface for all commands classes
 */
public interface Command {
    /**
     *
     * @return information about the result of execution of this command
     * @throws IOException this exception is thrown if there was a problem when reading or writing to a file during the execution
     * @throws WrongArgumentException this exception is thrown if an argument for this command was incorrect,
     * contains a message that describes the reason why the argument is incorrect
     */
    String execute() throws WrongArgumentException, IOException;
}
