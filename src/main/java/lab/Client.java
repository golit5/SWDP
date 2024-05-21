package lab;

import java.util.concurrent.Semaphore;

public class Client implements Runnable {
    private int clientId;
    private static int idCounter = 0;
    private static Semaphore clientSem = new Semaphore(DrinkMachine.getQueueMax());

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
            DrinkMachine.getOrders().push(new Order(this, drink));
            DrinkMachine.getQueueLock().unlock();
            clientSem.release();
        } else {
            System.out.println(getClientId() + " couldn't order a " + drink + ", the queue is full!");
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (DrinkMachine.getMachineSem().tryAcquire()) {
                    Thread.sleep(2000);
                    addDrink(new CoffeeWithMilk(new Coffee("Nescafe", 1.5)));
                    Thread.sleep(2000);
                    addDrink(new BlackTea("Greenfield", 1.0));
                    /*for (int i = 0; i < Main.machines.size(); i++) {
                        synchronized (Main.machines.get(i)) {
                            Main.machines.get(i).notify();
                        }
                    }*/
                } else Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Client was interrupted");
        }
    }
}
