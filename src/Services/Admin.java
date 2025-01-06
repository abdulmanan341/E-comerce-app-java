package Services;

import db.DBConnection;
import models.Product;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
    public void addProduct(Product product) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO products (prodName, prodPrice, prodDescription, prodStockQuantity) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, product.getProdName());
                statement.setDouble(2, product.getProdPrice());
                statement.setString(3, product.getProdDescription());
                statement.setInt(4, product.getProdStockQuantity());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayProducts() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM products";

            try (Statement stmt = con.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(query);

                while (resultSet.next()) {
                    Product product = new Product(
                        resultSet.getInt("prodId"),
                        resultSet.getString("prodName"),
                        resultSet.getDouble("prodPrice"),
                        resultSet.getString("prodDescription"),
                        resultSet.getInt("prodStockQuantity")
                    );
                    product.displayProductDetails();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductStock(int productId, int newStockQuantity) {
        try (Connection con = DBConnection.getConnection()) {
            String query = "UPDATE products SET prodStockQuantity = ? WHERE prodId = ?";

            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setInt(1, newStockQuantity);
                statement.setInt(2, productId);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Product stock updated successfully.");
                } else {
                    System.out.println("No product found with the given ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
