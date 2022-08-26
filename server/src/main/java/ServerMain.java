import connection.MusicBandServer;
import data.CollectionManager;
import data.DataManager;
import logic.CommandsExecutor;

import java.io.IOException;
import java.time.LocalDateTime;

public class ServerMain {
    public static void main(String[] args) {
        try {
            DataManager dataManager = new DataManager("collection.xml");
            CollectionManager collectionManager = new CollectionManager(dataManager);
            CommandsExecutor executor = new CommandsExecutor(collectionManager);
            MusicBandServer server = new MusicBandServer(4321, executor, collectionManager);
            try {
                server.launch();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
