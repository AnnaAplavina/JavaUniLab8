package gui.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BandFrame extends JFrame implements ActionListener {
    private Container container;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel xCoordinateLabel;
    private JTextField xCoordinateTextField;
    private JLabel yCoordinateLabel;
    private JTextField yCoordinateTextField;
    private JLabel participantsLabel;
    private JTextField participantsTextField;
    private JLabel albumsCountLabel;
    private JTextField albumsCountTextField;
    private JLabel descriptionLabel;
    private JTextArea descriptionTextArea;
    private JLabel genreLabel;
    private JComboBox genreComboBox;
    private JLabel bestAlbumLabel;
    private JTextField bestAlbumTextField;

    private JButton submitButton;
    private String[] genres = {"-", "soul", "blues", "punk rock", "post punk", "brit pop"};

    public BandFrame(String title){
        setTitle(title);
        setBounds(300, 90, 400, 400);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);

        nameLabel = new JLabel("Name");
        nameLabel.setSize(100, 20);
        nameLabel.setLocation(10, 20);
        container.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setSize(190, 20);
        nameTextField.setLocation(120, 20);
        container.add(nameTextField);

        xCoordinateLabel = new JLabel("X");
        xCoordinateLabel.setSize(100, 20);
        xCoordinateLabel.setLocation(10, 50);
        container.add(xCoordinateLabel);

        xCoordinateTextField = new JTextField();
        xCoordinateTextField.setSize(50, 20);
        xCoordinateTextField.setLocation(120, 50);
        container.add(xCoordinateTextField);

        yCoordinateLabel = new JLabel("Y");
        yCoordinateLabel.setSize(100, 20);
        yCoordinateLabel.setLocation(10, 80);
        container.add(yCoordinateLabel);

        yCoordinateTextField = new JTextField();
        yCoordinateTextField.setSize(50, 20);
        yCoordinateTextField.setLocation(120, 80);
        container.add(yCoordinateTextField);

        participantsLabel = new JLabel("Participants");
        participantsLabel.setSize(100, 20);
        participantsLabel.setLocation(10, 110);
        container.add(participantsLabel);

        participantsTextField = new JTextField();
        participantsTextField.setSize(50, 20);
        participantsTextField.setLocation(120, 110);
        container.add(participantsTextField);

        albumsCountLabel = new JLabel("Albums Count");
        albumsCountLabel.setSize(100, 20);
        albumsCountLabel.setLocation(10, 140);
        container.add(albumsCountLabel);

        albumsCountTextField = new JTextField();
        albumsCountTextField.setSize(50, 20);
        albumsCountTextField.setLocation(120, 140);
        container.add(albumsCountTextField);

        descriptionLabel = new JLabel("Description");
        descriptionLabel.setSize(100, 20);
        descriptionLabel.setLocation(10, 170);
        container.add(descriptionLabel);

        descriptionTextArea = new JTextArea();
        descriptionTextArea.setSize(200, 75);
        descriptionTextArea.setLocation(120, 170);
        descriptionTextArea.setLineWrap(true);
        container.add(descriptionTextArea);

        genreLabel = new JLabel("Genre");
        genreLabel.setSize(100, 20);
        genreLabel.setLocation(10, 255);
        container.add(genreLabel);

        genreComboBox = new JComboBox(genres);
        genreComboBox.setSize(100, 20);
        genreComboBox.setLocation(120, 255);
        container.add(genreComboBox);

        bestAlbumLabel = new JLabel("Best Album");
        bestAlbumLabel.setSize(100, 20);
        bestAlbumLabel.setLocation(10, 285);
        container.add(bestAlbumLabel);

        bestAlbumTextField = new JTextField();
        bestAlbumTextField.setSize(190, 20);
        bestAlbumTextField.setLocation(120, 285);
        container.add(bestAlbumTextField);

        submitButton = new JButton("Submit");
        submitButton.setSize(100, 20);
        submitButton.setLocation(80, 315);
        container.add(submitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
