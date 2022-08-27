package logic.commands;

import collectionitems.MusicBand;
import collectionitems.WrongArgumentException;
import data.CollectionManager;
import data.database.QueryExecutionException;

import java.io.IOException;

/**
 * This command add a new band to collection
 */
public class AddCommand implements Command{
    private CollectionManager manager;
    private MusicBand band;

    public AddCommand(CollectionManager manager, MusicBand band){
        this.manager = manager;
        this.band = band;
    }

    @Override
    public String execute() throws IOException, WrongArgumentException {
        if(band == null){
            throw new WrongArgumentException("Band can not be null");
        }
        try {
            manager.addNewElementFromUser(band);
        } catch (QueryExecutionException e) {
            throw new WrongArgumentException("Error when working with db!");
        }
        return "Added new band";
    }
}
