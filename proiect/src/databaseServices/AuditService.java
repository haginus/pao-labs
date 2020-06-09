package databaseServices;

import config.DatabaseConfiguration;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuditService {
    private static AuditService single_instance = null;

    private List<Pair<Timestamp, String>> logs;


    private AuditService() {
        try {
            this.loadFromDatabase();
        } catch (SQLException e) {
            System.out.println("saf");
            logs = new ArrayList<>();
        }
    }

    public void log(String action_name) {
        Pair<Timestamp, String> log = new Pair<>(new Timestamp(new Date().getTime()), action_name);
        logs.add(log);

        try {
            Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement stmt = databaseConnection.prepareStatement("INSERT INTO audit (action_name, timestamp) " +
                    "VALUES (?, ?)");
            stmt.setString(1, log.getValue());
            stmt.setTimestamp(2, log.getKey());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Log-ul nu a fost salvat!");
        }
    }

    public void listLogs() {
        for(Pair<Timestamp, String> log : logs) {
            System.out.println("Actiunea " + log.getValue() + " petrecuta la " + log.getKey());
        }
    }

    private void loadFromDatabase() throws SQLException {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Statement stmt = databaseConnection.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * from audit;");
        logs = new ArrayList<>();
        while(rs.next()) {
            logs.add(new Pair<>(rs.getTimestamp(3), rs.getString(2)));
        }
    }


    public static AuditService getInstance()
    {
        if (single_instance == null)
            single_instance = new AuditService();

        return single_instance;
    }
}
