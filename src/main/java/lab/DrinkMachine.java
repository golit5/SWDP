package lab;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DrinkMachine implements Runnable{
    private final int machineId;
    private static int idCounter = 0;
    private static int queueMax = 10;
    private static int queueSize = 0;
    private static final Lock queueLock = new ReentrantLock();
    private static final Deque<Order> orders = new ArrayDeque<Order>();

    public DrinkMachine() {
        this.machineId = idCounter++;
    }

    public static int getQueueSize() {
        return queueSize;
    }

    public static void incQueueSize() {
        DrinkMachine.queueSize++;
    }

    public static void decQueueSize() {
        DrinkMachine.queueSize--;
    }

    public static Lock getQueueLock() {
        return queueLock;
    }

    public static Deque<Order> getOrders() {
        return orders;
    }

    public static int getQueueMax(){
        return queueMax;
    }

    public static void setQueueMax(int queueMax) {
        DrinkMachine.queueMax = queueMax;
    }

    public int getMachineId() {
        return machineId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Client.getClientSem().tryAcquire()) {
                    queueLock.lock();
                    Order order = orders.removeFirst();
                    --queueSize;
                    queueLock.unlock();
                    Thread.sleep(500);
                    System.out.println(order.drink + " was given to the client " + order.client.getClientId());
                } else {
                    System.out.println("Machine " + (machineId + 1) + " is shleeping -_-zzz");
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("MACHINE " + (machineId + 1) + " WAS INTERRUPTED BRO");
        }
    }


}
