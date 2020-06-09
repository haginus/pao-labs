package config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
    public static void main(String[] args) {
        Connection db = DatabaseConfiguration.getDatabaseConnection();
        try {
            DatabaseSetup.setup();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("select * from pet");
            while(rs.next())
                System.out.println(rs.getString(1)+"  "+rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        } */
    }
}
