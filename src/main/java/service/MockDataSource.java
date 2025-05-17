package service;

import data.MockDataSources;
import model.Customer;
import model.Product;
import model.Trade;

import java.util.List;

/**
 * A data source implementation that wraps the MockDataSources class.
 * This provides backward compatibility with existing code while
 * implementing the DataSource interface.
 */
public class MockDataSource implements DataSource {
    private final String name;
    private final String dataType;

    /**
     * Create a new MockDataSource with the specified name and data type.
     *
     * @param name The name of the data source
     * @param dataType The type of data this source provides
     */
    public MockDataSource(String name, String dataType) {
        this.name = name;
        this.dataType = dataType;
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
        if (!supportsDataType(dataType)) {
            return null;
        }

        switch (dataType) {
            case "products":
                return (T) MockDataSources.getProducts();
            case "inventory":
                return (T) MockDataSources.getInventory();
            case "customer":
                return (T) MockDataSources.getCustomer();
            case "templateCustomer":
                return (T) MockDataSources.getTemplateCustomer();
            case "lookupServices":
                return (T) MockDataSources.createLookupServices();
            case "sourceRecords":
                return (T) MockDataSources.createSourceRecords();
            case "matchingRecords":
                if (parameters.length >= 2 && parameters[0] instanceof List && parameters[1] instanceof List) {
                    List<Trade> sourceTrades = (List<Trade>) parameters[0];
                    List<LookupService> lookupServices = (List<LookupService>) parameters[1];
                    return (T) MockDataSources.findMatchingRecords(sourceTrades, lookupServices);
                }
                break;
            case "nonMatchingRecords":
                if (parameters.length >= 2 && parameters[0] instanceof List && parameters[1] instanceof List) {
                    List<Trade> sourceTrades = (List<Trade>) parameters[0];
                    List<LookupService> lookupServices = (List<LookupService>) parameters[1];
                    return (T) MockDataSources.findNonMatchingRecords(sourceTrades, lookupServices);
                }
                break;
        }

        return null;
    }
}