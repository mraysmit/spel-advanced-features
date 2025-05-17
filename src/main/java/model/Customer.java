package model;

import java.util.List;
import java.util.UUID;

/**
 * Represents a customer with name, age, membership level, and preferred categories.
 */
public class Customer {
    private final UUID id;
    private String name;
    private int age;
    private String membershipLevel;
    private List<String> preferredCategories;

    /**
     * Constructor with all fields.
     * 
     * @param name The customer's name
     * @param age The customer's age
     * @param membershipLevel The customer's membership level (Basic, Silver, Gold)
     * @param preferredCategories The customer's preferred categories
     */
    public Customer(String name, int age, String membershipLevel, List<String> preferredCategories) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.age = age;
        this.membershipLevel = membershipLevel;
        this.preferredCategories = preferredCategories;
    }

    /**
     * Constructor without preferred categories.
     * 
     * @param name The customer's name
     * @param age The customer's age
     * @param membershipLevel The customer's membership level (Basic, Silver, Gold)
     */
    public Customer(String name, int age, String membershipLevel) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.age = age;
        this.membershipLevel = membershipLevel;
        this.preferredCategories = null;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getMembershipLevel() {
        return membershipLevel;
    }

    public List<String> getPreferredCategories() {
        return preferredCategories;
    }

    public UUID getId() {
        return id;
    }

    /**
     * Determines if the customer is eligible for a discount.
     * 
     * @return true if the customer is eligible for a discount, false otherwise
     */
    public boolean isEligibleForDiscount() {
        return "Gold".equals(membershipLevel) || "Silver".equals(membershipLevel) || age > 60;
    }
}
