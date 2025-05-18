package com.rulesengine.core.service;

/**
 * Interface for enrichment services.
 */
public interface Enricher extends NamedService {
    Object enrich(Object value);
}