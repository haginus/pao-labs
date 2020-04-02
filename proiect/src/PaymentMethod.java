public class PaymentMethod {
    protected double totalPaid;
}

class PaymentMethodCash extends PaymentMethod {
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

class PaymentMethodCard extends PaymentMethod {
    String cardNumber;
    String cardType;

    public PaymentMethodCard(String cardNumber, String cardType, double totalPaid) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.totalPaid = totalPaid;
    }

    @Override
    public String toString() {
        return String.format("Platit cu cardul %s %s. Total platit %.2f RON", cardType, cardNumber, totalPaid);
    }
}
