package lab;
// Посетитель, который заказывает напитки
public interface PaymentStrategy {
    void visit(DrinkFactory drinkFactory, String manufacturer);
    double getTotalCost();
    String getCardNumber();
    void pay(double amount);
}