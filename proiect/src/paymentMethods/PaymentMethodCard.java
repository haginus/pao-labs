package paymentMethods;

public class PaymentMethodCard extends PaymentMethod {
    String cardNumber;
    String cardType;

    public PaymentMethodCard(String cardNumber, String cardType, double totalPaid) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.totalPaid = totalPaid;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    @Override
    public String toString() {
        return String.format("Platit cu cardul %s %s. Total platit %.2f RON", cardType, cardNumber, totalPaid);
    }
}
