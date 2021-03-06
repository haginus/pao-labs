package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConfiguration {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/register";
    private static final String USER = "register";
    private static final String PASSWORD = "register";
    private static Connection databaseConnection;

    private DatabaseConfiguration() { }

    public static Connection getDatabaseConnection() {
        try {
            if (databaseConnection == null || databaseConnection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                databaseConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                DatabaseSetup.setup();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return databaseConnection;
    }
    public static void closeDatabaseConnection() {
        try {
            if (databaseConnection != null && !databaseConnection.isClosed()) {
                databaseConnection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
