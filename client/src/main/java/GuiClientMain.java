import connection.MusicBandConnection;
import gui.frames.CollectionFrame;
import gui.frames.LoginFrame;
import localization.BundlesManager;

import java.io.IOException;

public class GuiClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        MusicBandConnection connection = new MusicBandConnection("127.0.0.1", 4321);
        BundlesManager bundlesManager = new BundlesManager();
        LoginFrame loginFrame = new LoginFrame(connection, bundlesManager);
        loginFrame.setVisible(true);

//        MusicBandConnection connection = new MusicBandConnection("127.0.0.1", 4321);
//        connection.setUsername("NewUser");
//        connection.setPassword("1234");
//        CollectionFrame collectionFrame = new CollectionFrame("NewUser", connection);
//        collectionFrame.setVisible(true);
    }
}
