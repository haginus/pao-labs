import databaseServices.AuditService;
import databaseServices.CategoryDatabaseService;
import databaseServices.OrderDatabaseService;
import databaseServices.ProductDatabaseService;
import order.Order;
import productCategories.ProductCategory;
import products.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CashRegister {
    private HashMap<String, Product> products;
    private HashMap<Integer, Order> orders;
    private Order currentOrder;
    private List<ProductCategory> categories;
    private ProductDatabaseService productDatabaseService = ProductDatabaseService.getInstance();
    private OrderDatabaseService orderDatabaseService = OrderDatabaseService.getInstance();
    private CategoryDatabaseService categoryDatabaseService = CategoryDatabaseService.getInstance();
    private AuditService auditService = AuditService.getInstance();

    public void listLogs() {
        auditService.listLogs();
    }

    public void addCategory(String name) {
        ProductCategory category = new ProductCategory(0, name);
        try {
            categories = categoryDatabaseService.saveToDatabase(category);
        } catch (SQLException e) {
            System.out.println("Nu s-a putut salva.");
        }
        auditService.log("new_category");
    }

    public void listCategories() {
        for(ProductCategory category : categories) {
            System.out.println(category.getName());
        }
    }

    public boolean addProduct(String barcode, String name, double price, String... unit) {
        Product product;
        if(unit.length == 1) product = new Product(barcode, name, price, unit[0]);
        else product = new Product(barcode, name, price);
        if(this.products.containsKey(barcode))
            return false;
        try {
            this.products = productDatabaseService.addToDatabase(product);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nu s-a putut salva în baza de date.");
        }
        auditService.log("new_product");
        return true;
    }

    public boolean editProduct(Product product) {
        try {
            this.products = productDatabaseService.editInDatabase(product);
            auditService.log("edit_product");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nu s-a putut salva în baza de date.");
            return false;
        }
    }

    public boolean deleteProduct(String barcode) {
        try {
            this.products = productDatabaseService.deleteFromDatabaseByBarcode(barcode);
            auditService.log("delete_product");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void listProducts() {
        StringBuilder str = new StringBuilder();
        for(Product product : products.values()) {
            str.append(product.toString());
        }
        System.out.println(str);
    }

    public Product getProduct(String barcode) {
        return this.products.getOrDefault(barcode, null);
    }

    public void listOrders() {
        StringBuilder str = new StringBuilder();
        for(Order order : orders.values()) {
            str.append(order.toString());
        }
        System.out.println(str);
    }

    public void createNewOrder() {
        this.currentOrder = new Order(0);
    }

    public boolean addToOrder(String barcode, double quantity) {
        Product product = getProduct(barcode);
        if(product != null) {
            this.currentOrder.addItem(product, quantity);
            return true;
        }
        return false;
    }

    public boolean removeFromOrder(String barcode) {
        Product product = getProduct(barcode);
        if(product != null) {
            return this.currentOrder.removeItem(product);
        }
        return false;
    }

    public void payCurrentOrderCash(double handled) {
        this.currentOrder.payOrderCash(handled);
        saveCurrentOrder();
    }

    public void payCurrentOrderCard(String cardNumber, String cardType) {
        this.currentOrder.payOrderCard(cardNumber, cardType);
        saveCurrentOrder();
    }

    public void saveCurrentOrder() {
        try {
            this.orders = orderDatabaseService.addToDatabase(currentOrder);
            auditService.log("new_order");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Eroare.");
        }

    }

    public void discardCurrentOrder() {
        currentOrder = null;
    }

    public HashMap<Integer, Order> getOrders() {
        return orders;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public CashRegister() {
        this.categories = new ArrayList<ProductCategory>();
        this.products = new HashMap<String, Product>();
        this.orders = new HashMap<>();
        this.auditService.log("login");
        this.categories = this.categoryDatabaseService.getCategories();
        this.products = this.productDatabaseService.getProducts();
        this.orders = this.orderDatabaseService.getOrders();
    }
}
