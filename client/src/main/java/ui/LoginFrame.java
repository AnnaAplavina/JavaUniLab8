package ui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    JPanel northPanel = new JPanel();
    JPanel westPanel = new JPanel();
    JPanel eastPanel = new JPanel();
    JPanel southPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JLabel mainLabel = new JLabel("LOGIN", SwingConstants.RIGHT);

    public LoginFrame(){
        this.setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        this.setBounds(width/2 - 200, height/2 - 250, 400, 500);

        northPanel.setBackground(new Color(88, 119, 235));
        northPanel.setPreferredSize(new Dimension(400, 75));
        northPanel.setLayout(new BorderLayout());
        northPanel.add(mainLabel);
        this.add(northPanel, BorderLayout.NORTH);

        southPanel.setBackground(new Color(88, 119, 235));
        southPanel.setPreferredSize(new Dimension(400, 75));
        this.add(southPanel, BorderLayout.SOUTH);

        westPanel.setBackground(new Color(88, 119, 235));
        westPanel.setPreferredSize(new Dimension(88, 350));
        this.add(westPanel, BorderLayout.WEST);

        eastPanel.setBackground(new Color(88, 119, 235));
        eastPanel.setPreferredSize(new Dimension(88, 350));
        this.add(eastPanel, BorderLayout.EAST);

        centerPanel.setBackground(new Color(88, 119, 235));
        centerPanel.setPreferredSize(new Dimension(50, 50));
        this.add(centerPanel);
    }
}
