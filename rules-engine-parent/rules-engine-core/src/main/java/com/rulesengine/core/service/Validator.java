package com.rulesengine.core.service;

/**
 * Interface for validation services.
 */
public interface Validator extends NamedService {
    boolean validate(Object value);
}