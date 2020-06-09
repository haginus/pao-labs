package config;

import java.sql.*;

public class DatabaseSetup {
    public static void setup() throws SQLException {
        DatabaseSetup.setupTableProducts();
        DatabaseSetup.setupTableCategories();
        DatabaseSetup.setupTableOrders();
        DatabaseSetup.setupTableAudit();
    }

    private static void setupTableProducts() {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        String sql = "CREATE TABLE IF NOT EXISTS products ( barcode varchar(40) NOT NULL PRIMARY KEY, name varchar(40) NOT NULL, " +
                "price double NOT NULL, unit varchar(40));";
        try {
            Statement stmt = databaseConnection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setupTableCategories() {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        String sql = "CREATE TABLE IF NOT EXISTS product_categories ( id integer AUTO_INCREMENT PRIMARY KEY, name varchar(40) NOT NULL);";
        try {
            Statement stmt = databaseConnection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setupTableOrders() {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        String sql = "CREATE TABLE IF NOT EXISTS orders (\n" +
                "id integer AUTO_INCREMENT PRIMARY KEY,\n" +
                "total_price double NOT NULL,\n" +
                "payment_method varchar(40) NOT NULL,\n" +
                "card_number varchar(40),\n" +
                "card_type varchar(40),\n" +
                "handled_cash varchar(40)\n" +
                ");";
        String sql2 = "CREATE TABLE IF NOT EXISTS order_products (\n" +
                "id integer AUTO_INCREMENT PRIMARY KEY,\n" +
                "order_id integer NOT NULL,\n" +
                "product_barcode varchar(40) NOT NULL REFERENCES products(id) ON DELETE CASCADE,\n" +
                "quantity double NOT NULL,\n" +
                "FOREIGN KEY (order_id)\n" +
                    "REFERENCES orders(id) ON DELETE CASCADE,\n" +
                "FOREIGN KEY (product_barcode)\n" +
                    "REFERENCES products(barcode) ON DELETE CASCADE);";
        try {
            Statement stmt = databaseConnection.createStatement();
            stmt.execute(sql);
            stmt.execute(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setupTableAudit() {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        String sql = "CREATE TABLE IF NOT EXISTS audit ( id integer AUTO_INCREMENT PRIMARY KEY, action_name varchar(40) NOT NULL, timestamp timestamp NOT NULL);";
        try {
            Statement stmt = databaseConnection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
