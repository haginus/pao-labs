package databaseServices;

import config.DatabaseConfiguration;
import priceConventions.PricePerQuantity;
import products.Product;

import java.sql.*;
import java.util.HashMap;

public class ProductDatabaseService
{
    private static ProductDatabaseService single_instance = null;
    HashMap<String, Product> products;

    private ProductDatabaseService() {
        try {
            this.products = this.loadFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            this.products = new HashMap<>();
        }
    }

    public HashMap<String, Product> getProducts() {
        return products;
    }

    public HashMap<String, Product> addToDatabase(Product product) throws SQLException {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        PreparedStatement stmt = databaseConnection.prepareStatement("INSERT INTO products VALUES (?, ?, ?, ?); ");
        stmt.setString(1, product.getBarcode());
        stmt.setString(2, product.getName());
        stmt.setDouble(3, product.getPrice().price);
        String unit = product.getPrice() instanceof PricePerQuantity ? ((PricePerQuantity) product.getPrice()).measureUnit : null;
        stmt.setString(4, unit);
        stmt.executeUpdate();
        return loadFromDatabase();
    }

    public HashMap<String, Product> editInDatabase(Product product) throws SQLException {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        PreparedStatement stmt = databaseConnection.prepareStatement("UPDATE products SET name = ?, price = ?, unit = ?" +
                " WHERE barcode = ?;");
        stmt.setString(4, product.getBarcode());
        stmt.setString(1, product.getName());
        stmt.setDouble(2, product.getPrice().price);
        String unit = product.getPrice() instanceof PricePerQuantity ? ((PricePerQuantity) product.getPrice()).measureUnit : null;
        stmt.setString(3, unit);
        stmt.executeUpdate();
        return loadFromDatabase();
    }

    public HashMap<String, Product> deleteFromDatabaseByBarcode(String barcode) throws SQLException {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        PreparedStatement stmt = databaseConnection.prepareStatement("DELETE FROM products WHERE barcode = ?;");
        stmt.setString(1, barcode);
        stmt.executeUpdate();
        return loadFromDatabase();
    }

    public HashMap<String, Product> loadFromDatabase() throws SQLException {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Statement stmt = databaseConnection.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM products;");
        HashMap<String, Product> products = new HashMap<>();
        while(rs.next()) {
            Product product;
            String unit = rs.getString(4);
            if(unit == null) product = new Product(rs.getString(1), rs.getString(2), rs.getDouble(3));
            else product = new Product(rs.getString(1), rs.getString(2), rs.getDouble(3), unit);
            products.put(product.getBarcode(), product);
        }
        return products;
    }

    public static ProductDatabaseService getInstance()
    {
        if (single_instance == null)
            single_instance = new ProductDatabaseService();

        return single_instance;
    }


}