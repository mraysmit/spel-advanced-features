package service;

import java.util.function.Predicate;

public class ValidationLookup implements IDataLookup {
    private String name;
    private Predicate<Object> validationRule;

    public ValidationLookup(String name, Predicate<Object> validationRule) {
        this.name = name;
        this.validationRule = validationRule;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean validate(Object value) {
        return validationRule.test(value);
    }

    @Override
    public Object enrich(Object value) {
        return value; // No enrichment by default
    }

    @Override
    public Object transform(Object value) {
        return value; // No transformation by default
    }
}