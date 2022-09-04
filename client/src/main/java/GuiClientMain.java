import connection.MusicBandConnection;
import gui.frames.LoginFrame;
import localization.BundlesManager;

import java.io.IOException;

public class GuiClientMain {
    public static void main(String[] args) throws IOException {
        MusicBandConnection connection = new MusicBandConnection("127.0.0.1", 4321);
        BundlesManager bundlesManager = new BundlesManager();
        LoginFrame loginFrame = new LoginFrame(connection, bundlesManager);
        loginFrame.setVisible(true);
    }
}
