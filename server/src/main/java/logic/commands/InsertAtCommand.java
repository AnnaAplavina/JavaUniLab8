package logic.commands;

import collectionitems.MusicBand;
import collectionitems.WrongArgumentException;
import data.CollectionManager;

import java.io.IOException;

/**
 * This command is for inserting another band with given index
 */
public class InsertAtCommand implements Command
{
    private String arg;
    private CollectionManager manager;
    private MusicBand band;

    public InsertAtCommand(CollectionManager manager, String arg, MusicBand band){
        this.arg = arg;
        this.manager = manager;
        this.band = band;
    }

    @Override
    public String execute() throws IOException, WrongArgumentException {
        try{
            if(band == null){
                throw new WrongArgumentException("Band can not be null");
            }
            int index = Integer.parseInt(arg);
            if(index >= 0) {
                manager.addNewElementFromUser(index, band);
            }
            else{
                throw new WrongArgumentException("Index must be a positive integer");
            }
        }
        catch (NumberFormatException ex){
            throw new WrongArgumentException("Index must be a positive integer");
        }
        catch (ArrayIndexOutOfBoundsException ex){
            throw new WrongArgumentException("Index is out of bounds");
        }
        return "Inserted new element at index " + arg;
    }
}
