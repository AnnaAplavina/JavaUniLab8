import connection.MusicBandServer;
import data.CollectionManager;
import data.DataManager;
import data.database.DaoInitializationException;
import data.database.MusicBandDao;
import data.database.QueryExecutionException;
import logic.CommandsExecutor;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) {
        try {
            //todo: create a new table for the server
            MusicBandDao musicBandDao = new MusicBandDao("jdbc:postgresql://localhost:5432/studs",
                    "s264432", "ajf870", "test_prog_musicband_264432");
            CollectionManager collectionManager = new CollectionManager(musicBandDao);
            CommandsExecutor executor = new CommandsExecutor(collectionManager);
            MusicBandServer server = new MusicBandServer(4321, executor, collectionManager);
            try {
                server.launch();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (DaoInitializationException | QueryExecutionException e) {
            e.printStackTrace();
        }
    }
}
