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
            String dbName = "music_band_db";
            String userName = "user";
            String pass = "password";
            String bandsTable = "bands";
            String usersTable = "users";
            MusicBandDao musicBandDao = new MusicBandDao(dbName,
                    userName, pass, bandsTable);
            UserDao userDao = new UserDao(dbName,
                    userName, pass, usersTable);
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
