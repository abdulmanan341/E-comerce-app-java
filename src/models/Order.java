package models;
import java.util.ArrayList;

public class Order {
    private ArrayList<CartItem> orderItems;
    private double totalAmount;

    public Order(ArrayList<CartItem> orderItems, double totalAmount) {
        this.orderItems = orderItems;
        this.totalAmount = totalAmount;
    }

    public void displayReceipt() {
        System.out.println("\n------------------ Receipt ------------------");
        for (CartItem item : orderItems) {
            System.out.println(item.getProductItems().getProdName() + " x " + item.getCartItemQuantity() +
                    " = $" + item.getTotalPriceOfProduct());
        }
        System.out.println("Total Price: $" + totalAmount);
        System.out.println(" ----------------------------------------------");
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void applyDiscount(Discount discount) {
        totalAmount -= totalAmount * (discount.getDiscountPercentage() / 100);
    }
}
