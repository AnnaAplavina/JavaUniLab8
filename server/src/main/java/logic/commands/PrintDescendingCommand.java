package logic.commands;

import collectionitems.MusicBand;
import collectionitems.WrongArgumentException;
import data.CollectionManager;

import java.io.IOException;
import java.util.List;

/**
 * This command is for showing bands in collection in descending order
 */
public class PrintDescendingCommand implements Command{
    CollectionManager manager;

    public PrintDescendingCommand(CollectionManager manager){
        this.manager = manager;
    }

    @Override
    public String execute() throws IOException, WrongArgumentException{
        List<MusicBand> bands = manager.getDescending();
        StringBuilder res = new StringBuilder("Bands: ");
        for(MusicBand band: bands){
            res.append("\n\n").append(band.toString());
        }
        return res.toString();
    }
}
