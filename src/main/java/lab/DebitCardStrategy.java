package lab;

public class DebitCardStrategy implements PaymentStrategy{
    private final String cardNumber;

    public DebitCardStrategy(String cardNumber){this.cardNumber = cardNumber;}

    @Override
    public String getPaymentDetails(){return cardNumber;}

    @Override
    public void pay(double amount) {
        return;
    }
}
