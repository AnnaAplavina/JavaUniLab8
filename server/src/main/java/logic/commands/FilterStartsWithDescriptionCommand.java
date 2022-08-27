package logic.commands;

import collectionitems.MusicBand;
import collectionitems.MusicGenre;
import collectionitems.WrongArgumentException;
import data.CollectionManager;

import java.io.IOException;
import java.util.List;

/**
 * This command is for showing to the user bands with descriptions that start with given string
 */
public class FilterStartsWithDescriptionCommand implements Command {
    String arg;
    CollectionManager manager;

    public FilterStartsWithDescriptionCommand(CollectionManager manager, String arg){
        this.manager = manager;
        this.arg = arg;
    }

    @Override
    public String execute() throws IOException, WrongArgumentException {
        if(arg == null){
            throw new WrongArgumentException("Specify the genre please");
        }
        List<MusicBand> bands = manager.getWithDescriptionStart(arg);
        StringBuilder res = new StringBuilder("Found bands: ");
        for(MusicBand band: bands){
            res.append("\n\n").append(band.toString());
        }
        return "" + res;
    }
}
