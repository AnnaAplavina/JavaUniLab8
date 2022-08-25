package logic.commands;

import data.CollectionManager;

/**
 * This command is for showing all bands in collection
 */
public class ShowCommand implements Command {
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(){
        return collectionManager.toString();
    }
}
