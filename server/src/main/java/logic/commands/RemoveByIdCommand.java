package logic.commands;

import collectionitems.WrongArgumentException;
import data.CollectionManager;
import data.database.QueryExecutionException;

/**
 * This command is for removing the band with a particular id from collection
 */
public class RemoveByIdCommand implements Command {
    private CollectionManager collectionManager;
    private String arg;
    private String username;

    public RemoveByIdCommand(CollectionManager collectionManager, String arg, String username){
        this.collectionManager = collectionManager;
        this.arg = arg;
        this.username = username;
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
                if(collectionManager.checkOwner(id, username)){
                    collectionManager.removeElementById(id);
                }
                else{
                    throw new WrongArgumentException("The band with these id does not exist" +
                            " or you are not the owner of this band");
                }
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
