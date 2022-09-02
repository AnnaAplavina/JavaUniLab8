package ui.frames;
import connection.MusicBandConnection;
import connection.MusicBandResponse;
import connection.ResponseStatus;
import ui.components.PlaceholderPasswordField;
import ui.components.PlaceholderTextField;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginFrame extends JFrame {
    //design
    private final JLabel usernameCommentLabel;
    private final JLabel serverResponseLabel;
    private final PlaceholderTextField usernameField;
    private final PlaceholderPasswordField passwordField;

    //functionality
    private final MusicBandConnection connection;


    public LoginFrame(MusicBandConnection connection){
        //design
        JPanel northPanel = new JPanel();
        JPanel westPanel = new JPanel();
        JPanel eastPanel = new JPanel();
        JPanel southPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JLabel mainLabel = new JLabel("LOGIN", SwingConstants.CENTER);
        JLabel emojiLabel = new JLabel(new ImageIcon(getClass().getResource("emoji.png")), JLabel.RIGHT);
        JLabel lockLabel = new JLabel(new ImageIcon(getClass().getResource("lock.png")), JLabel.RIGHT);
        usernameField = new PlaceholderTextField();
        usernameField.setPlaceholder("Username");
        passwordField = new PlaceholderPasswordField();
        passwordField.setPlaceholder("Password");
        JButton signInButton = new JButton("Sign in");
        signInButton.setPreferredSize(new Dimension(165 ,30));
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(165, 30));

        Color mainColor = new Color(88, 119, 235);

        this.setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        this.setBounds(width/2 - 200, height/2 - 250, 400, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        northPanel.setBackground(mainColor);
        northPanel.setPreferredSize(new Dimension(400, 110));
        northPanel.setLayout(new GridBagLayout());
        mainLabel.setFont(new Font("OPPO Sans", Font.ITALIC, 35));
        mainLabel.setForeground(Color.WHITE);
        northPanel.add(mainLabel);
        this.add(northPanel, BorderLayout.NORTH);

        southPanel.setBackground(mainColor);
        southPanel.setPreferredSize(new Dimension(400, 75));
        this.add(southPanel, BorderLayout.SOUTH);

        westPanel.setBackground(mainColor);
        westPanel.setPreferredSize(new Dimension(88, 350));
        westPanel.setLayout(new GridLayout(9, 1));
        westPanel.add(emojiLabel);
        westPanel.add(new JLabel());
        westPanel.add(lockLabel);
        this.add(westPanel, BorderLayout.WEST);

        eastPanel.setBackground(mainColor);
        eastPanel.setPreferredSize(new Dimension(88, 350));
        this.add(eastPanel, BorderLayout.EAST);

        centerPanel.setBackground(mainColor);
        centerPanel.setPreferredSize(new Dimension(50, 50));
        centerPanel.setLayout(new GridLayout(9,1));
        centerPanel.add(usernameField);
        usernameCommentLabel = new JLabel();
        usernameCommentLabel.setForeground(Color.RED);
        centerPanel.add(usernameCommentLabel);
        centerPanel.add(passwordField);
        serverResponseLabel = new JLabel();
        serverResponseLabel.setForeground(Color.RED);
        centerPanel.add(serverResponseLabel);
        centerPanel.add(new JLabel());
        JPanel signInHolder = new JPanel();
        signInHolder.setBackground(mainColor);
        signInHolder.add(signInButton);
        centerPanel.add(signInHolder);
        centerPanel.add(new JLabel());
        centerPanel.add(new JLabel());
        JPanel registerHolder = new JPanel();
        registerHolder.setBackground(mainColor);
        registerHolder.add(registerButton);
        centerPanel.add(registerHolder);
        this.add(centerPanel);


        //functionality
        this.connection = connection;
        registerButton.addActionListener(e -> goToRegister());
        signInButton.addActionListener(e -> loginOnServer());
    }

    private void goToRegister(){
        setVisible(false);
        RegisterFrame registerFrame = new RegisterFrame();
        registerFrame.setVisible(true);
    }

    private void loginOnServer(){
        String username = usernameField.getText();
        if(username.equals("")){
            usernameCommentLabel.setText("Username can not be empty");
        }
        else{
            String password = new String(passwordField.getPassword());
            connection.setUsername(username);
            connection.setPassword(password);
            try {
                MusicBandResponse response = connection.sendCommand("login");
                if(response.status == ResponseStatus.FAIL){
                    serverResponseLabel.setText("Authorization failed");
                }
                else{
                    serverResponseLabel.setForeground(Color.GREEN);
                    serverResponseLabel.setText("Success");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
