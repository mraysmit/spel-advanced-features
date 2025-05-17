package service;

import model.Customer;
import model.Product;
import model.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for DataServiceManager.
 */
public class DataServiceManagerTest {
    private DataServiceManager dataServiceManager;

    @BeforeEach
    public void setUp() {
        dataServiceManager = new DataServiceManager();
        dataServiceManager.initializeWithMockData();
    }

    @Test
    public void testGetProductsData() {
        // Test requesting data by type
        List<Product> products = dataServiceManager.requestData("products");
        assertNotNull(products);
        assertFalse(products.isEmpty());

        // Verify some product data
        boolean foundTreasuryBond = false;
        for (Product product : products) {
            if ("US Treasury Bond".equals(product.getName())) {
                foundTreasuryBond = true;
                assertEquals(1200.0, product.getPrice());
                assertEquals("FixedIncome", product.getCategory());
                break;
            }
        }
        assertTrue(foundTreasuryBond, "US Treasury Bond should be in the products list");
    }

    @Test
    public void testGetInventoryData() {
        // Test requesting data by name
        List<Product> inventory = dataServiceManager.requestDataByName("InventoryDataSource", "inventory");
        assertNotNull(inventory);
        assertFalse(inventory.isEmpty());

        // Verify some inventory data
        boolean foundBitcoinETF = false;
        for (Product product : inventory) {
            if ("Bitcoin ETF".equals(product.getName())) {
                foundBitcoinETF = true;
                assertEquals(450.0, product.getPrice());
                assertEquals("ETF", product.getCategory());
                break;
            }
        }
        assertTrue(foundBitcoinETF, "Bitcoin ETF should be in the inventory list");
    }

    @Test
    public void testGetCustomerData() {
        // Test requesting customer data
        Customer customer = dataServiceManager.requestData("customer");
        assertNotNull(customer);
        assertEquals("Alice Smith", customer.getName());
        assertEquals(35, customer.getAge());
        assertEquals("Gold", customer.getMembershipLevel());

        // Test requesting template customer data
        Customer templateCustomer = dataServiceManager.requestData("templateCustomer");
        assertNotNull(templateCustomer);
        assertEquals("Bob Johnson", templateCustomer.getName());
        assertEquals(42, templateCustomer.getAge());
        assertEquals("Silver", templateCustomer.getMembershipLevel());
    }

    @Test
    public void testGetLookupServicesData() {
        // Test requesting lookup services data
        List<LookupService> lookupServices = dataServiceManager.requestData("lookupServices");
        assertNotNull(lookupServices);
        assertFalse(lookupServices.isEmpty());

        // Verify some lookup service data
        boolean foundInstrumentTypes = false;
        for (LookupService lookupService : lookupServices) {
            if ("InstrumentTypes".equals(lookupService.getName())) {
                foundInstrumentTypes = true;
                List<String> values = lookupService.getLookupValues();
                assertTrue(values.contains("Equity"));
                assertTrue(values.contains("Bond"));
                break;
            }
        }
        assertTrue(foundInstrumentTypes, "InstrumentTypes lookup service should be in the list");
    }

    @Test
    public void testGetSourceRecordsData() {
        // Test requesting source records data
        List<Trade> sourceRecords = dataServiceManager.requestData("sourceRecords");
        assertNotNull(sourceRecords);
        assertFalse(sourceRecords.isEmpty());

        // Verify some source record data
        boolean foundEquityTrade = false;
        for (Trade trade : sourceRecords) {
            if ("T001".equals(trade.getId())) {
                foundEquityTrade = true;
                assertEquals("Equity", trade.getValue());
                assertEquals("InstrumentType", trade.getCategory());
                break;
            }
        }
        assertTrue(foundEquityTrade, "Equity trade should be in the source records list");
    }

    @Test
    public void testFindMatchingRecords() {
        // Get source records and lookup services
        List<Trade> sourceRecords = dataServiceManager.requestData("sourceRecords");
        List<LookupService> lookupServices = dataServiceManager.requestData("lookupServices");

        // Test finding matching records
        List<Trade> matchingRecords = dataServiceManager.requestData("matchingRecords", sourceRecords, lookupServices);
        assertNotNull(matchingRecords);
        assertFalse(matchingRecords.isEmpty());

        // Verify that matching records contain expected trades
        boolean foundEquityTrade = false;
        for (Trade trade : matchingRecords) {
            if ("T001".equals(trade.getId()) && "Equity".equals(trade.getValue())) {
                foundEquityTrade = true;
                break;
            }
        }
        assertTrue(foundEquityTrade, "Equity trade should be in the matching records list");
    }

