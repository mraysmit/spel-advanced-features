package model;

import java.util.UUID;

/**
 * Represents a financial product with name, price, and category.
 */
public class Product {
    private final UUID id;
    private String name;
    private double price;
    private String category;

    public Product(String name, double price, String category) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}
