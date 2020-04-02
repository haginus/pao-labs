public class ProductItem {
    private Product product;
    private double quantity;
    public double getPrice() {
        return product.getPrice().price * this.quantity;
    }

    public ProductItem(Product product, double quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%s | %.2f * %s = %.2f RON\n", product.getName(), quantity, product.getPrice(), getPrice());
    }
}
