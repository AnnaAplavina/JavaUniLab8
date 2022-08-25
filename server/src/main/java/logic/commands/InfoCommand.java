package logic.commands;

import data.CollectionManager;

/**
 * This command is for showing the information about the collection
 */
public class InfoCommand implements Command{
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(){
        return "Collection type: Vector \n" + "Initialization Data: " + collectionManager.getInitializationDate()
                + "\nCollection size: " + collectionManager.getCollectionSize();
    }
}
