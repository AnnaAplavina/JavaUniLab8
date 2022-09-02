import ui.frames.LoginFrame;
import ui.frames.RegisterFrame;

public class TestingGuiMain {
    public static void main(String[] args) {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        RegisterFrame registerFrame = new RegisterFrame();
        registerFrame.setVisible(true);
    }
}
