package logic.commands;

import collectionitems.WrongArgumentException;
import data.CollectionManager;

import java.io.IOException;

/**
 * This command is for saving collection to a file
 */
public class SaveCommand implements Command{
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager manager){
        this.collectionManager = manager;
    }

    @Override
    public String execute() throws IOException, WrongArgumentException {
        collectionManager.saveCollection();
        return "Saved";
    }
}
