package logic.commands;

import collectionitems.MusicBand;
import collectionitems.WrongArgumentException;
import data.CollectionManager;

import java.io.IOException;

/**
 * This command inserting a new band if it is lesser than any other band in collection
 */
public class InsertIfMinCommand implements Command{
    private CollectionManager manager;
    MusicBand band;

    public InsertIfMinCommand(CollectionManager manager, MusicBand band){
        this.manager = manager;
        this.band = band;
    }


    @Override
    public String execute() throws IOException, WrongArgumentException {
        if(band == null){
            throw new WrongArgumentException("Band can not be null");
        }
        if(manager.addIfMin(band)){
            return "Added new min element";
        }
        return "Provided element is not min element";
    }
}
