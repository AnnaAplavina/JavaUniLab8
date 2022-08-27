package logic.commands;

import collectionitems.WrongArgumentException;
import data.CollectionManager;
import data.database.QueryExecutionException;

/**
 * This command is for removing the band with a particular id from collection
 */
public class RemoveByIdCommand implements Command {
    private CollectionManager collectionManager;
    String arg;

    public RemoveByIdCommand(CollectionManager collectionManager, String arg){
        this.collectionManager = collectionManager;
        this.arg = arg;
    }

    @Override
    public String execute() throws WrongArgumentException {
        if(arg == null){
            throw new WrongArgumentException("id is needed to execute update command");
        }
        try{
            int id = Integer.parseInt(arg);
            if(id <= 0){
                throw new WrongArgumentException("id must be greater than 0");
            }
            try {
                collectionManager.removeElementById(id);
            } catch (QueryExecutionException e) {
                throw new WrongArgumentException("Error when working with db!");
            }
        }
        catch (NumberFormatException ex){
            throw new WrongArgumentException("id must be an integer");
        }
        return "Removed";
    }
}
