package models;
import java.util.ArrayList;

public class UserRegistration {
    private int userId;
    private String username;
    private String userPassword;
    private String userEmail;
    private ArrayList<Order> orderHistory;

    public UserRegistration(int userId, String username, String userPassword, String userEmail) {
        this.userId = userId;
        this.username = username;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.orderHistory = new ArrayList<>();
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getUserPassword() { return userPassword; }
    public String getUserEmail() { return userEmail; }
    public ArrayList<Order> getOrderHistory() { return orderHistory; }
    public void addOrder(Order order) { orderHistory.add(order); }
}
