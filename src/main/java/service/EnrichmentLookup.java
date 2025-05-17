package service;

import java.util.Map;

public class EnrichmentLookup implements IDataLookup {
    private String name;
    private Map<Object, Object> enrichmentData;

    public EnrichmentLookup(String name, Map<Object, Object> enrichmentData) {
        this.name = name;
        this.enrichmentData = enrichmentData;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean validate(Object value) {
        return true; // Always valid
    }

    @Override
    public Object enrich(Object value) {
        return enrichmentData.getOrDefault(value, value);
    }

    @Override
    public Object transform(Object value) {
        return value; // No transformation by default
    }
}