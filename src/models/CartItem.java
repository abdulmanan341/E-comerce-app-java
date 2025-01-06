package models;

public class CartItem {
    private Product product;
    private int cartItemQuantity;

    public CartItem(Product product, int cartItemQuantity) {
        this.product = product;
        this.cartItemQuantity = cartItemQuantity;
    }

    public double getTotalPriceOfProduct() { return (product.getProdPrice()) * cartItemQuantity; }
    public Product getProductItems() { return product; }
    public int getCartItemQuantity() { return cartItemQuantity; }

    public void increaseCartItemQuantity(int quantity) { this.cartItemQuantity += quantity; }
    public void reduceCartItemQuantity(int quantity) {
        if (this.cartItemQuantity - quantity >= 0) {
            this.cartItemQuantity -= quantity;
        }
    }

    public void displayCartItem() {
        System.out.println(product.getProdName() + " x " + cartItemQuantity + " = $" + getTotalPriceOfProduct());
    }
}
