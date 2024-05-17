package lab;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        Visitor visitor = new DrinkVisitorProxy();
        Menu menu = Menu.getInstance();
        menu.setVisitor(visitor);
        menu.setScanner(scanner);
        menu.greet();
        boolean flag = true;
        do {
            menu.advise();
            flag = menu.choose();
        }
        while (flag);
        menu.printTotalCost();
    }
}
