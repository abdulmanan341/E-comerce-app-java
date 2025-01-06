package Services;

import db.DBConnection;
import models.UserRegistration;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
    private Scanner sc = new Scanner(System.in);

    public void userRegistration() {
        System.out.print("\nEnter the username: ");
        String username = sc.nextLine();
        System.out.print("Enter the password: ");
        String userPassword = sc.nextLine();
        System.out.print("Enter your email: ");
        String userEmail = sc.nextLine();

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ?";

            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    System.out.println("Username already exists! Please try another.");
                } else {
                    String hashedPassword = hashPassword(userPassword);
                    String insertQuery = "INSERT INTO users (username, userPassword, UserEmail) VALUES (?, ?, ?)";

                    try (PreparedStatement insertStatement = con.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, username);
                        insertStatement.setString(2, hashedPassword);
                        insertStatement.setString(3, userEmail);
                        insertStatement.executeUpdate();

                        System.out.println("\nHello, " + username + "! \nYou have successfully been registered!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserRegistration loginUser() {
        System.out.print("\nEnter the username: ");
        String username = sc.nextLine();
        System.out.print("Enter the password: ");
        String userPassword = sc.nextLine();

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? ";

            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String storedPasswordHash = resultSet.getString("userPassword");

                    if (storedPasswordHash.equals(hashPassword(userPassword))) {
                        System.out.println("\nWelcome Back, " + username + "!");
                        return new UserRegistration(resultSet.getInt("userId"), username, storedPasswordHash, resultSet.getString("userEmail"));
                    } else {
                        System.out.println("Invalid credentials. Please try again.");
                    }
                } else {
                    System.out.println("No user found with that username.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : encodedHash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
