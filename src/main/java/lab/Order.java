package lab;

public class Order {
    public Client client;
    public Drink drink;

    public Order(Client client, Drink drink){
        this.client = client;
        this.drink = drink;
    }
}
