package logic.commands;

import data.CollectionManager;

/**
 * This command clears the collection
 */
public class ClearCommand implements Command{
    CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(){
        collectionManager.clearCollection();
        return "Cleared collection";
    }
}
