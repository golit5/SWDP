package lab;
// Кофе
class Coffee implements Drink {
    private final double price;
    private final String manufacturer;

    public Coffee(String manufacturer, double price) {
        this.manufacturer = manufacturer;
        this.price = price;
    }

    @Override
    public double calculateCost() {
        if (manufacturer.equalsIgnoreCase("Nescafe")) {
            return price + 0.7; // Дополнительная цена за кофе Nescafe
        } else {
            return price;
        }
    }
}