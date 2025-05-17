package service;

/**
 * Interface for data lookup services.
 * This interface defines the methods that a data lookup service must implement.
 */
public interface IDataLookup {
    String getName();
    boolean validate(Object value);
    Object enrich(Object value);
    Object transform(Object value);
}