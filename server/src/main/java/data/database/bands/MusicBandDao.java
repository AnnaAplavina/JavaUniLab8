package data.database.bands;

import collectionitems.*;
import data.database.DaoInitializationException;
import data.database.QueryExecutionException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MusicBandDao {
    private static final Logger logger = Logger.getLogger(MusicBandDao.class.getName());

    private final Statement statement;
    private final Connection connection;
    private final String tableName;

    public MusicBandDao(String url, String login, String password, String tableName) throws DaoInitializationException {
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException ex){
            logger.info("Could not load the driver \n" + ex.getMessage());
            throw new DaoInitializationException("Could not load the driver \n" + ex.getMessage());
        }
        try {
            this.connection = DriverManager.getConnection(url, login, password);
            statement = connection.createStatement();
            this.tableName = tableName;
            createTableIfNotExists();
        }
        catch (SQLException ex){
            logger.info("Could not initialize MusicBandDao \n" + ex.getMessage());
            throw new DaoInitializationException("Could not initialize MusicBandDao \n" + ex.getMessage());
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
                    LocalDateTime creationDate = LocalDateTime.parse(dateStr);
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

    public void addBandToDb(MusicBand band) throws QueryExecutionException {
        String query = "INSERT INTO " + tableName +
                "(name,x,y," +
                "creation_date,number_of_participants,albums_count,description," +
                "genre,best_album_name,best_album_tracks,best_album_length,best_album_sales)"+
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, band.getName());
            preparedStatement.setFloat(2, band.getCoordinates().getX());
            preparedStatement.setFloat(3, band.getCoordinates().getY());
            preparedStatement.setDate(4, Date.valueOf(band.getCreationDate().toLocalDate()));
            preparedStatement.setInt(5, band.getNumberOfParticipants());
            preparedStatement.setLong(6, band.getAlbumsCount());
            preparedStatement.setString(7, band.getDescription());
            if(band.getGenre() != null){
                preparedStatement.setString(8, band.getGenre().toString());
            }
            else{
                preparedStatement.setNull(8, Types.VARCHAR);
            }
            if(band.getBestAlbum() != null){
                preparedStatement.setString(9, band.getBestAlbum().getName());
                preparedStatement.setFloat(10, band.getBestAlbum().getTracks());
                preparedStatement.setInt(11, band.getBestAlbum().getLength());
                preparedStatement.setFloat(12, band.getBestAlbum().getSales());
            }
            else {
                preparedStatement.setNull(9, Types.VARCHAR);
                preparedStatement.setNull(10, Types.FLOAT);
                preparedStatement.setNull(11, Types.INTEGER);
                preparedStatement.setNull(12, Types.FLOAT);
            }
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex){
            logger.info("Could no add new music band to database\n" + ex.getMessage());
            throw new QueryExecutionException("Could no add new music band to database\n" + ex.getMessage());
        }
    }

    public void removeBandById(int id) throws QueryExecutionException {
        try {
            statement.executeQuery("DELETE FROM " + tableName + " WHERE id=" + id);
        } catch (SQLException ex) {
            logger.info("Could not remove band from db\n" + ex.getMessage());
            throw new QueryExecutionException("Could not remove band from db\n" + ex.getMessage());
        }
    }

    public void changeBandById(int id, MusicBand band) throws QueryExecutionException {
        String query = "UPDATE TABLE " + tableName + " SET " +
                "name=?,x=?,y=?,creation_date=?,number_of_participants=?,albums_count=?,description=?," +
                "genre=?,best_album_name=?,best_album_tracks=?,best_album_length=?,best_album_sales=?" +
                "WHERE id=?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, band.getName());
            preparedStatement.setFloat(2, band.getCoordinates().getX());
            preparedStatement.setFloat(3, band.getCoordinates().getY());
            preparedStatement.setDate(4, Date.valueOf(band.getCreationDate().toLocalDate()));
            preparedStatement.setInt(5, band.getNumberOfParticipants());
            preparedStatement.setLong(6, band.getAlbumsCount());
            preparedStatement.setString(7, band.getDescription());
            if(band.getGenre() != null){
                preparedStatement.setString(8, band.getGenre().toString());
            }
            else{
                preparedStatement.setNull(8, Types.VARCHAR);
            }
            if(band.getBestAlbum() != null){
                preparedStatement.setString(9, band.getBestAlbum().getName());
                preparedStatement.setFloat(10, band.getBestAlbum().getTracks());
                preparedStatement.setInt(11, band.getBestAlbum().getLength());
                preparedStatement.setFloat(12, band.getBestAlbum().getSales());
            }
            else {
                preparedStatement.setNull(9, Types.VARCHAR);
                preparedStatement.setNull(10, Types.FLOAT);
                preparedStatement.setNull(11, Types.INTEGER);
                preparedStatement.setNull(12, Types.FLOAT);
            }
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex){
            logger.info("Could no add new music band to database\n" + ex.getMessage());
            throw new QueryExecutionException("Could no add new music band to database\n" + ex.getMessage());
        }
    }

    public void clear() throws QueryExecutionException {
        try{
            String query = "DELETE FROM " + tableName;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex){
            logger.info("Could no add new music band to database\n" + ex.getMessage());
            throw new QueryExecutionException("Could not clear the table" + ex.getMessage());
        }
    }

    private void createTableIfNotExists() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(id SERIAL PRIMARY KEY NOT NULL," +
                "name TEXT NOT NULL," +
                "x FLOAT(24) NOT NULL," +
                "y FLOAT(24) NOT NULL," +
                "creation_date DATE NOT NULL," +
                "number_of_participants INT NOT NULL," +
                "albums_count INT NOT NULL," +
                "description TEXT," +
                "genre TEXT," +
                "best_album_name TEXT," +
                "best_album_tracks INT," +
                "best_album_length INT," +
                "best_album_sales FLOAT(24))";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}
