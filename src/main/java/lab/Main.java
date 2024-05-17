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
        System.out.println("Enter your credit card number: ");
        menu.pay(new CreditCardStrategyProxy(scanner.next()));


    }

}