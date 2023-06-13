import connection.MusicBandServer;
import data.CollectionManager;
import data.database.DaoInitializationException;
import data.database.bands.MusicBandDao;
import data.database.QueryExecutionException;
import data.database.users.UserDao;
import logic.CommandsExecutor;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class ServerMain {
    public static void main(String[] args) {
        try {
            System.out.println(LocalDateTime.now());
            MusicBandDao musicBandDao = new MusicBandDao("jdbc:postgresql://localhost:5432/mydb",
                    "music_band_server", "1234", "music_bands_table");
            UserDao userDao = new UserDao("jdbc:postgresql://localhost:5432/mydb",
                    "music_band_server", "1234", "music_band_users_table");
            CollectionManager collectionManager = new CollectionManager(musicBandDao);
            CommandsExecutor executor = new CommandsExecutor(collectionManager, userDao);
            MusicBandServer server = new MusicBandServer(4321, executor);
            try {
                server.launch();
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } catch (DaoInitializationException | QueryExecutionException e) {
            e.printStackTrace();
        }
    }
}
