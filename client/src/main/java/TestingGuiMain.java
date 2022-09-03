import connection.MusicBandConnection;
import ui.frames.CollectionFrame;
import ui.frames.LoginFrame;

import java.io.IOException;

public class TestingGuiMain {
    public static void main(String[] args) throws IOException {
//        LoginFrame loginFrame = new LoginFrame(new MusicBandConnection("127.0.0.1", 4321));
//        loginFrame.setVisible(true);
        CollectionFrame collectionFrame = new CollectionFrame();
        collectionFrame.setVisible(true);
    }
}
