package priceConventions;

public class PricePerUnit extends Price {
    public PricePerUnit(double p) {
        this.price = p;
    }

    @Override
    public String toString() {
        return String.format("%.2f", price) + " RON";
    }
}
