package data.database;

import collectionitems.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MusicBandDbManager {
    private static final Logger logger = Logger.getLogger(MusicBandDbManager.class.getName());

    private final Statement statement;
    private final String tableName;

    public MusicBandDbManager(String url, String login, String password, String tableName) throws DbManagerInitializationException {
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException ex){
            logger.info("Could not load the driver \n" + ex.getMessage());
            throw new DbManagerInitializationException("Could not load the driver \n" + ex.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, login, password);
            statement = connection.createStatement();
            this.tableName = tableName;
        }
        catch (SQLException ex){
            logger.info("Could not initialize MusicBandDbManager \n" + ex.getMessage());
            throw new DbManagerInitializationException("Could not initialize MusicBandDbManager \n" + ex.getMessage());
        }
    }

    public List<MusicBand> getBandsFromDb() throws QueryExecutionException {
        List<MusicBand> bands = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            while (resultSet.next()){
                try {
                    MusicBand band = new MusicBand();

                    int id = resultSet.getInt("id");
                    band.setId(id);
                    String name = resultSet.getString("name");
                    band.setName(name);

                    Coordinates coordinates = new Coordinates();
                    coordinates.setX(resultSet.getFloat("x"));
                    coordinates.setY(resultSet.getFloat("y"));
                    band.setCoordinates(coordinates);

                    String dateStr = resultSet.getString("creation_date");
                    LocalDateTime creationDate = dateStr == null? null : LocalDateTime.parse(dateStr);
                    band.setCreationDate(creationDate);

                    int numberOfParticipants = resultSet.getInt("number_of_participants");
                    band.setNumberOfParticipants(numberOfParticipants);
                    long albumsCount = resultSet.getLong("albums_count");
                    band.setAlbumsCount(albumsCount);
                    String description = resultSet.getString("description");
                    band.setDescription(description);
                    String genreStr = resultSet.getString("genre");
                    if(genreStr != null){
                        MusicGenre genre = MusicGenre.valueOf(resultSet.getString("genre"));
                        band.setGenre(genre);
                    }

                    String bestAlbumName = resultSet.getString("best_album_name");
                    if(bestAlbumName != null){
                        Album bestAlbum = new Album();
                        bestAlbum.setName(bestAlbumName);
                        long bestAlbumTracks = resultSet.getLong("best_album_tracks");
                        bestAlbum.setTracks(bestAlbumTracks);
                        Integer bestAlbumLength = resultSet.getInt("best_album_length");
                        bestAlbum.setLength(bestAlbumLength);
                        Float bestAlbumSales = resultSet.getFloat("best_album_sales");
                        bestAlbum.setSales(bestAlbumSales);
                        band.setBestAlbum(bestAlbum);
                    }
                    bands.add(band);
                }
                catch (WrongArgumentException ex){
                    logger.info("Critical error when getting MusicBand object from db, incorrect field value in ds " +
                            ex.getMessage());
                    throw new QueryExecutionException("Error on server! Incorrect field value in database!");
                }
            }
        } catch (SQLException e) {
            throw new QueryExecutionException("Could not execute query to select all music bands \n" +
                    e.getMessage());
        }
        return bands;
    }
}
