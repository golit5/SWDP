package lab;

public class ClientSpawner implements Runnable {

    @Override
    public void run() {
        while(true) {
            try {
                new Thread(new Client(new BlackTea("Greenfield", 1.0))).start();
                new Thread(new Client(new Coffee("Nescafe", 2.0))).start();
                new Thread(new Client(new GreenTea("Greenfield", 1.5))).start();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Client spawner interrupted");
                break;
            }
        }
    }
}
