package lab;
import java.util.ArrayList;
import java.util.List;

// Посетитель, оценивающий стоимость заказа
class DrinkVisitor implements Visitor {
    private double totalCost = 0;

    @Override
    public void visit(DrinkFactory drinkFactory, String manufacturer) {
        Drink drink = drinkFactory.getDrink(manufacturer);
        totalCost += drink.calculateCost();
    }

    @Override
    public double getTotalCost() {
        return totalCost;
    }
}