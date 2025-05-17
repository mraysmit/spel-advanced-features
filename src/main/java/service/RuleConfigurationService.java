package service;

import engine.Rule;
import engine.RuleGroup;
import engine.RulesEngineConfiguration;
import integration.DemoRuleConfiguration;

/**
 * Service for managing rule configurations.
 * This class hosts the main repository of Rules for the project and provides methods
 * for registering and retrieving rules.
 */
public class RuleConfigurationService {
    private final RulesEngineConfiguration configuration;

    /**
     * Create a new RuleConfigurationService with the given configuration.
     *
     * @param configuration The configuration to use
     */
    public RuleConfigurationService(RulesEngineConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Get the configuration used by this service.
     *
     * @return The configuration
     */
    public RulesEngineConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Register all predefined rules.
     * This method registers all the rules defined in DemoRuleConfiguration.
     */
    public void registerAllRules() {
        registerLoanApprovalRules();
        registerDiscountRules();
        registerCombinedRulesDemoRules();
        registerRuleGroupDemoRules();
    }

    /**
     * Register loan approval rules.
     */
    public void registerLoanApprovalRules() {
        DemoRuleConfiguration.registerLoanApprovalRules(configuration);
    }

    /**
     * Register discount rules.
     */
    public void registerDiscountRules() {
        DemoRuleConfiguration.registerDiscountRules(configuration);
    }

    /**
     * Register combined rules demonstration rules.
     */
    public void registerCombinedRulesDemoRules() {
        DemoRuleConfiguration.registerCombinedRulesDemoRules(configuration);
    }

    /**
     * Register rule group demonstration rules.
     */
    public void registerRuleGroupDemoRules() {
        DemoRuleConfiguration.registerRuleGroupDemoRules(configuration);
    }

    /**
     * Get a rule by its ID.
     *
     * @param id The ID of the rule
     * @return The rule with the specified ID, or null if not found
     */
    public Rule getRuleById(String id) {
        return configuration.getRuleById(id);
    }

    /**
     * Get a rule group by its ID.
     *
     * @param id The ID of the rule group
     * @return The rule group with the specified ID, or null if not found
     */
    public RuleGroup getRuleGroupById(String id) {
        return configuration.getRuleGroupById(id);
    }

    /**
     * Create a new rule with the given parameters.
     *
     * @param id The ID of the rule
     * @param category The category of the rule
     * @param name The name of the rule
     * @param condition The condition of the rule
     * @param message The message of the rule
     * @param description The description of the rule
     * @param priority The priority of the rule
     * @return The created rule
     */
    public Rule createRule(String id, String category, String name, String condition, 
                          String message, String description, int priority) {
        return configuration.rule(id)
            .withCategory(category)
            .withName(name)
            .withCondition(condition)
            .withMessage(message)
            .withDescription(description)
            .withPriority(priority)
            .build();
    }
}