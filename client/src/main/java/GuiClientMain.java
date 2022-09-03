import connection.MusicBandConnection;
import gui.frames.LoginFrame;

import java.io.IOException;

public class GuiClientMain {
    public static void main(String[] args) throws IOException {
        LoginFrame loginFrame = new LoginFrame(new MusicBandConnection("127.0.0.1", 4321));
        loginFrame.setVisible(true);
    }
}
