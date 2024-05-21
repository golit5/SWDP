package lab;

import java.util.concurrent.Semaphore;

public class Client implements Runnable {
    private Drink drink;
    private final int clientId;
    private static int idCounter = 0;
    private static Semaphore clientSem = new Semaphore(DrinkMachine.getQueueMax());

    public Client(Drink drink) {
        this.drink = drink;
        clientId = idCounter++;
    }

    public int getClientId() {
        return clientId;
    }

    public Client() {
        clientId = idCounter++;
    }

    public static Semaphore getClientSem() {
        return clientSem;
    }

    public static void setClientSem(Semaphore clientSem) {
        Client.clientSem = clientSem;
        Client.clientSem.drainPermits();
    }

    public void addDrink(Drink drink){
        if(DrinkMachine.getQueueSize() < DrinkMachine.getQueueMax()) {
            DrinkMachine.getQueueLock().lock();
            DrinkMachine.incQueueSize();
            DrinkMachine.getOrders().addLast(new Order(this, drink));
            System.out.println("Client " + clientId + " is waiting to get a " + drink);
            DrinkMachine.getQueueLock().unlock();
            clientSem.release();
        } else {
            System.out.println(clientId + " couldn't order a " + drink + ", the queue is full!");
        }
    }

    @Override
    public void run() {
        addDrink(drink);
    }
}
