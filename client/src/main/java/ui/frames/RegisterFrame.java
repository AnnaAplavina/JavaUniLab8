package ui.frames;

import connection.MusicBandConnection;
import connection.MusicBandResponse;
import connection.ResponseStatus;
import ui.components.PlaceholderPasswordField;
import ui.components.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RegisterFrame extends JFrame {

    private PlaceholderTextField usernameField = new PlaceholderTextField();
    private PlaceholderPasswordField passwordField1 = new PlaceholderPasswordField();
    private PlaceholderPasswordField passwordField2 = new PlaceholderPasswordField();
    private JLabel usernameCommentLabel = new JLabel();
    private JLabel passwordCommentLabel = new JLabel();
    private MusicBandConnection connection;

    public RegisterFrame(MusicBandConnection connection){
        this.connection = connection;

        //design
        JPanel northPanel = new JPanel();
        JPanel westPanel = new JPanel();
        JPanel eastPanel = new JPanel();
        JPanel southPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JLabel mainLabel = new JLabel("REGISTER", SwingConstants.CENTER);
        JLabel emojiLabel = new JLabel(new ImageIcon(getClass().getResource("emoji.png")), JLabel.RIGHT);
        JLabel lockLabel1 = new JLabel(new ImageIcon(getClass().getResource("lock.png")), JLabel.RIGHT);
        JLabel lockLabel2 = new JLabel(new ImageIcon(getClass().getResource("lock.png")), JLabel.RIGHT);
        usernameField.setPlaceholder("Username");
        passwordField1.setPlaceholder("Password");
        passwordField2.setPlaceholder("Repeat password");
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
        westPanel.add(new JLabel());
        westPanel.add(lockLabel1);
        westPanel.add(new JLabel());
        westPanel.add(lockLabel2);
        this.add(westPanel, BorderLayout.WEST);

        eastPanel.setBackground(mainColor);
        eastPanel.setPreferredSize(new Dimension(88, 350));
        this.add(eastPanel, BorderLayout.EAST);

        centerPanel.setBackground(mainColor);
        centerPanel.setPreferredSize(new Dimension(50, 50));
        centerPanel.setLayout(new GridLayout(9,1));
        centerPanel.add(usernameField);
        usernameCommentLabel.setForeground(Color.RED);
        centerPanel.add(usernameCommentLabel);
        centerPanel.add(new JLabel());
        centerPanel.add(passwordField1);
        centerPanel.add(new JLabel());
        centerPanel.add(passwordField2);
        passwordCommentLabel.setForeground(Color.RED);
        centerPanel.add(passwordCommentLabel);
        centerPanel.add(new JLabel());
        JPanel registerHolder = new JPanel();
        registerHolder.setBackground(mainColor);
        registerHolder.add(registerButton);
        centerPanel.add(registerHolder);
        this.add(centerPanel);

        //functionality
        registerButton.addActionListener((e)-> registerOnServer());
    }

    private void registerOnServer(){
        String username = usernameField.getText().trim();
        if(username.equals("")){
            usernameCommentLabel.setText("Username field can not be empty");
        }
        else {
            String password1 = new String(passwordField1.getPassword());
            String password2 = new String(passwordField2.getPassword());
            if(password1.equals("")){
                passwordCommentLabel.setText("Password field can not be empty");
            }
            else if(!password1.equals(password2)){
                passwordCommentLabel.setText("Passwords are not the same");
            }
            else{
                connection.setUsername(username);
                connection.setPassword(password1);
                try {
                    MusicBandResponse response = connection.sendCommand("register");
                    if(response.status == ResponseStatus.SUCCESS){
                        setVisible(false);
                        CollectionFrame collectionFrame = new CollectionFrame(username);
                        collectionFrame.setVisible(true);
                    }
                    else{
                        passwordCommentLabel.setText("This username is already used");
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
}
