package logic.commands;

import collectionitems.MusicBand;
import collectionitems.WrongArgumentException;
import data.CollectionManager;

import java.io.IOException;

/**
 * This command is for replacing an element with a particular id
 */
public class UpdateCommand implements Command {
    String arg;
    CollectionManager manager;
    MusicBand band;

    public UpdateCommand(String arg, CollectionManager manager, MusicBand band){
        this.manager = manager;
        this.arg = arg;
        this.band = band;
    }

    @Override
    public String execute() throws IOException, WrongArgumentException{
        if(band == null){
            throw new WrongArgumentException("Band can not be null");
        }
        if(arg == null){
            throw new WrongArgumentException("Id is needed to execute update command");
        }
        try{
            int id = Integer.parseInt(arg);
            if(id <= 0){
                throw new WrongArgumentException("Id must be greater than 0");
            }
            manager.changeElementFromUser(id, band);
        }
        catch (NumberFormatException ex){
            throw new WrongArgumentException("Id must be an integer");
        }
        return "Updated";
    }
}
