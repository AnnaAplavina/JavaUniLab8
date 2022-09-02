package ui.frames;

import ui.components.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JPanel northPanel;
    private JPanel westPanel;
    private JPanel eastPanel;
    private JPanel southPanel;
    private JPanel centerPanel;
    private JLabel mainLabel;
    private JLabel emojiLabel;
    private JLabel lockLabel1;
    private JLabel lockLabel2;
    private PlaceholderTextField usernameField;
    private PlaceholderTextField passwordField1;
    private PlaceholderTextField passwordField2;
    private JButton registerButton;

    public RegisterFrame(){
        northPanel = new JPanel();
        westPanel = new JPanel();
        eastPanel = new JPanel();
        southPanel = new JPanel();
        centerPanel = new JPanel();
        mainLabel = new JLabel("REGISTER", SwingConstants.CENTER);
        emojiLabel = new JLabel(new ImageIcon(getClass().getResource("emoji.png")), JLabel.RIGHT);
        lockLabel1 = new JLabel(new ImageIcon(getClass().getResource("lock.png")), JLabel.RIGHT);
        lockLabel2 = new JLabel(new ImageIcon(getClass().getResource("lock.png")), JLabel.RIGHT);
        usernameField = new PlaceholderTextField();
        usernameField.setPlaceholder("Username");
        passwordField1 = new PlaceholderTextField();
        passwordField1.setPlaceholder("Password");
        passwordField2 = new PlaceholderTextField();
        passwordField2.setPlaceholder("Password");
        registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(165, 30));

        Color mainColor = new Color(88, 119, 235);

        this.setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        this.setBounds(width/2 - 200, height/2 - 250, 400, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

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
        centerPanel.add(new JLabel());
        centerPanel.add(new JLabel());
        centerPanel.add(passwordField1);
        centerPanel.add(new JLabel());
        centerPanel.add(passwordField2);
        centerPanel.add(new JLabel());
        centerPanel.add(new JLabel());
        JPanel registerHolder = new JPanel();
        registerHolder.setBackground(mainColor);
        registerHolder.add(registerButton);
        centerPanel.add(registerHolder);
        this.add(centerPanel);
    }
}
