package lab;

public class CreditCardStrategyProxy extends CreditCardStrategy {
    PaymentStrategy creditCard;

    public CreditCardStrategyProxy(String cardNumber) {
        super(cardNumber);
        this.creditCard = new CreditCardStrategy(cardNumber);
    }

    @Override
    public void visit(DrinkFactory drinkFactory, String manufacturer) {
        System.out.print("'visit' function reached, totalcost = ");
        System.out.println(creditCard.getTotalCost());
        creditCard.visit(drinkFactory, manufacturer);
        System.out.print("'visit' function exited, totalcost = ");
        System.out.println(creditCard.getTotalCost());
    }

    @Override
    public double getTotalCost() {
        System.out.println("'getTotalCost' method reached");
        return creditCard.getTotalCost();
    }

    @Override
    public void pay(double amount) {
        creditCard.pay(amount);
        System.out.println(amount + " paid with credit card " + creditCard.getCardNumber());
    }
}


