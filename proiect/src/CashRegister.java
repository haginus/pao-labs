import fileServices.AuditService;
import fileServices.CategoryFileService;
import fileServices.OrderFileService;
import fileServices.ProductFileService;
import order.Order;
import productCategories.ProductCategory;
import products.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CashRegister {
    private HashMap<String, Product> products;
    private List<Order> orders;
    private Order currentOrder;
    private List<ProductCategory> categories;
    private ProductFileService productFileService = ProductFileService.getInstance();
    private OrderFileService orderFileService = OrderFileService.getInstance();
    private CategoryFileService categoryFileService = CategoryFileService.getInstance();
    private AuditService auditService = AuditService.getInstance();

    public void listLogs() {
        auditService.listLogs();
    }

    public void addCategory(String name) {
        this.categories.add(new ProductCategory(this.categories.size() + 1, name));
        try {
            categoryFileService.saveToFile(this.categories);
        } catch (IOException e) {
            System.out.println("Nu s-a putut salva in fisier.");
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
        this.products.put(barcode, product);
        try {
            productFileService.saveToFile(this.products);
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println("Nu s-a putut salva in fisier.");
        }
        auditService.log("new_product");
        return true;
    }

    public boolean deleteProduct(String barcode) {
        if(products.containsKey(barcode)) {
            products.remove(barcode);
            auditService.log("delete_product");
            return true;
        }
        return false;
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

    public void createNewOrder() {
        this.currentOrder = new Order(orders.size() + 1);
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
            if(this.currentOrder.removeItem(product))
                return true;
            else return false;
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
        this.orders.add(currentOrder);
        try {
            orderFileService.saveToFile(this.orders);
        } catch (IOException e) {
            System.out.println("Eroare la scrierea in fisier.");
        }
        auditService.log("new_order");
    }

    public void discardCurrentOrder() {
        currentOrder = null;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public CashRegister() {
        this.categories = new ArrayList<ProductCategory>();
        this.products = new HashMap<String, Product>();
        this.orders = new ArrayList<Order>();
        this.auditService.log("login");
        try {
            this.categories = this.categoryFileService.loadFromFile();
        } catch (IOException e) {
            System.out.println("Eroare la citirea categoriilor!");
        }

        try {
            this.products = this.productFileService.loadFromFile();
        } catch (IOException e) {
            System.out.println("Eroare la citirea produselor!");
        }
        try {
            this.orders = this.orderFileService.loadFromFile(this.products);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Eroare la citirea comenzilor!");
        }

    }
}
