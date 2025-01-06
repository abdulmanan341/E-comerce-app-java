package models;

public class Product {
    private int prodId;
    private String prodName;
    private double prodPrice;
    private String prodDescription;
    private int prodStockQuantity;
    private int totalProdSold;

    public Product(int prodId, String prodName, double prodPrice, String prodDescription, int prodStockQuantity) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodDescription = prodDescription;
        this.prodStockQuantity = prodStockQuantity;
        this.totalProdSold = 0;
    }

    public int getProdId() { return prodId; }
    public String getProdName() { return prodName; }
    public double getProdPrice() { return prodPrice; }
    public String getProdDescription() { return prodDescription; }
    public int getProdStockQuantity() { return prodStockQuantity; }
    public int getTotalProdSold() { return totalProdSold; }

    public void increaseProductStockQuantity(int stockQuantity) {
        prodStockQuantity += stockQuantity;
    }

    public boolean reduceProductStockQuantity(int stockQuantity) {
        if (stockQuantity <= prodStockQuantity) {
            prodStockQuantity -= stockQuantity;
            totalProdSold += stockQuantity;
            return true;
        } else {
            System.out.println("No stock is available for " + prodName);
            return false;
        }
    }


    public void displayProductDetails() {
        System.out.println("\nProduct ID: " + prodId + " | Product Name: " + prodName +
                " | Product Price: $" + prodPrice + " | Stock Quantity: " + prodStockQuantity +
                " | Product Sold: " + totalProdSold + " | Product Description: " + prodDescription);
    }
}
