package logic.commands;

import collectionitems.MusicBand;
import collectionitems.WrongArgumentException;
import connection.MusicBandResponse;
import connection.ResponseStatus;
import data.CollectionManager;
import data.database.QueryExecutionException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This command add a new band to collection
 */
public class AddCommand implements Command, ChangingCollectionCommand{
    private CollectionManager manager;
    private MusicBand band;
    private String username;
    private UpdateStatus updateStatus = UpdateStatus.NOT_EXECUTED;

    public AddCommand(CollectionManager manager, MusicBand band, String username){
        this.manager = manager;
        this.band = band;
        this.username = username;
    }

    @Override
    public String execute() throws IOException, WrongArgumentException {
        if(band == null){
            updateStatus = UpdateStatus.NOT_UPDATED;
            throw new WrongArgumentException("Band can not be null");
        }
        try {
            manager.addNewElementFromUser(band, username);
            updateStatus = UpdateStatus.UPDATED;
        } catch (QueryExecutionException e) {
            updateStatus = UpdateStatus.NOT_UPDATED;
            throw new WrongArgumentException("Error when working with db!");
        }
        return "Added new band";
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
        updateResponse.status = ResponseStatus.UPDATE_ADD;
        updateResponse.musicBandList = new ArrayList<>();
        updateResponse.musicBandList.add(band);
        return updateResponse;
    }
}
