package ui.frames;

import javax.swing.*;
import java.awt.*;

public class CollectionFrame extends JFrame {
    private JPanel mainPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel middlePanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JButton helpButton = new JButton();
    private JComboBox<String> languageComboBox = new JComboBox<>();
    private JLabel userLabel = new JLabel();

    public CollectionFrame(){
        Color mainColor = new Color(88, 119, 235);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        this.setBounds(width/2 - 600, height/2 - 400, 1200, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        //main holder
        add(mainPanel);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(mainColor);

        //header
        topPanel.setPreferredSize(new Dimension(1200, 90));
        topPanel.setBackground(mainColor);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        GridBagLayout headerGridBagLayout = new GridBagLayout();
        topPanel.setLayout(headerGridBagLayout);
        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerConstraints.weightx = 1180;
        headerConstraints.weighty = 96;
        headerConstraints.gridx = 0;
        headerConstraints.gridy = 0;
        headerConstraints.gridheight = 3;
        headerConstraints.gridwidth = 1;
        topPanel.add(new JLabel(), headerConstraints);
        headerConstraints.gridx = 1;
        headerConstraints.gridy = 0;
        headerConstraints.gridheight = 1;
        headerConstraints.gridwidth = 1;
        topPanel.add(new JLabel(), headerConstraints);
        helpButton.setText("Help");
        helpButton.setPreferredSize(new Dimension(273, 30));
        headerConstraints.gridx = 1;
        headerConstraints.gridy = 1;
        headerConstraints.gridheight = 1;
        headerConstraints.gridwidth = 1;
        topPanel.add(helpButton, headerConstraints);
        headerConstraints.gridx = 1;
        headerConstraints.gridy = 2;
        topPanel.add(new JLabel(), headerConstraints);
        headerConstraints.gridx = 2;
        headerConstraints.gridy = 0;
        topPanel.add(new JLabel(), headerConstraints);
        headerConstraints.gridx = 2;
        headerConstraints.gridy = 1;
        languageComboBox.addItem("English(Ireland)");
        languageComboBox.addItem("Русский");
        languageComboBox.addItem("Lietuvių");
        languageComboBox.addItem("Norsk");
        topPanel.add(languageComboBox, headerConstraints);
        languageComboBox.setPreferredSize(new Dimension(273, 30));
        headerConstraints.gridx = 2;
        headerConstraints.gridy = 2;
        topPanel.add(new JLabel(), headerConstraints);
        headerConstraints.gridx = 3;
        headerConstraints.gridy = 0;
        headerConstraints.gridheight = 3;
        headerConstraints.gridwidth = 1;
        userLabel.setText("USERNAME");
        ImageIcon userIcon = new ImageIcon(getClass().getResource("user.png"));
        userLabel.setIcon(userIcon);
        userLabel.setHorizontalTextPosition(JLabel.LEFT);
        userLabel.setFont(new Font("OPPO Sans", Font.ITALIC, 35));
        userLabel.setForeground(Color.WHITE);
        topPanel.add(userLabel, headerConstraints);

        topPanel.revalidate();

        middlePanel.setPreferredSize(new Dimension(1180, 450));
        JPanel leftMargin = new JPanel();
        leftMargin.setBackground(mainColor);
        leftMargin.setPreferredSize(new Dimension(10,450));
        JPanel rightMargin = new JPanel();
        rightMargin.setBackground(mainColor);
        leftMargin.setPreferredSize(new Dimension(10,450));
        mainPanel.add(leftMargin, BorderLayout.EAST);
        mainPanel.add(rightMargin, BorderLayout.WEST);
        mainPanel.add(middlePanel);

        bottomPanel.setPreferredSize(new Dimension(1200, 260));
        bottomPanel.setBackground(Color.YELLOW);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        bottomPanel.setLayout(new BorderLayout());
        JPanel leftMarginBottom = new JPanel();
        leftMarginBottom.setBackground(mainColor);
        leftMarginBottom.setPreferredSize(new Dimension(10, 260));
        bottomPanel.add(leftMarginBottom, BorderLayout.WEST);

    }
}
