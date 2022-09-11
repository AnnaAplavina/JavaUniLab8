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
 * This command is for inserting another band with given index
 */
public class InsertAtCommand implements Command, ChangingCollectionCommand
{
    private String arg;
    private CollectionManager manager;
    private MusicBand band;
    private String username;
    private UpdateStatus updateStatus = UpdateStatus.NOT_EXECUTED;

    public InsertAtCommand(CollectionManager manager, String arg, MusicBand band, String username){
        this.arg = arg;
        this.manager = manager;
        this.band = band;
        this.username = username;
    }

    @Override
    public String execute() throws IOException, WrongArgumentException {
        try{
            if(band == null){
                updateStatus = UpdateStatus.NOT_UPDATED;
                throw new WrongArgumentException("Band can not be null");
            }
            int index = Integer.parseInt(arg);
            if(index >= 0) {
                try {
                    manager.addNewElementFromUser(index, band, username);
                    updateStatus = UpdateStatus.UPDATED;
                } catch (QueryExecutionException e) {
                    updateStatus = UpdateStatus.NOT_UPDATED;
                    throw new WrongArgumentException("Error when working with db!");
                }
            }
            else{
                updateStatus = UpdateStatus.NOT_UPDATED;
                throw new WrongArgumentException("Index must be a positive integer");
            }
        }
        catch (NumberFormatException ex){
            updateStatus = UpdateStatus.NOT_UPDATED;
            throw new WrongArgumentException("Index must be a positive integer");
        }
        catch (ArrayIndexOutOfBoundsException ex){
            updateStatus = UpdateStatus.NOT_UPDATED;
            throw new WrongArgumentException("Index is out of bounds");
        }
        return "Inserted new element at index " + arg;
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
