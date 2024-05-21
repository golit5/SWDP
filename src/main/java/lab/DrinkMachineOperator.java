package lab;

public class DrinkMachineOperator implements Runnable{
    private final DrinkMachine drinkMachine;
    Drink manufacturer;
    public DrinkMachineOperator(DrinkMachine drinkMachine) {
        this.drinkMachine = drinkMachine;
    }

    @Override
    public void run() {
        drinkMachine.operateMachine(manufacturer);
    }
}
