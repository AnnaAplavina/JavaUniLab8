package logic.commands;

import collectionitems.WrongArgumentException;
import data.CollectionManager;
import data.database.QueryExecutionException;

/**
 * This command clears the collection
 */
public class ClearCommand implements Command{
    private CollectionManager collectionManager;
    private String username;

    public ClearCommand(CollectionManager collectionManager, String username){
        this.collectionManager = collectionManager;
        this.username = username;
    }

    @Override
    public String execute() throws WrongArgumentException {
        try {
            collectionManager.clearCollection(username);
        } catch (QueryExecutionException e) {
            throw new WrongArgumentException("Error when working with db!");
        }
        return "Cleared collection";
    }
}
