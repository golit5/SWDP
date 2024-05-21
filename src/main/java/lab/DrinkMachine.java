package lab;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DrinkMachine {
    private final int seats;
    private final Semaphore waitingQueue;
    private final Lock machineLock;
    private final Condition machineReady;
    private boolean isMachineSleeping;
    private String currentDrink;

    public DrinkMachine(int numberOfSeats) {
        this.seats = numberOfSeats;
        this.waitingQueue = new Semaphore(seats);
        this.machineLock = new ReentrantLock();
        this.machineReady = machineLock.newCondition();
        this.isMachineSleeping = true;
    }
    public void serveDrink(int customerId, String drink, Drink manufacturer){
        try {
            if(waitingQueue.tryAcquire()) {
                System.out.println("Customer " + customerId + "is waiting to get " + manufacturer + "'s " + drink + ".");
                machineLock.lock();
                try {
                    if (isMachineSleeping){
                        isMachineSleeping = false;
                        currentDrink = drink;
                        machineReady.signal();
                        System.out.println("Customer " + customerId + "wakes up to the Drink Machen for " + manufacturer + "'s " + drink + "." );
                    } else {
                        currentDrink = drink;
                    }
                    System.out.println("Customer " + customerId + " is getting " + manufacturer + "'s " + drink + ".");

                } finally {
                    machineLock.unlock();
                    waitingQueue.release();
                }
            } else {
                System.out.println("Customer " + customerId + "leave because the queue is full.");
        }

    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();}
    }
    public void operateMachine(Drink manufacturer) {
        while (true){
            machineLock.lock();
            try {
                while (isMachineSleeping) {
                    try {
                        System.out.println("Drink Machine is sleeping.");
                        machineReady.await();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println("Drink Machine is preparing " + manufacturer + "'s " + currentDrink + "." );
                Thread.sleep(3000); // Делем вид, что напиток делается.
                System.out.println("Drink Machine finished serving " + manufacturer + "'s " + currentDrink + " and goes back to sleep.");
                isMachineSleeping = true;
        } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } finally {
                machineLock.unlock();
            }
            }
    }
}
