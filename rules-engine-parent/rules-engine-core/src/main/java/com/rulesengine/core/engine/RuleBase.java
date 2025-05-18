package com.rulesengine.core.engine;

import java.util.Set;

public interface RuleBase {
    String getId();
    Set<Category> getCategories();
    boolean hasCategory(Category category);
    String getName();
    String getDescription();
    int getPriority();
    // Other common methods
}