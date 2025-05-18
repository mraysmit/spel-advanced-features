package com.rulesengine.core.service;

/**
 * Service for enrichment operations.
 */
public class EnrichmentService {
    private final LookupServiceRegistry registry;
    
    public EnrichmentService(LookupServiceRegistry registry) {
        this.registry = registry;
    }
    
    public Object enrich(String enricherName, Object value) {
        Enricher enricher = registry.getService(enricherName, Enricher.class);
        return enricher != null ? enricher.enrich(value) : value;
    }
}