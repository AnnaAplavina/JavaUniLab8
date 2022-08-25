package logic.commands;

import collectionitems.MusicBand;
import collectionitems.WrongArgumentException;
import data.CollectionManager;

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
        manager.addNewElementFromUser(band);
        return "Added new band";
    }
}
