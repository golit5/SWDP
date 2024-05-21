package lab;

import java.util.Stack;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DrinkMachine implements Runnable{
    private static Semaphore machineSem;
    private final Lock machineLock = new ReentrantLock();
    private static int queueMax = 10;
    private static int queueSize = 0;
    private static final Lock queueLock = new ReentrantLock();
    private static Stack<Order> orders = new Stack<Order>();

    public static Semaphore getMachineSem() {
        return machineSem;
    }

    public static void setMachineSem(Semaphore machineSem) {
        DrinkMachine.machineSem = machineSem;
        DrinkMachine.machineSem.release(Main.machines.size());
    }

    public static int getQueueSize() {
        return queueSize;
    }

    public static void incQueueSize() {
        ++DrinkMachine.queueSize;
    }

    public static void decQueueSize() {
        --DrinkMachine.queueSize;
    }

    public static Lock getQueueLock() {
        return queueLock;
    }

    public static Stack<Order> getOrders() {
        return orders;
    }

    public static int getQueueMax(){
        return queueMax;
    }

    public static void setQueueMax(int queueMax) {
        DrinkMachine.queueMax = queueMax;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Client.getClientSem().tryAcquire()) {
                    queueLock.lock();
                    Order order = orders.pop();
                    --queueSize;
                    queueLock.unlock();
                    System.out.println("Client " + order.client.getClientId() + " is waiting to get a " + order.drink);
                    machineLock.lock();
                    Thread.sleep(500);
                    System.out.println(order.drink + " was given to the client #" + order.client.getClientId());
                    machineLock.unlock();
                    machineSem.release();
                } else Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("DM WAS INTERRUPTED BRO");
        }
    }
}
