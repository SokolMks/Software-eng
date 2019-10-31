package DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Catherine on 19/03/2018.
 */
public class DatabaseConnection {
    private static Connection connection = null;

    private DatabaseConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://catherine-softeng.czifjng6sesv.eu-west-2.rds.amazonaws.com:3306/Restaurant?autoReconnect=true&useSSL=false", "catherine", "catherine");
            System.out.println("Connected");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) new DatabaseConnection();
        return connection;
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}