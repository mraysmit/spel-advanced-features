# DataServiceManager

The DataServiceManager is a service that manages a list of data sources for the RulesEngine or any other service to utilize. It abstracts away concrete data sources, such as the test data contained in the `data` package, and provides a flexible and configurable approach to data access.

## Key Features

- **Abstraction**: The DataServiceManager abstracts away concrete data sources, allowing for different data sources to be used interchangeably.
- **Configuration**: The DataServiceManager maintains an internal configuration of data sources that can be loaded at startup time or through the `loadDataSource` method.
- **Mediation**: The DataServiceManager acts as a mediator service to route requests from the RuleEngine or any other service for data.
- **Flexibility**: The DataServiceManager can accept requests for data sets and, using the configuration loaded at startup time or via the `loadDataSource` method, make this data available to be consumed by a requestor using the `requestData` method.

## Classes

### DataSource Interface

The `DataSource` interface defines the contract for data sources that can provide various types of data. It abstracts away the concrete implementation of data sources, allowing for different data sources to be used interchangeably.

```java
public interface DataSource {
    String getName();
    String getDataType();
    boolean supportsDataType(String dataType);
    <T> T getData(String dataType, Object... parameters);
}
```

### MockDataSource

The `MockDataSource` class is a data source implementation that wraps the `MockDataSources` class. This provides backward compatibility with existing code while implementing the `DataSource` interface.

```java
public class MockDataSource implements DataSource {
    // Implementation details...
}
```

### CustomDataSource

The `CustomDataSource` class is a custom data source implementation that provides data from in-memory collections. This demonstrates how to create a custom data source that implements the `DataSource` interface.

```java
public class CustomDataSource implements DataSource {
    // Implementation details...
}
```

### DataServiceManager

The `DataServiceManager` class is the main class that manages a list of data sources and provides methods to load, register, and request data from them.

```java
public class DataServiceManager {
    // Implementation details...
}
```

## Usage

### Basic Usage

```java
// Create and initialize the DataServiceManager
DataServiceManager dataServiceManager = new DataServiceManager();
dataServiceManager.initializeWithMockData();

// Get products
List<Product> products = dataServiceManager.requestData("products");

// Get customer
Customer customer = dataServiceManager.requestData("customer");
```

### Advanced Usage

```java
// Get source records and lookup services
List<Trade> sourceRecords = dataServiceManager.requestData("sourceRecords");
List<LookupService> lookupServices = dataServiceManager.requestData("lookupServices");

// Find matching records
List<Trade> matchingRecords = dataServiceManager.requestData("matchingRecords", sourceRecords, lookupServices);

// Find non-matching records
List<Trade> nonMatchingRecords = dataServiceManager.requestData("nonMatchingRecords", sourceRecords, lookupServices);
```

### Custom Data Sources

```java
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

// Get custom customer
Customer customCustomer = dataServiceManager.requestData("customCustomer");

// Get custom trades
List<Trade> customTrades = dataServiceManager.requestData("customTrades");
```

## Demo

The `DataServiceManagerDemo` class demonstrates how to use the DataServiceManager to access data. It shows how to replace direct calls to MockDataSources with calls to DataServiceManager, which provides a more flexible and configurable approach to data access.

```java
public class DataServiceManagerDemo {
    public static void main(String[] args) {
        demonstrateBasicUsage();
        demonstrateAdvancedUsage();
        demonstrateCustomDataSources();
    }
}
```