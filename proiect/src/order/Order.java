package order;

import paymentMethods.PaymentMethod;
import paymentMethods.PaymentMethodCard;
import paymentMethods.PaymentMethodCash;
import products.Product;

import java.util.ArrayList;
import java.util.List;

public class Order {
    int id;
    double totalPrice = 0;
    List<ProductItem> items;
    PaymentMethod paymentMethod;

    public void addItem(ProductItem item) {
        totalPrice += item.getProduct().getPrice().price * item.getQuantity();
        for(ProductItem existingItem : items) {
            if(existingItem.getProduct() == item.getProduct()) {
                existingItem.setQuantity(item.getQuantity() + existingItem.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public boolean removeItem(Product product) {
        for(ProductItem existingItem : items) {
            if(existingItem.getProduct() == product) {
                totalPrice -= existingItem.getProduct().getPrice().price * existingItem.getQuantity();
                items.remove(existingItem);
                return true;
            }
        }
        return false;
    }

    public void payOrderCash(double handled) {
        this.paymentMethod = new PaymentMethodCash(handled, this.totalPrice);
    }

    public void payOrderCard(String cardNumber, String cardType) {
        this.paymentMethod = new PaymentMethodCard(cardNumber, cardType, this.totalPrice);
    }

    public void addItem(Product product, double quantity) {
        addItem(new ProductItem(product, quantity));
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<ProductItem> getItems() {
        return items;
    }

    public int getId() {
        return id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Order(int id) {
        this.id = id;
        this.items = new ArrayList<ProductItem>();
    }

    public Order(int id, double totalPrice, List<ProductItem> items, PaymentMethod paymentMethod) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.items = items;
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Comanda:\n");
        for(ProductItem item : items) {
            str.append(item.toString());
        }
        str.append(String.format("TOTAL: %.2f RON", totalPrice));
        if(paymentMethod != null) str.append("\n").append(paymentMethod);
        return new String(str);
    }

    public void setId(int id) {
        this.id = id;
    }
}
