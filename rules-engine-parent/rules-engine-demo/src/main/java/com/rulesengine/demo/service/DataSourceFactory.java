package com.rulesengine.demo.service;

/**
 * Interface for data source factory.
 */
public interface DataSourceFactory {
    /**
     * Create a data source with the specified name and data type.
     * 
     * @param name The name of the data source
     * @param dataType The type of data the source will provide
     * @return A new data source
     */
    DataSource createDataSource(String name, String dataType);
}