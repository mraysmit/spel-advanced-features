package model;

import java.util.UUID;

/**
 * Represents an order with customer information and order details.
 */
public class OrderInfo {
    private final UUID id;
    private String customerName;
    private double orderTotal;
    private int customerYears;

    /**
     * Constructor with all fields.
     * 
     * @param customerName The name of the customer
     * @param orderTotal The total amount of the order
     * @param customerYears The number of years the customer has been with the company
     */
    public OrderInfo(String customerName, double orderTotal, int customerYears) {
        this.id = UUID.randomUUID();
        this.customerName = customerName;
        this.orderTotal = orderTotal;
        this.customerYears = customerYears;
    }

    public UUID getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public int getCustomerYears() {
        return customerYears;
    }

    /**
     * Calculates the discount percentage based on order total and customer loyalty.
     * 
     * @return The discount percentage (0.0 to 0.15)
     */
    public double calculateDiscountPercentage() {
        if (orderTotal >= 1000) {
            return 0.15; // 15% discount for large orders
        } else if (customerYears >= 5) {
            return 0.10; // 10% discount for loyal customers
        } else if (customerYears == 0) {
            return 0.05; // 5% discount for new customers
        } else {
            return 0.0; // No discount
        }
    }

    /**
     * Calculates the final price after applying the discount.
     * 
     * @return The final price after discount
     */
    public double calculateFinalPrice() {
        return orderTotal * (1 - calculateDiscountPercentage());
    }
}