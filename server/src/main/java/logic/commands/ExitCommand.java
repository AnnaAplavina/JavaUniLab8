package logic.commands;

import collectionitems.WrongArgumentException;
import data.CollectionManager;

import java.io.IOException;

/**
 * This command is for exiting the program
 */
public class ExitCommand implements Command{
    private CollectionManager manager;

    public ExitCommand(CollectionManager manager){
        this.manager = manager;
    }

    @Override
    public String execute() throws WrongArgumentException, IOException {
        manager.saveCollection();
        return "Exiting program";
    }
}
