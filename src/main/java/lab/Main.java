package lab;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Main {
    public static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("labContext.xml");

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
        return;
    }

}