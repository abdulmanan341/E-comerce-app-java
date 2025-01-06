package app;

import Services.UserManager;
import db.DBConnection;
import Services.Admin;
import Services.Payment;
import models.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ECommerceShoppingApp {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("-------------------- WELCOME TO E-COMMERCE SHOPPING APP --------------------");
        System.out.println("--------------------------- ENJOY YOUR SHOPPING ----------------------------");

        UserManager userManager = new UserManager();
        Admin admin = new Admin();
        ShoppingCartItems shoppingCartItems = new ShoppingCartItems();
        Payment payment = new Payment();

        // admin.addProduct(new Product(1, "IPHONE 15 pro max", 50.45, "Brand new phone with exceptional features", 4));
        // admin.addProduct(new Product(2, "LAPTOP", 30.43, "Hp elitebook core i7 10 generation specs", 5));
        // admin.addProduct(new Product(3, "Mens Gersey", 10.12, "Wool and cotton blend material", 3));
        // admin.addProduct(new Product(4, "T-shirts", 5.98, "For both men and women, high quality cotton", 3));
        // admin.addProduct(new Product(5, "Washing Machine", 200.99, "Front load washing machine with 7 kg capacity", 8));
        // admin.addProduct(new Product(6, "Blender", 25.00, "Electric blender for kitchen use", 10));
        // admin.addProduct(new Product(7, "Smartwatch", 75.55, "Smartwatch with fitness tracking features", 12));
        // admin.addProduct(new Product(8, "Air Conditioner", 350.00, "High-efficiency air conditioner, 1.5 ton", 4));
        // admin.addProduct(new Product(9, "Refrigerator", 500.00, "Energy-efficient double door refrigerator", 3));
        // admin.addProduct(new Product(10, "Microwave", 90.99, "Microwave with convection and grill features", 6));
        // admin.addProduct(new Product(11, "Headphones", 45.99, "Noise-canceling over-ear headphones", 15));
        // admin.addProduct(new Product(12, "Speakers", 60.00, "Bluetooth portable speakers", 9));
        // admin.addProduct(new Product(13, "Gaming Console", 300.00, "Next-gen gaming console with 4K resolution support", 7));
        // admin.addProduct(new Product(14, "Wireless Router", 40.00, "High-speed Wi-Fi router for home use", 5));
        // admin.addProduct(new Product(15, "Electric Kettle", 20.50, "Stainless steel electric kettle, 1.5L", 12));
        // admin.addProduct(new Product(16, "Dishwasher", 220.00, "Fully automatic dishwasher, 12 settings", 4));
        // admin.addProduct(new Product(17, "Digital Camera", 180.00, "Digital camera with 20MP resolution", 3));
        // admin.addProduct(new Product(18, "Smartphone", 120.00, "Mid-range smartphone with 6GB RAM", 10));
        // admin.addProduct(new Product(19, "Air Purifier", 150.00, "HEPA air purifier with filter replacement", 8));
        // admin.addProduct(new Product(20, "Power Bank", 15.99, "Portable power bank, 10,000 mAh", 20));
        // admin.addProduct(new Product(21, "Laptop Stand", 12.00, "Adjustable laptop stand for ergonomic use", 25));
        // admin.addProduct(new Product(22, "Gaming Mouse", 20.00, "Ergonomic gaming mouse with RGB lighting", 30));
        // admin.addProduct(new Product(23, "Gaming Keyboard", 40.00, "Mechanical gaming keyboard with RGB lighting", 15));
        // admin.addProduct(new Product(24, "Smartphone Case", 8.00, "Silicone case for iPhone", 35));
        // admin.addProduct(new Product(25, "TV", 400.00, "4K Smart TV, 55 inch", 2));
        // admin.addProduct(new Product(26, "Smart Bulb", 12.00, "Wi-Fi enabled LED smart bulb", 100));
        // admin.addProduct(new Product(27, "Digital Thermometer", 10.00, "Smart digital thermometer with LCD display", 50));
        // admin.addProduct(new Product(28, "Tablet", 150.00, "10-inch tablet, 64GB storage", 6));
        // admin.addProduct(new Product(29, "Projector", 250.00, "Portable mini projector with 1080p resolution", 4));
        // admin.addProduct(new Product(30, "Wristwatch", 30.00, "Stylish wristwatch for men and women", 20));

        boolean isLoggedIn = false;
        UserRegistration currentUser   = null;

        while (true) {
            if (isLoggedIn) {
                System.out.println("\n-------- What do you want to do! --------");
                System.out.println("1. View Products");
                System.out.println("2. View Cart");
                System.out.println("3. Add Product to Cart");
                System.out.println("4. Remove Product from Cart");
                System.out.println("5. Checkout");
                System.out.println("6. Logout");
                System.out.print("Enter your choice to proceed: ");

                int choice = sc.nextInt();
                sc.nextLine(); 

                switch (choice) {
                    case 1:
                        admin.displayProducts();
                        break;
                    case 2:
                        shoppingCartItems.displayShoppingCart();
                        break;
                    case 3:
                        System.out.print("Enter product ID: ");
                        int productId = sc.nextInt();
                        System.out.print("Enter quantity: ");
                        int quantity = sc.nextInt();
                        sc.nextLine();

                        Product product = getProductById(productId);
                        if (product != null) {
                            shoppingCartItems.addCartItems(product, quantity);
                            System.out.println("\nProduct added to cart!");
                        } else {
                            System.out.println("Product not found.");
                        }
                        break;
                    case 4:
                        System.out.print("Enter product ID to remove: ");
                        productId = sc.nextInt();
                        sc.nextLine();
                        
                        product = getProductById(productId);
                        if (product != null) {
                            shoppingCartItems.removeCartItems(product);
                            System.out.println("\nProduct removed from cart.");
                        } else {
                            System.out.println("Product not found.");
                        }
                        break;
                    case 5:
                        double totalAmount = shoppingCartItems.getTotalAmount();
                        System.out.println("\nTotal amount to pay: $" + totalAmount);
                        
                        System.out.print("\nEnter promo code (if any): ");
                        String promoCode = sc.nextLine();
                        Discount discount = new Discount("SAVE10", 10);

                        if (discount.isValid(promoCode)) {
                            discount.applyDiscount(totalAmount);
                            System.out.println("\nDiscount applied! New total amount: $" + totalAmount);
                        } else {
                            System.out.println("Invalid promo code.");
                        }

                        System.out.print("\nSelect payment method (Credit Card / Debit Card): ");
                        String paymentMethod = sc.nextLine();
                        
                        if (payment.processPayment(totalAmount, paymentMethod)) {
                            Order order = new Order(new ArrayList<>(shoppingCartItems.getCartItems()), totalAmount);
                            order.displayReceipt();
                            shoppingCartItems.clearShoppingCart();
                        } else {
                            System.out.println("Payment failed. Please try again.");
                        }
                        break;
                    case 6:
                        isLoggedIn = false;
                        System.out.println("Logged out successfully.");
                        System.out.println("Thanks for using our ---- E-COMMERCE SHOPPING APP ---- we hope you have enjoyed it!");
                        break;
                    default:
                        System.out.println("Invalid option, try again.");
                }
            } else {
                System.out.println("\n1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        userManager.userRegistration();
                        break;
                    case 2:
                        currentUser  = userManager.loginUser ();
                        if (currentUser  != null) {
                            isLoggedIn = true;
                            System.out.println("Successfully Logg in...");
                        }
                        break;
                    case 3: 
                        sc.nextLine();
                        System.out.println("Logging out.....");
                        System.out.println("Thanks for using our ---- E-COMMERCE SHOPPING APP ---- we hope you have enjoyed it!");
                        System.exit(0); 
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    private static Product getProductById(int productId) {
        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM products WHERE prodId = ?";

            try (PreparedStatement statement = con.prepareStatement(query)) {

                statement.setInt(1, productId);
                ResultSet resultSet = statement.executeQuery();
                
                if (resultSet.next()) {
                    return new Product(
                        resultSet.getInt("prodId"),
                        resultSet.getString("prodName"),
                        resultSet.getDouble("prodPrice"),
                        resultSet.getString("prodDescription"),
                        resultSet.getInt("prodStockQuantity")
                    );
                } else {
                    System.out.println("Product not found with ID: " + productId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
} 
