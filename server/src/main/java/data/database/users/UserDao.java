package data.database.users;

import data.database.DaoInitializationException;
import data.database.QueryExecutionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDao {
    private static final Logger logger = Logger.getLogger(UserDao.class.getName());

    private final String tableName;
    private final Connection connection;

    public UserDao(String url, String login, String password, String tableName) throws DaoInitializationException {
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException ex){
            logger.info("Could not load the driver \n" + ex.getMessage());
            throw new DaoInitializationException("Could not load the driver \n" + ex.getMessage());
        }
        try{
            this.tableName = tableName;
            connection = DriverManager.getConnection(url, login, password);
            createTableIfNotExists();
        }
        catch (SQLException ex){
            logger.info("Could not initialize MusicBandDao \n" + ex.getMessage());
            throw new DaoInitializationException("Could not initialize MusicBandDao \n" + ex.getMessage());
        }
    }

    public List<User> getAllUsers() throws QueryExecutionException {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM " + tableName;
            ResultSet resultSet = statement.executeQuery(query);
            List<User> users = new ArrayList<>();
            while (resultSet.next()){
                String username = resultSet.getString("username");
                String encryptedPass = resultSet.getString("encrypted_pass");
                User user = new User(username, encryptedPass);
                users.add(user);
            }
            return users;
        }
        catch (SQLException ex){
            logger.info("Could not retrieve users from table " + tableName + ex.getMessage());
            throw new  QueryExecutionException("Could not retrieve users from table " + tableName + ex.getMessage());
        }
    }


    public void addUser(User user) throws QueryExecutionException {
        try {
            String query = "INSERT INTO " + tableName +
                    "(username, encrypted_pass) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEncryptedPass());
            preparedStatement.executeUpdate();
            logger.info("Added new user to the table " + tableName);
        }
        catch (SQLException ex){
            logger.info("Could not add user to database " + ex.getMessage());
            throw new QueryExecutionException("Could not add user to database " + ex.getMessage());
        }
    }

    private void createTableIfNotExists() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(username TEXT NOT NULL," +
                "encrypted_pass TEXT NOT NULL)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}