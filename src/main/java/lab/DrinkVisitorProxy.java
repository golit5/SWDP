package lab;

import org.springframework.stereotype.Component;

@Component
public class DrinkVisitorProxy implements Visitor {
    Visitor drinkVisitor;

    public DrinkVisitorProxy() {
        this.drinkVisitor = new DrinkVisitor();
    }

    @Override
    public void visit(DrinkFactory drinkFactory, String manufacturer) {
        System.out.print("'visit' function reached, totalcost = ");
        System.out.println(drinkVisitor.getTotalCost());
        drinkVisitor.visit(drinkFactory, manufacturer);
        System.out.print("'visit' function exited, totalcost = ");
        System.out.println(drinkVisitor.getTotalCost());
    }

    @Override
    public double getTotalCost() {
        System.out.println("'getTotalCost' method reached");
        return drinkVisitor.getTotalCost();
    }
}