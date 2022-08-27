package logic.commands;

import collectionitems.MusicBand;
import collectionitems.WrongArgumentException;
import data.CollectionManager;
import data.database.QueryExecutionException;

import java.io.IOException;

/**
 * This command inserting a new band if it is greater than any other band in collection
 */
public class InsertIfMaxCommand implements Command{
    private CollectionManager manager;
    private MusicBand band;

    public InsertIfMaxCommand(CollectionManager manager, MusicBand band){
        this.manager = manager;
        this.band = band;
    }


    @Override
    public String execute() throws IOException, WrongArgumentException{
        if(band == null){
            throw new WrongArgumentException("Band can not be null");
        }
        try {
            if(manager.addIfMax(band)){
                    return "Added new max element";
                }
        } catch (QueryExecutionException e) {
            throw new WrongArgumentException("Error when working with db!");
        }
        return "Provided element is not max element";
    }
}
