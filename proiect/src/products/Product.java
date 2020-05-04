package products;

import priceConventions.Price;
import priceConventions.PricePerQuantity;
import priceConventions.PricePerUnit;

public class Product {
    private String barcode;
    private String name;
    Price price;

    public Product(String barcode, String name, double price) {
        this.barcode = barcode;
        this.name = name;
        this.price = new PricePerUnit(price);
    }

    public Product(String barcode, String name, double price, String unit) {
        this.barcode = barcode;
        this.name = name;
        this.price = new PricePerQuantity(price, unit);
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = new PricePerUnit(price);
    }

    public void setPrice(double price, String unit) {
        this.price = new PricePerQuantity(price, unit);
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return barcode + " | " + name + " | " + price.toString() + "\n";
    }
}






