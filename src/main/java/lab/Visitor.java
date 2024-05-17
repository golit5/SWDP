package lab;
// Посетитель, который заказывает напитки
interface Visitor {
    void visit(DrinkFactory drinkFactory, String manufacturer);
    double getTotalCost();
}