package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {

    private static final String URL = ConfigReader.get("db.url"); // DB URL
    private static final String USER = ConfigReader.get("db.username");// DB username
    private static final String PASSWORD = ConfigReader.get("db.password"); // DB password

    // ✅ This method creates the connection using the above details
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}