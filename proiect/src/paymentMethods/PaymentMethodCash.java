package paymentMethods;

public class PaymentMethodCash extends PaymentMethod {
    double handledCash;
    double change;

    public PaymentMethodCash(double handledCash, double totalPaid) {
        this.handledCash = handledCash;
        this.totalPaid = totalPaid;
        this.change = handledCash - totalPaid;
    }

    public double getChange() {
        return change;
    }

    public double getHandledCash() {
        return handledCash;
    }

    @Override
    public String toString() {
        return String.format("Platit cash cu %.2f RON, rest %.2f RON. Total platit %.2f RON.", handledCash, change, totalPaid);
    }
}
