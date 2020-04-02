import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CashRegister {
    private HashMap<String, Product> products;
    private List<Order> orders;
    private Order currentOrder;


    public boolean addProduct(String barcode, String name, double price, String... unit) {
        Product product;
        if(unit.length == 1) product = new Product(barcode, name, price, unit[0]);
        else product = new Product(barcode, name, price);
        if(this.products.containsKey(barcode))
            return false;
        this.products.put(barcode, product);
        return true;
    }

    public boolean deleteProduct(String barcode) {
        if(products.containsKey(barcode)) {
            products.remove(barcode);
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
        this.currentOrder = new Order();
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
        this.products = new HashMap<String, Product>();
        this.orders = new ArrayList<Order>();
        /* Niste produse default */
        this.addProduct("001", "Coca-Cola 0.5L", 3.29);
        this.addProduct("002", "Pepsi 2.5L", 7.89);
        this.addProduct("003", "Mere", 5.23, "kg");
        this.addProduct("004", "Pere", 6.49, "kg");
    }
}
