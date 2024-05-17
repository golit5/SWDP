package lab;


class CreditCardStrategy implements PaymentStrategy {
    private double totalCost = 0;
    private String cardNumber;

    public CreditCardStrategy(String cardNumber){this.cardNumber = cardNumber;}

    public String getCardNumber(){return cardNumber;}
    @Override
    public void visit(DrinkFactory drinkFactory, String manufacturer) {
        Drink drink = drinkFactory.getDrink(manufacturer);
        totalCost += drink.calculateCost();
    }

    @Override
    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public void pay(double amount) {
       return;
    }
}
