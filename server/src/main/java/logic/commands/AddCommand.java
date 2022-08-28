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
    private String username;

    public AddCommand(CollectionManager manager, MusicBand band, String username){
        this.manager = manager;
        this.band = band;
        this.username = username;
    }

    @Override
    public String execute() throws IOException, WrongArgumentException {
        if(band == null){
            throw new WrongArgumentException("Band can not be null");
        }
        try {
            manager.addNewElementFromUser(band, username);
        } catch (QueryExecutionException e) {
            throw new WrongArgumentException("Error when working with db!");
        }
        return "Added new band";
    }
}
