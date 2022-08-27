package logic.commands;

import collectionitems.WrongArgumentException;
import data.CollectionManager;
import data.database.QueryExecutionException;

/**
 * This command clears the collection
 */
public class ClearCommand implements Command{
    CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute() throws WrongArgumentException {
        try {
            collectionManager.clearCollection();
        } catch (QueryExecutionException e) {
            throw new WrongArgumentException("Error when working with db!");
        }
        return "Cleared collection";
    }
}
