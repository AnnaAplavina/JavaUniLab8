package logic.commands;

import collectionitems.WrongArgumentException;
import connection.MusicBandResponse;
import connection.ResponseStatus;
import data.CollectionManager;
import data.database.QueryExecutionException;

/**
 * This command clears the collection
 */
public class ClearCommand implements Command, ChangingCollectionCommand{
    private CollectionManager collectionManager;
    private String username;
    private UpdateStatus updateStatus = UpdateStatus.NOT_EXECUTED;

    public ClearCommand(CollectionManager collectionManager, String username){
        this.collectionManager = collectionManager;
        this.username = username;
    }

    @Override
    public String execute() throws WrongArgumentException {
        try {
            collectionManager.clearCollection(username);
            updateStatus = UpdateStatus.UPDATED;
        } catch (QueryExecutionException e) {
            updateStatus = UpdateStatus.NOT_UPDATED;
            throw new WrongArgumentException("Error when working with db!");
        }
        return "Cleared collection";
    }

    @Override
    public MusicBandResponse getUpdateResponse() {
        if(updateStatus == UpdateStatus.NOT_EXECUTED){
            throw new GetUpdateBeforeExecutionException();
        }
        if(updateStatus == UpdateStatus.NOT_UPDATED){
            return null;
        }
        MusicBandResponse updateResponse = new MusicBandResponse();
        updateResponse.response = username;
        updateResponse.status = ResponseStatus.UPDATE_CLEAR;
        return updateResponse;
    }
}
