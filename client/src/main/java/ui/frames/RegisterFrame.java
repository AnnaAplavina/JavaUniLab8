package ui.frames;

import ui.components.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    public RegisterFrame(){
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
        PlaceholderTextField usernameField = new PlaceholderTextField();
        usernameField.setPlaceholder("Username");
        PlaceholderTextField passwordField1 = new PlaceholderTextField();
        passwordField1.setPlaceholder("Password");
        PlaceholderTextField passwordField2 = new PlaceholderTextField();
        passwordField2.setPlaceholder("Password");
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
