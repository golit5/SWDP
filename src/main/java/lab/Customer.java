package lab;

public class Customer implements Runnable {

    private final int customerId;
    private final String drink;

    public Customer(int customerId, String drink) {
        this.customerId = customerId;
        this.drink = drink;
    }

    @Override
    public void run() {

    }
}
