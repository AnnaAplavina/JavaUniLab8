package gui.frames;

import collectionitems.*;
import connection.MusicBandConnection;
import connection.MusicBandResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
    private JCheckBox noAlbumCheckBox;
    private JLabel albumNameLabel;
    private JTextField albumNameTextField;
    private JLabel albumTracksLabel;
    private JTextField albumTracksTextField;

    private JLabel albumLengthLabel;

    private JTextField albumLengthTextField;

    private JLabel albumSalesLabel;
    private JTextField albumSalesTextField;

    private JButton submitButton;

    private MusicBand band = new MusicBand();
    private String[] genres = {"-", "soul", "blues", "punk rock", "post punk", "brit pop"};

    private final MusicBandConnection connection;

    public BandFrame(String title, MusicBandConnection connection){
        this.connection = connection;
        setTitle(title);
        setBounds(300, 90, 400, 600);
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

        noAlbumCheckBox = new JCheckBox("No Best Album");
        noAlbumCheckBox.setSize(250, 20);
        noAlbumCheckBox.setLocation(10, 285);
        noAlbumCheckBox.addActionListener(e -> {
            if(noAlbumCheckBox.isSelected()){
                albumNameTextField.setEnabled(false);
                albumTracksTextField.setEnabled(false);
                albumLengthTextField.setEnabled(false);
                albumSalesTextField.setEnabled(false);
            }
            else {
                albumNameTextField.setEnabled(true);
                albumTracksTextField.setEnabled(true);
                albumLengthTextField.setEnabled(true);
                albumSalesTextField.setEnabled(true);
            }
        });
        container.add(noAlbumCheckBox);

        albumNameLabel = new JLabel("Best Album Name");
        albumNameLabel.setSize(150, 20);
        albumNameLabel.setLocation(10, 315);
        container.add(albumNameLabel);

        albumNameTextField = new JTextField();
        albumNameTextField.setSize(190, 20);
        albumNameTextField.setLocation(150, 315);
        container.add(albumNameTextField);

        albumTracksLabel = new JLabel("Best Album Tracks");
        albumTracksLabel.setSize(150, 20);
        albumTracksLabel.setLocation(10, 345);
        container.add(albumTracksLabel);

        albumTracksTextField = new JTextField();
        albumTracksTextField.setSize(190, 20);
        albumTracksTextField.setLocation(150, 345);
        container.add(albumTracksTextField);

        albumLengthLabel = new JLabel("Best Album Length");
        albumLengthLabel.setSize(150, 20);
        albumLengthLabel.setLocation(10, 375);
        container.add(albumLengthLabel);

        albumLengthTextField = new JTextField();
        albumLengthTextField.setSize(190, 20);
        albumLengthTextField.setLocation(150, 375);
        container.add(albumLengthTextField);

        albumSalesLabel = new JLabel("Best Album Sales");
        albumSalesLabel.setSize(150, 20);
        albumSalesLabel.setLocation(10, 405);
        container.add(albumSalesLabel);

        albumSalesTextField = new JTextField();
        albumSalesTextField.setSize(190, 20);
        albumSalesTextField.setLocation(150, 405);
        container.add(albumSalesTextField);

        submitButton = new JButton("Submit");
        submitButton.setSize(100, 20);
        submitButton.setLocation(80, 435);
        submitButton.addActionListener(this);
        container.add(submitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean isCorrect = true;
        try{
            band.setName(nameTextField.getText());
        }
        catch (WrongArgumentException ex){
            JOptionPane.showMessageDialog(null, "Band name can not be empty");
            isCorrect = false;
        }
        if(xCoordinateTextField.getText().equals("") || yCoordinateTextField.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Empty coordinates");
            isCorrect = false;
        }
        Coordinates coordinates = new Coordinates();
        try{
            float x = Float.parseFloat(xCoordinateTextField.getText());
            coordinates.setX(x);
            float y = Float.parseFloat(yCoordinateTextField.getText());
            coordinates.setY(y);
        }
        catch (WrongArgumentException|NumberFormatException exception){
            JOptionPane.showMessageDialog(null, "Incorrect coordinates");
            isCorrect = false;
        }
        try{
            band.setNumberOfParticipants(Integer.parseInt(participantsTextField.getText()));
        }
        catch (WrongArgumentException ex){
            JOptionPane.showMessageDialog(null, "Number of participants must be greater than 0");
            isCorrect = false;
        }
        catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Number of participants must be a number");
            isCorrect = false;
        }
        try{
            band.setAlbumsCount(Integer.parseInt(albumsCountTextField.getText()));
        }
        catch (WrongArgumentException ex){
            JOptionPane.showMessageDialog(null, "Albums count must be greater than 0");
            isCorrect = false;
        }
        catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Albums count must be a number");
            isCorrect = false;
        }
        band.setDescription(descriptionTextArea.getText());
        String genre = (String)genreComboBox.getSelectedItem();
        switch (genre){
            case "soul": band.setGenre(MusicGenre.SOUL); break;
            case "blues": band.setGenre(MusicGenre.BLUES); break;
            case "punk rock": band.setGenre(MusicGenre.PUNK_ROCK); break;
            case "post punk": band.setGenre(MusicGenre.POST_PUNK); break;
            case "brit pop": band.setGenre(MusicGenre.BRIT_POP); break;
            case "-": band.setGenre(null); break;
        }
        if(!noAlbumCheckBox.isSelected()){
            Album album = new Album();
            try {
                album.setName(albumNameTextField.getText());
            }
            catch (WrongArgumentException ex){
                JOptionPane.showMessageDialog(null, "Best album name can not be empty");
                isCorrect = false;
            }
            try {
                album.setLength(Integer.parseInt(albumLengthTextField.getText()));
            }
            catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Best album length must be a number");
                isCorrect = false;
            }
            catch (WrongArgumentException ex){
                JOptionPane.showMessageDialog(null, "Best album length must be greater than 0 and can not be empty");
                isCorrect = false;
            }
            try {
                album.setTracks(Long.parseLong(albumTracksTextField.getText()));
            }
            catch (WrongArgumentException ex){
                JOptionPane.showMessageDialog(null, "Best album tracks must be greater than 0");
                isCorrect = false;
            }
            catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Best album tracks must be a number");
                isCorrect = false;
            }
            try {
                album.setSales(Float.parseFloat(albumSalesTextField.getText()));
                band.setBestAlbum(album);
            }
            catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Best album sales must be a number");
                isCorrect = false;
            }
            catch (WrongArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Best album sales must be greater than 0 and can not be empty");
                isCorrect = false;
            }
        }
        if(isCorrect){
            try {
                MusicBandResponse response = connection.sendCommand("add", null, band);
                System.out.println(response.response);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
