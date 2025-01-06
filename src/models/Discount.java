package models;

public class Discount {
    private String promoCode;
    private double discountPercentage;

    public Discount(String promoCode, double discountPercentage) {
        this.promoCode = promoCode;
        this.discountPercentage = discountPercentage;
    }

    public String getPromoCode() { return promoCode; }
    public double getDiscountPercentage() { return discountPercentage; }
    public boolean isValid(String code) { return promoCode.equals(code); }

    public void applyDiscount(double totalAmount) {
        totalAmount -= totalAmount * (discountPercentage / 100);
    }
}

