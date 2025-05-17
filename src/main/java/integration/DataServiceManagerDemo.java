package integration;

import model.Customer;
import model.Product;
import model.Trade;
import service.CustomDataSource;
import service.DataServiceManager;
import service.LookupService;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates how to use the DataServiceManager to access data.
 * This class shows how to replace direct calls to MockDataSources with
 * calls to DataServiceManager, which provides a more flexible and
 * configurable approach to data access.
 */
public class DataServiceManagerDemo {

    /**
     * Demonstrates basic usage of the DataServiceManager.
     */
    public static void demonstrateBasicUsage() {
        System.out.println("\n=== Demonstrating DataServiceManager Basic Usage ===");

        // Create and initialize the DataServiceManager
        DataServiceManager dataServiceManager = new DataServiceManager();
        dataServiceManager.initializeWithMockData();

        // Get products
        List<Product> products = dataServiceManager.requestData("products");
        System.out.println("Products:");
        for (Product product : products) {
            System.out.println("  " + product.getName() + " - $" + product.getPrice() + " (" + product.getCategory() + ")");
        }

        // Get customer
        Customer customer = dataServiceManager.requestData("customer");
        System.out.println("\nCustomer:");
        System.out.println("  Name: " + customer.getName());
        System.out.println("  Age: " + customer.getAge());
        System.out.println("  Membership Level: " + customer.getMembershipLevel());
        System.out.println("  Preferred Categories: " + customer.getPreferredCategories());
    }

    /**
     * Demonstrates advanced usage of the DataServiceManager.
     */
    public static void demonstrateAdvancedUsage() {
        System.out.println("\n=== Demonstrating DataServiceManager Advanced Usage ===");

        // Create and initialize the DataServiceManager
        DataServiceManager dataServiceManager = new DataServiceManager();
        dataServiceManager.initializeWithMockData();

        // Get source records and lookup services
        List<Trade> sourceRecords = dataServiceManager.requestData("sourceRecords");
        List<LookupService> lookupServices = dataServiceManager.requestData("lookupServices");

        // Find matching records
        List<Trade> matchingRecords = dataServiceManager.requestData("matchingRecords", sourceRecords, lookupServices);
        System.out.println("Matching Records:");
        for (Trade trade : matchingRecords) {
            System.out.println("  " + trade.getId() + " - " + trade.getValue() + " (" + trade.getCategory() + ")");
        }

        // Find non-matching records
        List<Trade> nonMatchingRecords = dataServiceManager.requestData("nonMatchingRecords", sourceRecords, lookupServices);
        System.out.println("\nNon-Matching Records:");
        for (Trade trade : nonMatchingRecords) {
            System.out.println("  " + trade.getId() + " - " + trade.getValue() + " (" + trade.getCategory() + ")");
        }
    }

    /**
     * Demonstrates how to use custom data sources with the DataServiceManager.
     */
    public static void demonstrateCustomDataSources() {
        System.out.println("\n=== Demonstrating DataServiceManager with Custom Data Sources ===");

        // Create the DataServiceManager
        DataServiceManager dataServiceManager = new DataServiceManager();

        // Create and load custom data sources
        dataServiceManager.loadDataSources(
            new CustomDataSource("CustomProductsSource", "customProducts"),
            new CustomDataSource("CustomCustomerSource", "customCustomer"),
            new CustomDataSource("CustomTradesSource", "customTrades")
        );

        // Get custom products
        List<Product> customProducts = dataServiceManager.requestData("customProducts");
        System.out.println("Custom Products:");
        for (Product product : customProducts) {
            System.out.println("  " + product.getName() + " - $" + product.getPrice() + " (" + product.getCategory() + ")");
        }

        // Get custom customer
        Customer customCustomer = dataServiceManager.requestData("customCustomer");
        System.out.println("\nCustom Customer:");
        System.out.println("  Name: " + customCustomer.getName());
        System.out.println("  Age: " + customCustomer.getAge());
        System.out.println("  Membership Level: " + customCustomer.getMembershipLevel());
        System.out.println("  Preferred Categories: " + customCustomer.getPreferredCategories());

        // Get custom trades
        List<Trade> customTrades = dataServiceManager.requestData("customTrades");
        System.out.println("\nCustom Trades:");
        for (Trade trade : customTrades) {
            System.out.println("  " + trade.getId() + " - " + trade.getValue() + " (" + trade.getCategory() + ")");
        }

        // Demonstrate dynamic data modification
        System.out.println("\nDemonstrating Dynamic Data Modification:");

        // Get the custom data source by name
        CustomDataSource customTradesSource = (CustomDataSource) dataServiceManager.getDataSourceByName("CustomTradesSource");

        // Add a new trade
        List<Trade> updatedTrades = new ArrayList<>(customTrades);
        updatedTrades.add(new Trade("CT004", "New Custom Value", "Custom Category"));
        customTradesSource.addData("customTrades", updatedTrades);

        // Get the updated trades
        List<Trade> newCustomTrades = dataServiceManager.requestData("customTrades");
        System.out.println("Updated Custom Trades:");
        for (Trade trade : newCustomTrades) {
            System.out.println("  " + trade.getId() + " - " + trade.getValue() + " (" + trade.getCategory() + ")");
        }
    }

    /**
     * Main method to run the demo.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        demonstrateBasicUsage();
        demonstrateAdvancedUsage();
        demonstrateCustomDataSources();
    }
}
