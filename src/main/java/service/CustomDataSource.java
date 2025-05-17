package service;

import model.Customer;
import model.Product;
import model.Trade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A custom data source implementation that provides data from in-memory collections.
 * This demonstrates how to create a custom data source that implements the DataSource interface.
 */
public class CustomDataSource implements DataSource {
    private static final Logger LOGGER = Logger.getLogger(CustomDataSource.class.getName());
    private final String name;
    private final String dataType;
    private final Map<String, Object> dataStore = new HashMap<>();

    /**
     * Create a new CustomDataSource with the specified name and data type.
     *
     * @param name The name of the data source
     * @param dataType The type of data this source provides
     */
    public CustomDataSource(String name, String dataType) {
        LOGGER.info("Creating CustomDataSource: " + name + " for data type: " + dataType);
        this.name = name;
        this.dataType = dataType;
        initializeData();
        LOGGER.fine("CustomDataSource initialized");
    }

    /**
     * Initialize the data store with some example data.
     */
    private void initializeData() {
        LOGGER.fine("Initializing data for data type: " + dataType);

        switch (dataType) {
            case "customProducts":
                LOGGER.fine("Creating custom products data");
                List<Product> products = new ArrayList<>();
                products.add(new Product("Custom Product 1", 100.0, "Custom"));
                products.add(new Product("Custom Product 2", 200.0, "Custom"));
                products.add(new Product("Custom Product 3", 300.0, "Custom"));
                dataStore.put(dataType, products);
                LOGGER.fine("Added " + products.size() + " custom products");
                break;

            case "customCustomer":
                LOGGER.fine("Creating custom customer data");
                Customer customer = new Customer("Custom Customer", 30, "Platinum", Arrays.asList("Custom", "Premium"));
                dataStore.put(dataType, customer);
                LOGGER.fine("Added custom customer: " + customer.getName());
                break;

            case "customTrades":
                LOGGER.fine("Creating custom trades data");
                List<Trade> trades = new ArrayList<>();
                trades.add(new Trade("CT001", "Custom Value 1", "Custom Category"));
                trades.add(new Trade("CT002", "Custom Value 2", "Custom Category"));
                trades.add(new Trade("CT003", "Custom Value 3", "Custom Category"));
                dataStore.put(dataType, trades);
                LOGGER.fine("Added " + trades.size() + " custom trades");
                break;

            default:
                LOGGER.info("No predefined data for data type: " + dataType);
                break;
        }

        LOGGER.fine("Data initialization completed for data type: " + dataType);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    @Override
    public boolean supportsDataType(String dataType) {
        return this.dataType.equals(dataType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getData(String dataType, Object... parameters) {
        LOGGER.fine("Getting data for data type: " + dataType);

        if (!supportsDataType(dataType)) {
            LOGGER.warning("Data type not supported: " + dataType);
            return null;
        }

        T data = (T) dataStore.get(dataType);
        LOGGER.fine("Retrieved data for data type: " + dataType + 
                    (data != null ? ", data found" : ", no data found"));
        return data;
    }

    /**
     * Add data to the data store.
     *
     * @param <T> The type of data
     * @param dataType The type of data
     * @param data The data to add
     */
    public <T> void addData(String dataType, T data) {
        LOGGER.info("Adding data for data type: " + dataType);

        if (data == null) {
            LOGGER.warning("Attempted to add null data for data type: " + dataType);
            return;
        }

        dataStore.put(dataType, data);
        LOGGER.fine("Data added for data type: " + dataType + ", data class: " + data.getClass().getSimpleName());
    }

    /**
     * Remove data from the data store.
     *
     * @param dataType The type of data to remove
     * @return The removed data, or null if no data was removed
     */
    @SuppressWarnings("unchecked")
    public <T> T removeData(String dataType) {
        LOGGER.info("Removing data for data type: " + dataType);

        T removedData = (T) dataStore.remove(dataType);

        if (removedData != null) {
            LOGGER.fine("Data removed for data type: " + dataType);
        } else {
            LOGGER.fine("No data found to remove for data type: " + dataType);
        }

        return removedData;
    }

    /**
     * Clear all data from the data store.
     */
    public void clearData() {
        LOGGER.info("Clearing all data from data store");
        int dataCount = dataStore.size();
        dataStore.clear();
        LOGGER.fine("Cleared " + dataCount + " data entries from data store");
    }
}
