package gui.frames;

import collectionitems.MusicGenre;
import connection.MusicBandConnection;
import connection.MusicBandResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CountLesserGenreFrame extends JFrame implements ActionListener {
    private Container container;
    private JLabel genreLabel;
    private JComboBox genreComboBox;
    private JButton submitButton;

    private String[] genres = {"soul", "blues", "punk rock", "post punk", "brit pop"};

    private MusicBandConnection connection;

    public CountLesserGenreFrame(String title, MusicBandConnection connection){
        this.connection = connection;
        setTitle(title);
        setBounds(300, 90, 300, 130);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);

        genreLabel = new JLabel("Genre");
        genreLabel.setSize(100, 20);
        genreLabel.setLocation(10, 20);
        container.add(genreLabel);

        genreComboBox = new JComboBox(genres);
        genreComboBox.setSize(100, 20);
        genreComboBox.setLocation(120, 20);
        container.add(genreComboBox);

        submitButton = new JButton("Submit");
        submitButton.setSize(100, 20);
        submitButton.setLocation(80, 50);
        submitButton.addActionListener(this);
        container.add(submitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String genre = (String)genreComboBox.getSelectedItem();
        MusicBandResponse response = null;
        try{
            switch (genre){
                case "soul": response = connection.sendCommand("count_less_than_genre", "soul"); break;
                case "blues": response =connection.sendCommand("count_less_than_genre", "blues"); break;
                case "punk rock": response = connection.sendCommand("count_less_than_genre", "punk rock"); break;
                case "post punk": response = connection.sendCommand("count_less_than_genre", "post punk"); break;
                case "brit pop": response = connection.sendCommand("count_less_than_genre", "brit pop"); break;
            }
            JOptionPane.showMessageDialog(null, response.response);
        } catch (IOException|ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
}