    @Test
    public void testFindNonMatchingRecords() {
        // Get source records and lookup services
        List<Trade> sourceRecords = dataServiceManager.requestData("sourceRecords");
        List<LookupService> lookupServices = dataServiceManager.requestData("lookupServices");

        // Test finding non-matching records
        List<Trade> nonMatchingRecords = dataServiceManager.requestData("nonMatchingRecords", sourceRecords, lookupServices);
        assertNotNull(nonMatchingRecords);

        // Verify that non-matching records contain expected trades
        boolean foundCommodityTrade = false;
        for (Trade trade : nonMatchingRecords) {
            if ("T007".equals(trade.getId()) && "Commodity".equals(trade.getValue())) {
                foundCommodityTrade = true;
                break;
            }
        }
        assertTrue(foundCommodityTrade, "Commodity trade should be in the non-matching records list");
    }

    @Test
    public void testUnsupportedDataType() {
        // Test requesting an unsupported data type
        Object result = dataServiceManager.requestData("unsupportedType");
        assertNull(result);
    }

    @Test
    public void testNonExistentDataSource() {
        // Test requesting data from a non-existent data source
        Object result = dataServiceManager.requestDataByName("NonExistentDataSource", "products");
        assertNull(result);
    }

    @Test
    public void testLoadDataSource() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Create a mock data source
        MockDataSource dataSource = new MockDataSource("TestDataSource", "testData");

        // Load the data source
        manager.loadDataSource(dataSource);

