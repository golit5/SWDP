package lab;


class CreditCardStrategy implements PaymentStrategy {
    private final String cardNumber;

    public CreditCardStrategy(String cardNumber){this.cardNumber = cardNumber;}

    @Override
    public String getPaymentDetails(){return "credit card #" + cardNumber;}

    @Override
    public void pay(double amount) {
       return;
    }
}
