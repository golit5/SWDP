package lab;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    public static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("labContext.xml");
    public static Client user = new Client();
    public static List<Thread> clients;
    public static List<Thread> machines;
    private static final int QMAX = 10;
    private static final int CLIENTS = 2;
    private static final int MACHINES = 1;
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DrinkMachine.setQueueMax(QMAX);
        clients = new ArrayList<Thread>();
        for (int i = 0; i < CLIENTS; i++) {
            clients.add(new Thread(new Client()));
        }
        Client.setClientSem(new Semaphore(DrinkMachine.getQueueMax()));
        machines = new ArrayList<Thread>();
        for (int i = 0; i < MACHINES; i++) {
            machines.add(new Thread(new DrinkMachine()));
        }
        DrinkMachine.setMachineSem(new Semaphore(1));
        for (int i = 0; i < CLIENTS; i++) {
            clients.get(i).start();
        }
        for (int i = 0; i < MACHINES; i++) {
            machines.get(i).start();
        }
        Scanner scanner = new Scanner(System.in);
        Menu menu = context.getBean("menu", Menu.class);
        menu.setScanner(scanner);
        menu.greet();
        boolean flag = true;
        do {
            menu.advise();
            flag = menu.choose();
        }
        while (flag);
        menu.printTotalCost();
        Class<? extends PaymentStrategy> ps =
                menu.choosePayment();
        if(ps != SoulPaymentStrategy.class){
            System.out.println("Enter your card number: ");
            menu.pay(new PaymentStrategyProxy((PaymentStrategy) ps.getConstructors()[0].newInstance(scanner.next())));
        }
        else
            menu.pay(new PaymentStrategyProxy(ps.getConstructor().newInstance()));
        for (int i = 0; i < CLIENTS; i++) {
            clients.get(i).interrupt();
        }
        for (int i = 0; i < MACHINES; i++) {
            machines.get(i).interrupt();
        }
        return;
    }

}