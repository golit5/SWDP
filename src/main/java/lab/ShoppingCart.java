package lab;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCart {


    private final List<Drink> drinks;

    public ShoppingCart() {
        this.drinks = new ArrayList<>();
    }


    public Drink addDrink(DrinkFactory df, String manufacturer){
        addDrink(df.getDrink(manufacturer));
        return df.getDrink(manufacturer);
    }
    public void addDrink(Drink item) {
        drinks.add(item);
    }

    public void removeDrink(Drink item) {
        drinks.remove(item);
    }

    public double calculateTotal() {
        double sum = 0;
        for (Drink item : drinks) {
            sum += item.calculateCost();
        }
        return sum;
    }

    public void pay(PaymentStrategy paymentMethod) {
        double amount = calculateTotal();
        paymentMethod.pay(amount);
    }
}
