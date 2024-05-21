package lab;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Menu {
    private static Menu instance = null;
    private ShoppingCart cart;
    private Scanner scanner;
    private Handler handler;

    List<Class<? extends DrinkFactory>> factories = new ArrayList<Class<? extends DrinkFactory>>();
    List<String> selections = new ArrayList<String>();
    List<Class<? extends PaymentStrategy>> payments = new ArrayList<Class<? extends PaymentStrategy>>();

    public static Menu getInstance(){
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }
    public Menu(){
        factories.add(BlackTeaFactory.class);
        factories.add(CoffeeWithMilkFactory.class);
        factories.add(GreenTeaFactory.class);
        selections.add("You've selected black tea. Please enter the manufacturer (Lipton or other):");
        selections.add("You've selected coffee. Please enter the manufacturer (Nescafe or other):");
        selections.add("You've selected green tea. Please enter the manufacturer (Lipton or other):");
        payments.add(DebitCardStrategy.class);
        payments.add(CreditCardStrategy.class);
        payments.add(SoulPaymentStrategy.class);
        instance = this;
    }

    public void setCart(ShoppingCart cart){ this.cart = cart; }
    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }
    public void setHandler(Handler handler){
        this.handler = handler;
    }
    public void greet(){
        System.out.println("Welcome to the drink menu!");
    }
    public void pay(PaymentStrategy ps){
        cart.pay(ps);
    }
    public void advise(){
        System.out.println("Please select your drinks (1 for black tea, 2 for coffee, 3 for green tea, 0 for exit):");
    }
    public void printTotalCost(){
        System.out.println("Total cost: $" + cart.calculateTotal());
    }
    public boolean choose() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            String schoice = scanner.next();
            if (!handler.handle(schoice)) {
                return true;
            }
            int choice = Integer.parseInt(schoice);
            if (choice == 0)
                return false;
        DrinkFactory drinkFactory = factories.get(choice - 1).getConstructor().newInstance();
            System.out.println(selections.get(choice - 1));
        String manufacturer = scanner.next();
            Main.user.addDrink(cart.addDrink(drinkFactory, manufacturer));
            return true;
        }

    public Class<? extends PaymentStrategy> choosePayment() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println("Select your payment method:\n1. Credit Card\n2. Debit Card\nOther input means you are giving away your soul!");
        String pchoice = scanner.next();
        handler.setNext(null);
        if(handler.handle(pchoice)) {
            int choice = Integer.parseInt(pchoice);
            if (choice > 0 && choice < 3) {
                return payments.get(choice - 1);
            }
        }
        return payments.get(2);
    }
}
