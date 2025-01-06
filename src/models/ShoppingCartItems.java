package models;

import java.util.ArrayList;

public class ShoppingCartItems {
    private ArrayList<CartItem> cartItems;

    public ShoppingCartItems() {
        this.cartItems = new ArrayList<>();
    }

    public void addCartItems(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProductItems().getProdId() == product.getProdId()) {
                item.increaseCartItemQuantity(quantity);
                return;
            }
        }
        cartItems.add(new CartItem(product, quantity));
    }

    public void removeCartItems(Product product) {
        cartItems.removeIf(item -> item.getProductItems().getProdId() == product.getProdId());
    }

    public void displayShoppingCart() {
        if (cartItems.isEmpty()) {
            System.out.println("\nYour shopping cart is empty.");
        } else {
            System.out.println("\n-------------------- Shopping Cart --------------------");
            double totalAmount = 0;
            for (CartItem item : cartItems) {
                item.displayCartItem();
                totalAmount += item.getTotalPriceOfProduct();
            }
            System.out.println("Total Amount: $" + totalAmount);
            System.out.println("------------------------------------------------------");
        }
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotalAmount() {
        double totalAmount = 0;
        for (CartItem item : cartItems) {
            totalAmount += item.getTotalPriceOfProduct();
        }
        return totalAmount;
    }

    public void clearShoppingCart() {
        cartItems.clear();
    }
}
