package model;

import java.util.UUID;

/**
 * Represents an order with price, quantity, and status.
 */
public class Order {
    private final UUID id;
    private double price;
    private int quantity;
    private String status; // PENDING, SHIPPED, DELIVERED

    public Order(double price, int quantity, String status) {
        this.id = UUID.randomUUID();
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public double calculateTotal() {
        return price * quantity;
    }

    public UUID getId() {
        return id;
    }
}