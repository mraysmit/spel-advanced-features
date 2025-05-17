package integration;

import engine.*;
import service.RuleConfigurationService;

import java.util.HashMap;
import java.util.Map;

/**
 * This class demonstrates the SpEL Rules Engine functionality,
 * particularly focusing on rule groups.
 */
public class DemoSpelRulesEngine {

    /**
     * Demonstrates rule groups functionality.
     * This method is called by the SpelRuleGroupsTest.
     */
    public static void demoRuleGroupsDemo() {
        System.out.println("\n=== Demonstrating Rule Groups ===");

        // Create configuration and rules engine
        RulesEngineConfiguration config = new RulesEngineConfiguration();
        RulesEngine engine = new RulesEngine(config);

        // Create rule configuration service
        RuleConfigurationService ruleConfigService = new RuleConfigurationService(config);

        // Register rule group demo rules
        ruleConfigService.registerRuleGroupDemoRules();

        // Create test scenarios for rule groups
        Map<String, Map<String, Object>> scenarios = createTestScenarios();

        // Execute each scenario
        for (Map.Entry<String, Map<String, Object>> scenario : scenarios.entrySet()) {
            String scenarioName = scenario.getKey();
            Map<String, Object> facts = scenario.getValue();

            System.out.println("\nProcessing scenario for: " + scenarioName);

            // Execute rules for each category
            executeRulesForCategory(engine, "investment-rules", facts);
            executeRulesForCategory(engine, "risk-assessment", facts);
            executeRulesForCategory(engine, "compliance", facts);
        }
    }

    /**
     * Execute rules for a specific category and print the results.
     */
    private static void executeRulesForCategory(RulesEngine engine, String category, Map<String, Object> facts) {
        RuleResult result = engine.executeRulesForCategory(category, facts);
        if (result.isTriggered()) {
            System.out.println("Category: " + category + ", rule triggered: " + result.getRuleName());
            System.out.println("Result: " + result.getMessage());
        }
    }

    /**
     * Create test scenarios for rule groups.
     */
    private static Map<String, Map<String, Object>> createTestScenarios() {
        Map<String, Map<String, Object>> scenarios = new HashMap<>();

        // Scenario 1: High-value retirement investment
        Map<String, Object> scenario1 = new HashMap<>();
        scenario1.put("investmentAmount", 150000);
        scenario1.put("accountType", "retirement");
        scenario1.put("clientRiskScore", 5);
        scenario1.put("marketVolatility", 0.15);
        scenario1.put("kycVerified", true);
        scenarios.put("High-value retirement investment", scenario1);

        // Scenario 2: High-risk client with volatile market
        Map<String, Object> scenario2 = new HashMap<>();
        scenario2.put("investmentAmount", 75000);
        scenario2.put("accountType", "standard");
        scenario2.put("clientRiskScore", 8);
        scenario2.put("marketVolatility", 0.25);
        scenario2.put("kycVerified", true);
        scenarios.put("High-risk client with volatile market", scenario2);

        // Scenario 3: KYC verification required
        Map<String, Object> scenario3 = new HashMap<>();
        scenario3.put("investmentAmount", 50000);
        scenario3.put("accountType", "standard");
        scenario3.put("clientRiskScore", 4);
        scenario3.put("marketVolatility", 0.1);
        scenario3.put("kycVerified", false);
        scenarios.put("KYC verification required", scenario3);

        return scenarios;
    }
}
