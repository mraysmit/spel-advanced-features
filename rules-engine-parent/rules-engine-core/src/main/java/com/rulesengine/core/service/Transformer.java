package com.rulesengine.core.service;

/**
 * Interface for transformation services.
 */
public interface Transformer extends NamedService {
    Object transform(Object value);
}