        // Verify the data source was loaded
        DataSource loadedDataSource = manager.getDataSourceByName("TestDataSource");
        assertNotNull(loadedDataSource);
        assertEquals("TestDataSource", loadedDataSource.getName());
        assertEquals("testData", loadedDataSource.getDataType());
    }

    @Test
    public void testLoadDataSourceWithNullDataSource() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Load a null data source
        manager.loadDataSource(null);

        // Verify no data source was loaded
        DataSource loadedDataSource = manager.getDataSourceByName("TestDataSource");
        assertNull(loadedDataSource);
    }

    @Test
    public void testLoadDataSourceWithNullName() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Create a mock data source with null name
        MockDataSource dataSource = new MockDataSource(null, "testData") {
            @Override
            public String getName() {
                return null;
            }
        };

        // Load the data source
        manager.loadDataSource(dataSource);

        // Verify no data source was loaded
        DataSource loadedDataSource = manager.getDataSourceByType("testData");
        assertNull(loadedDataSource);
    }

    @Test
    public void testLoadDataSourceWithEmptyName() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Create a mock data source with empty name
        MockDataSource dataSource = new MockDataSource("", "testData") {
            @Override
            public String getName() {
                return "";
            }
        };

        // Load the data source
        manager.loadDataSource(dataSource);

        // Verify no data source was loaded
        DataSource loadedDataSource = manager.getDataSourceByType("testData");
        assertNull(loadedDataSource);
    }

    @Test
    public void testLoadDataSourceWithNullDataType() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Create a mock data source with null data type
        MockDataSource dataSource = new MockDataSource("TestDataSource", null) {
            @Override
            public String getDataType() {
                return null;
            }
        };

        // Load the data source
        manager.loadDataSource(dataSource);

        // Verify no data source was loaded
        DataSource loadedDataSource = manager.getDataSourceByName("TestDataSource");
        assertNull(loadedDataSource);
    }

    @Test
    public void testLoadDataSourceWithEmptyDataType() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Create a mock data source with empty data type
        MockDataSource dataSource = new MockDataSource("TestDataSource", "") {
            @Override
            public String getDataType() {
                return "";
            }
        };

        // Load the data source
        manager.loadDataSource(dataSource);

        // Verify no data source was loaded
        DataSource loadedDataSource = manager.getDataSourceByName("TestDataSource");
        assertNull(loadedDataSource);
    }

    @Test
    public void testLoadDataSources() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Create mock data sources
        MockDataSource dataSource1 = new MockDataSource("TestDataSource1", "testData1");
        MockDataSource dataSource2 = new MockDataSource("TestDataSource2", "testData2");

        // Load the data sources
        manager.loadDataSources(dataSource1, dataSource2);

        // Verify the data sources were loaded
        DataSource loadedDataSource1 = manager.getDataSourceByName("TestDataSource1");
        assertNotNull(loadedDataSource1);
        assertEquals("TestDataSource1", loadedDataSource1.getName());
        assertEquals("testData1", loadedDataSource1.getDataType());

        DataSource loadedDataSource2 = manager.getDataSourceByName("TestDataSource2");
        assertNotNull(loadedDataSource2);
        assertEquals("TestDataSource2", loadedDataSource2.getName());
        assertEquals("testData2", loadedDataSource2.getDataType());
    }

    @Test
    public void testLoadDataSourcesWithNullDataSources() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Load null data sources
        manager.loadDataSources(null);

        // Verify no data sources were loaded
        DataSource loadedDataSource = manager.getDataSourceByName("TestDataSource");
        assertNull(loadedDataSource);
    }

    @Test
    public void testLoadDataSourcesWithNullDataSource() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Create a mock data source
        MockDataSource dataSource = new MockDataSource("TestDataSource", "testData");

        // Load the data sources with a null data source
        manager.loadDataSources(dataSource, null);

        // Verify only the non-null data source was loaded
        DataSource loadedDataSource = manager.getDataSourceByName("TestDataSource");
        assertNotNull(loadedDataSource);
        assertEquals("TestDataSource", loadedDataSource.getName());
        assertEquals("testData", loadedDataSource.getDataType());
    }

    @Test
    public void testGetDataSourceByName() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Create a mock data source
        MockDataSource dataSource = new MockDataSource("TestDataSource", "testData");

        // Load the data source
        manager.loadDataSource(dataSource);

        // Get the data source by name
        DataSource loadedDataSource = manager.getDataSourceByName("TestDataSource");
        assertNotNull(loadedDataSource);
        assertEquals("TestDataSource", loadedDataSource.getName());
        assertEquals("testData", loadedDataSource.getDataType());
    }

    @Test
    public void testGetDataSourceByNameWithNonExistentName() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Get a data source with a non-existent name
        DataSource loadedDataSource = manager.getDataSourceByName("NonExistentDataSource");
        assertNull(loadedDataSource);
    }

    @Test
    public void testGetDataSourceByType() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Create a mock data source
        MockDataSource dataSource = new MockDataSource("TestDataSource", "testData");

        // Load the data source
        manager.loadDataSource(dataSource);

        // Get the data source by type
        DataSource loadedDataSource = manager.getDataSourceByType("testData");
        assertNotNull(loadedDataSource);
        assertEquals("TestDataSource", loadedDataSource.getName());
        assertEquals("testData", loadedDataSource.getDataType());
    }

    @Test
    public void testGetDataSourceByTypeWithNonExistentType() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Get a data source with a non-existent type
        DataSource loadedDataSource = manager.getDataSourceByType("NonExistentDataType");
        assertNull(loadedDataSource);
    }

    @Test
    public void testIntegrationWithCustomDataSource() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Create a custom data source
        CustomDataSource dataSource = new CustomDataSource("CustomProductsSource", "customProducts");

        // Load the data source
        manager.loadDataSource(dataSource);

        // Get the data source by name
        DataSource loadedDataSource = manager.getDataSourceByName("CustomProductsSource");
        assertNotNull(loadedDataSource);
        assertEquals("CustomProductsSource", loadedDataSource.getName());
        assertEquals("customProducts", loadedDataSource.getDataType());

        // Verify we can get data from the custom data source
        List<Product> products = manager.requestData("customProducts");
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    public void testDynamicDataModification() {
        // Create a new DataServiceManager
        DataServiceManager manager = new DataServiceManager();

        // Create a custom data source
        CustomDataSource dataSource = new CustomDataSource("CustomTradesSource", "customTrades");

        // Load the data source
        manager.loadDataSource(dataSource);

        // Get the initial data
        List<Trade> initialTrades = manager.requestData("customTrades");
        assertNotNull(initialTrades);
        int initialSize = initialTrades.size();

        // Get the custom data source by name
        CustomDataSource customTradesSource = (CustomDataSource) manager.getDataSourceByName("CustomTradesSource");
        assertNotNull(customTradesSource);

        // Add a new trade
        List<Trade> updatedTrades = new ArrayList<>(initialTrades);
        updatedTrades.add(new Trade("CT004", "New Custom Value", "Custom Category"));
        customTradesSource.addData("customTrades", updatedTrades);

        // Get the updated data
        List<Trade> newTrades = manager.requestData("customTrades");
        assertNotNull(newTrades);
        assertEquals(initialSize + 1, newTrades.size());

        // Verify the new trade was added
        boolean foundNewTrade = false;
        for (Trade trade : newTrades) {
            if ("CT004".equals(trade.getId())) {
                foundNewTrade = true;
                assertEquals("New Custom Value", trade.getValue());
                assertEquals("Custom Category", trade.getCategory());
                break;
            }
        }
        assertTrue(foundNewTrade, "New trade should be in the trades list");
    }
}
