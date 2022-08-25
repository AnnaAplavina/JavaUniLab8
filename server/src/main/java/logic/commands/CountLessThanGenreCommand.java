package logic.commands;

import collectionitems.MusicGenre;
import collectionitems.WrongArgumentException;
import data.CollectionManager;

/**
 * This command counts bands that have lesser genre than genre then the genre that the user specified
 */
public class CountLessThanGenreCommand implements Command{
    String arg;
    CollectionManager manager;

    public CountLessThanGenreCommand(CollectionManager manager, String arg){
        this.manager = manager;
        this.arg = arg;
    }

    @Override
    public String execute() throws WrongArgumentException{
        MusicGenre musicGenre;
        if(arg == null){
            throw new WrongArgumentException("Specify the genre please");
        }
        switch (arg){
            case "soul": musicGenre = MusicGenre.SOUL; break;
            case "blues": musicGenre = MusicGenre.BLUES; break;
            case "punk rock": musicGenre = MusicGenre.PUNK_ROCK; break;
            case "post punk": musicGenre = MusicGenre.POST_PUNK; break;
            case "brit pop": musicGenre = MusicGenre.BRIT_POP; break;
            default: throw new WrongArgumentException("Unknown genre");
        }
        int amount = manager.countWithLesserGenre(musicGenre);
        return "" + amount;
    }
}
