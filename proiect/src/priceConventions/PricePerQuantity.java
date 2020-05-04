package priceConventions;

public class PricePerQuantity extends Price {
    public String measureUnit;
    public PricePerQuantity(double p, String unit) {
        this.price = p;
        this.measureUnit = unit;
    }

    @Override
    public String toString() {
        return String.format("%.2f", price) + " RON/" + measureUnit;
    }
}
