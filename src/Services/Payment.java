package Services;

public class Payment {
    public boolean processPayment(double amount, String paymentMethod) {
        System.out.println("\nProcessing payment of $" + amount + " using " + paymentMethod + "......");

        if (paymentMethod.equals("Credit Card") || paymentMethod.equals("Debit Card")) {
            System.out.println("Payment successful with " + paymentMethod + "!");
            return true;
        } else {
            System.out.println("Payment failed. Please try again.");
            return false;
        }
    }
}
