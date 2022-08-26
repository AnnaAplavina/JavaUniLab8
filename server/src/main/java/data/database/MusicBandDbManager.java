package data.database;

import collectionitems.MusicBand;
import collectionitems.WrongArgumentException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MusicBandDbManager {
    private static final Logger logger = Logger.getLogger(MusicBandDbManager.class.getName());
//    public String testConnection(){
//        try{
//            Class.forName("org.postgresql.Driver");
//        }
//        catch (ClassNotFoundException ex){
//            ex.printStackTrace();
//        }
//        String res = "";
//        try{
//            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs",
//                    "s264432", "ajf870");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT str_field FROM test_table");
//            while (resultSet.next()){
//                res+= resultSet.getString("str_field");
//            }
//        }
//        catch (SQLException ex){
//            ex.printStackTrace();
//        }
//        return res;
//    }

    private Statement statement;
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
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                MusicBand band = new MusicBand();
                band.setId(id);
                try {
                    band.setName(name);
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
