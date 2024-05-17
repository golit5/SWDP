package lab;

public class SoulPaymentStrategy implements PaymentStrategy {

    @Override
    public String getPaymentDetails(){return "YOUR SOUL";}

    @Override
    public void pay(double amount) {
        return;
    }
}
