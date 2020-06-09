package databaseServices;

import config.DatabaseConfiguration;
import order.Order;
import order.ProductItem;
import paymentMethods.PaymentMethod;
import paymentMethods.PaymentMethodCard;
import paymentMethods.PaymentMethodCash;
import products.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderDatabaseService
{
    private static OrderDatabaseService single_instance = null;
    private ProductDatabaseService productDatabaseService = ProductDatabaseService.getInstance();

    private HashMap<Integer, Order> orders;

    private OrderDatabaseService() {
        try {
            this.orders = loadFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            this.orders = new HashMap<>();
        }
    }

    public HashMap<Integer, Order> getOrders() {
        return orders;
    }

    public HashMap<Integer, Order> addToDatabase(Order order) throws SQLException {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        PreparedStatement stmt = databaseConnection.prepareStatement("INSERT INTO orders " +
                "(total_price, payment_method, card_number, card_type, handled_cash) " +
                "VALUES (?, ?, ?, ?, ?)");
        stmt.setDouble(1, order.getTotalPrice());
        if(order.getPaymentMethod() instanceof PaymentMethodCash) {
            stmt.setString(2, "cash");
            stmt.setString(3, null);
            stmt.setString(4, null);
            stmt.setDouble(5, ((PaymentMethodCash) order.getPaymentMethod()).getHandledCash());
        } else if(order.getPaymentMethod() instanceof PaymentMethodCard) {
            stmt.setString(2, "card");
            stmt.setString(3, ((PaymentMethodCard) order.getPaymentMethod()).getCardNumber());
            stmt.setString(4, ((PaymentMethodCard) order.getPaymentMethod()).getCardType());
        }
        stmt.executeUpdate();
        // get auto insert id
        Statement stmtOrderId = databaseConnection.createStatement();
        ResultSet rsOrderId = stmtOrderId.executeQuery("SELECT LAST_INSERT_ID();");
        rsOrderId.next();
        order.setId(rsOrderId.getInt(1));
        StringBuilder str = new StringBuilder();
        for(ProductItem productItem : order.getItems()) {
            str.append(" (");
            str.append(order.getId()).append(", ");
            str.append('"').append(productItem.getProduct().getBarcode()).append('"').append(", ");
            str.append(productItem.getQuantity());
            str.append(") \n");
        }
        String sqlString = "INSERT INTO order_products (order_id, product_barcode, quantity) VALUES \n" + str;
        Statement stmt2 = databaseConnection.createStatement();
        stmt2.executeUpdate(sqlString);
        return loadFromDatabase();
    }

    private HashMap<Integer, Order> loadFromDatabase() throws SQLException {

        HashMap<Integer, Order> orders = new HashMap<>();
        // ID, TOTAL_PRICE, PAYMENT_METHOD, CARD_NUMBER, CARD_TYPE, HANDLED_CASH
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Statement stmt = databaseConnection.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM orders;");
        while(rs.next()) {
            Order order;
            String _payment = rs.getString(3);
            PaymentMethod paymentMethod;
            if(_payment.equals("cash")) {
                paymentMethod = new PaymentMethodCash(rs.getInt(6), rs.getDouble(2));
            } else if(_payment.equals("card")) {
                paymentMethod = new PaymentMethodCard(rs.getString(4), rs.getString(5), rs.getDouble(2));
            } else throw new SQLException();
            PreparedStatement stmt2 = databaseConnection.prepareStatement("SELECT * FROM order_products WHERE order_id = ?;");
            stmt2.setInt(1, rs.getInt(1));
            ResultSet rs2 = stmt2.executeQuery();
            ArrayList<ProductItem> items = new ArrayList<>();
            HashMap<String, Product> products = productDatabaseService.getProducts();
            while(rs2.next()) {
                ProductItem item = new ProductItem(products.get(rs2.getString(3)), rs2.getDouble(4));
                items.add(item);
            }
            order = new Order(rs.getInt(1), rs.getDouble(2), items, paymentMethod);
            orders.put(order.getId(), order);
        }
        return orders;
    }

    public static OrderDatabaseService getInstance()
    {
        if (single_instance == null)
            single_instance = new OrderDatabaseService();

        return single_instance;
    }
}