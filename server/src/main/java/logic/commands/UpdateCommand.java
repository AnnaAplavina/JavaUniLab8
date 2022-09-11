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
 * This command is for replacing an element with a particular id
 */
public class UpdateCommand implements Command, ChangingCollectionCommand {
    private String arg;
    private CollectionManager manager;
    private MusicBand band;
    private String username;
    private UpdateStatus updateStatus = UpdateStatus.NOT_EXECUTED;

    public UpdateCommand(String arg, CollectionManager manager, MusicBand band, String username){
        this.manager = manager;
        this.arg = arg;
        this.band = band;
        this.username = username;
    }

    @Override
    public String execute() throws IOException, WrongArgumentException{
        if(band == null){
            updateStatus = UpdateStatus.NOT_UPDATED;
            throw new WrongArgumentException("Band can not be null");
        }
        if(arg == null){
            updateStatus = UpdateStatus.NOT_UPDATED;
            throw new WrongArgumentException("Id is needed to execute update command");
        }
        try{
            int id = Integer.parseInt(arg);
            if(id <= 0){
                updateStatus = UpdateStatus.NOT_UPDATED;
                throw new WrongArgumentException("Id must be greater than 0");
            }
            try {
                if(manager.checkOwner(id ,username)){
                    manager.changeElementFromUser(id, band);
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
            throw new WrongArgumentException("Id must be an integer");
        }
        return "Updated";
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
        updateResponse.status = ResponseStatus.UPDATE_UPDATE;
        updateResponse.musicBandList = new ArrayList<>();
        updateResponse.musicBandList.add(band);
        return updateResponse;
    }
}
