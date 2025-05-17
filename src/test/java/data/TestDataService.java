package data;

import model.Product;
import model.Customer;
import java.util.Arrays;
import java.util.List;

/**
 * Service for providing test product and customer data.
 * This class centralizes test data loading and management.
 */
public class TestDataService {
    
    /**
     * Get a list of financial products for demonstration.
     * @return List of Product objects
     */
    public static List<Product> getProducts() {
        return Arrays.asList(
            new Product("US Treasury Bond", 1200.0, "FixedIncome"),
            new Product("Apple Stock", 800.0, "Equity"),
            new Product("S&P 500 ETF", 150.0, "ETF"),
            new Product("Gold Futures", 200.0, "Derivative"),
            new Product("Corporate Bond", 100.0, "FixedIncome")
        );
    }
    
    /**
     * Get a list of financial products for inventory demonstration.
     * @return List of Product objects representing inventory
     */
    public static List<Product> getInventory() {
        return Arrays.asList(
            new Product("US Treasury Bond", 1200.0, "FixedIncome"),
            new Product("Apple Stock", 800.0, "Equity"),
            new Product("S&P 500 ETF", 150.0, "ETF"),
            new Product("Gold Futures", 200.0, "Derivative"),
            new Product("Corporate Bond", 100.0, "FixedIncome"),
            new Product("Microsoft Stock", 350.0, "Equity"),
            new Product("Bitcoin ETF", 450.0, "ETF")
        );
    }
    
    /**
     * Get a customer for demonstration.
     * @return Customer object
     */
    public static Customer getCustomer() {
        return new Customer("Alice Smith", 35, "Gold", Arrays.asList("Equity", "ETF"));
    }
    
    /**
     * Get a customer for template demonstration.
     * @return Customer object
     */
    public static Customer getTemplateCustomer() {
        return new Customer("Bob Johnson", 42, "Silver", Arrays.asList("Equity", "ETF"));
    }
}