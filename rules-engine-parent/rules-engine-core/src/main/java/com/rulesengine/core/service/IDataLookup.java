package com.rulesengine.core.service;

/**
 * Legacy interface that combines all capabilities.
 * Kept for backward compatibility.
 */
public interface IDataLookup extends Validator, Enricher, Transformer {
    // No additional methods needed as it inherits all from parent interfaces
}