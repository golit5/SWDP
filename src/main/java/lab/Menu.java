package lab;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static Menu instance = null;
    private Visitor visitor;
    private Scanner scanner;
    private DrinkFactory drinkFactory;
    private String manufacturer;
    public static Menu getInstance(){
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }
    List<Class<? extends DrinkFactory>> factories = new ArrayList<Class<? extends DrinkFactory>>();
    List<String> selections = new ArrayList<String>();
    private Menu(){
        factories.add(BlackTeaFactory.class);
        factories.add(CoffeeWithMilkFactory.class);
        factories.add(GreenTeaFactory.class);
        selections.add("You've selected black tea. Please enter the manufacturer (Lipton or other):");
        selections.add("You've selected coffee. Please enter the manufacturer (Nescafe or other):");
        selections.add("You've selected green tea. Please enter the manufacturer (Lipton or other):");
    }
    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }
    public void greet(){
        System.out.println("Welcome to the drink menu!");
    }
    public void advise(){
        System.out.println("Please select your drinks (1 for black tea, 2 for coffee, 3 for green tea, 0 for exit):");
    }
    public void printTotalCost(){
        System.out.println("Total cost: $" + visitor.getTotalCost());
    }
    public boolean choose() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            Handler handler = new ParseHandler();
            handler.setNext(new IntHandler());
            String schoice = scanner.next();
            if (!handler.handle(schoice)) {
                return true;
            }
            int choice = Integer.parseInt(schoice);
            if (choice == 0)
                return false;
            drinkFactory = factories.get(choice - 1).getConstructor().newInstance();
            System.out.println(selections.get(choice - 1));
            manufacturer = scanner.next();
            visitor.visit(drinkFactory, manufacturer);
            return true;
        }

}
