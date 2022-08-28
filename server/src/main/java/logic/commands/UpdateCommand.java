package logic.commands;

import collectionitems.MusicBand;
import collectionitems.WrongArgumentException;
import data.CollectionManager;
import data.database.QueryExecutionException;

import java.io.IOException;

/**
 * This command is for replacing an element with a particular id
 */
public class UpdateCommand implements Command {
    private String arg;
    private CollectionManager manager;
    private MusicBand band;
    private String username;

    public UpdateCommand(String arg, CollectionManager manager, MusicBand band, String username){
        this.manager = manager;
        this.arg = arg;
        this.band = band;
        this.username = username;
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
            try {
                if(manager.checkOwner(id ,username)){
                    manager.changeElementFromUser(id, band);
                }
                else{
                    throw new WrongArgumentException("The band with these id does not exist" +
                            " or you are not the owner of this band");
                }
            } catch (QueryExecutionException e) {
                throw new WrongArgumentException("Error when working with db!");
            }
        }
        catch (NumberFormatException ex){
            throw new WrongArgumentException("Id must be an integer");
        }
        return "Updated";
    }
}
