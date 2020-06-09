package databaseServices;

import config.DatabaseConfiguration;
import productCategories.ProductCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDatabaseService {
    private static CategoryDatabaseService single_instance = null;

    private ArrayList<ProductCategory> categories;
    private CategoryDatabaseService() {
        try {
            this.categories = this.loadFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            this.categories = new ArrayList<>();
        }
    }

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public List<ProductCategory> saveToDatabase(ProductCategory category) throws SQLException {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        PreparedStatement stmt = databaseConnection.prepareStatement("INSERT INTO product_categories (name) " +
                "VALUES (?)");
        stmt.setString(1, category.getName());
        stmt.executeUpdate();
        return loadFromDatabase();
    }

    private ArrayList<ProductCategory> loadFromDatabase() throws SQLException {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Statement stmt = databaseConnection.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM product_categories;");
        ArrayList<ProductCategory> categories = new ArrayList<>();
        while(rs.next()) {
            categories.add(new ProductCategory(rs.getInt(1), rs.getString(2)));
        }
        return categories;
    }

    public static CategoryDatabaseService getInstance()
    {
        if (single_instance == null) {
            single_instance = new CategoryDatabaseService();
        }

        return single_instance;
    }
}
