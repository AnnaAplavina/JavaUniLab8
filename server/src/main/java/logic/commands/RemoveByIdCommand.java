package logic.commands;

import collectionitems.WrongArgumentException;
import connection.MusicBandResponse;
import connection.ResponseStatus;
import data.CollectionManager;
import data.database.QueryExecutionException;

import java.util.ArrayList;

/**
 * This command is for removing the band with a particular id from collection
 */
public class RemoveByIdCommand implements Command, ChangingCollectionCommand {
    private CollectionManager collectionManager;
    private String arg;
    private String username;
    private UpdateStatus updateStatus = UpdateStatus.NOT_EXECUTED;
    int id;

    public RemoveByIdCommand(CollectionManager collectionManager, String arg, String username){
        this.collectionManager = collectionManager;
        this.arg = arg;
        this.username = username;
    }

    @Override
    public String execute() throws WrongArgumentException {
        if(arg == null){
            updateStatus = UpdateStatus.NOT_UPDATED;
            throw new WrongArgumentException("id is needed to execute update command");
        }
        try{
            int id = Integer.parseInt(arg);
            this.id = id;
            if(id <= 0){
                updateStatus = UpdateStatus.NOT_UPDATED;
                throw new WrongArgumentException("id must be greater than 0");
            }
            try {
                if(collectionManager.checkOwner(id, username)){
                    collectionManager.removeElementById(id);
                    updateStatus = UpdateStatus.UPDATED;
                }
                else{
                    updateStatus = UpdateStatus.NOT_UPDATED;
                    throw new WrongArgumentException("The band with these id does not exist" +
                            " or you are not the owner of this band");
                }
            } catch (QueryExecutionException e) {
                updateStatus = UpdateStatus.NOT_UPDATED;
                throw new WrongArgumentException("Error when working with db!");
            }
        }
        catch (NumberFormatException ex){
            updateStatus = UpdateStatus.NOT_UPDATED;
            throw new WrongArgumentException("id must be an integer");
        }
        return "Removed";
    }

    @Override
    public MusicBandResponse getUpdateResponse() {
        if(updateStatus == UpdateStatus.NOT_EXECUTED){
            throw new GetUpdateBeforeExecutionException();
        }
        if(updateStatus == UpdateStatus.NOT_UPDATED) {
            return null;
        }
        MusicBandResponse updateResponse = new MusicBandResponse();
        updateResponse.status = ResponseStatus.UPDATE_DELETE;
        updateResponse.ids = new ArrayList<>();
        updateResponse.ids.add(id);
        return updateResponse;
    }
}
