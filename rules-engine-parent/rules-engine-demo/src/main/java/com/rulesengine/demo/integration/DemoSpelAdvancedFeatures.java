package com.rulesengine.demo.integration;

import com.rulesengine.core.engine.Rule;
import com.rulesengine.core.engine.RuleResult;
import com.rulesengine.core.engine.ExpressionEvaluatorService;
import com.rulesengine.core.engine.RuleEngineService;
import com.rulesengine.core.engine.TemplateProcessorService;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rulesengine.demo.model.Customer;
import com.rulesengine.demo.model.Product;
import com.rulesengine.demo.model.Trade;
import com.rulesengine.demo.service.LookupService;
import com.rulesengine.demo.service.PricingServiceDemo;
import com.rulesengine.demo.service.DataServiceManager;

/**
 * This class demonstrates advanced features of SpEL for dynamic evaluation.
 * It focuses on collection manipulation, array operations, and complex expressions.
 * 
 * This is a test/demo class that uses test data to demonstrate the rules engine functionality.
 */
public class DemoSpelAdvancedFeatures {
    // Services
    private final ExpressionEvaluatorService evaluatorService;
    private final RuleEngineService ruleEngineService;
    private final TemplateProcessorService templateProcessorService;
    private final DataServiceManager dataServiceManager;

    /**
     * Constructor with dependency injection.
     * 
     * @param evaluatorService The expression evaluator service
     * @param ruleEngineService The rule engine service
     * @param templateProcessorService The template processor service
     */
    public DemoSpelAdvancedFeatures(
            ExpressionEvaluatorService evaluatorService,
            RuleEngineService ruleEngineService,
            TemplateProcessorService templateProcessorService) {
        this.evaluatorService = evaluatorService;
        this.ruleEngineService = ruleEngineService;
        this.templateProcessorService = templateProcessorService;

        // Initialize DataServiceManager with mock data
        this.dataServiceManager = new DataServiceManager();
        this.dataServiceManager.initializeWithMockData();
    }

    public static void main(String[] args) {
        // Create services
        ExpressionEvaluatorService evaluatorService = new ExpressionEvaluatorService();
        RuleEngineService ruleEngineService = new RuleEngineService(evaluatorService);
        TemplateProcessorService templateProcessorService = new TemplateProcessorService(evaluatorService);
        PricingServiceDemo pricingService = new PricingServiceDemo();

        // Create main class with injected services
        DemoSpelAdvancedFeatures demoSpelAdvancedFeatures = new DemoSpelAdvancedFeatures(
            evaluatorService,
            ruleEngineService,
            templateProcessorService
        );

        // Example 1: Collection and array operations
        demoSpelAdvancedFeatures.demonstrateCollectionOperations();

        // Example 2: Advanced rule engine with collection filtering
        demoSpelAdvancedFeatures.demonstrateAdvancedRuleEngine();

        // Example 3: Dynamic method resolution and execution
        demoSpelAdvancedFeatures.demonstrateDynamicMethodExecution(pricingService);

        // Example 4: Template expressions with placeholders
        demoSpelAdvancedFeatures.demonstrateTemplateExpressions();

        // Example 5: XML template expressions with placeholders
        demoSpelAdvancedFeatures.demonstrateXmlTemplateExpressions();

        // Example 6: JSON template expressions with placeholders
        demoSpelAdvancedFeatures.demonstrateJsonTemplateExpressions();

        // Example 7: Dynamic lookup service
        demoSpelAdvancedFeatures.demonstrateDynamicLookupService();
    }

    /**
     * Demonstrates collection operations using SpEL.
     */
    private void demonstrateCollectionOperations() {
        System.out.println("\n=== Financial Instrument Collection Operations ===");

        StandardEvaluationContext context = new StandardEvaluationContext();

        // Get products from data service
        List<Product> products = dataServiceManager.requestData("products");
        context.setVariable("products", products);

        // Collection selection - filter fixed income products
        com.rulesengine.core.engine.RuleResult result1 = evaluatorService.evaluateWithResult("#products.?[category == 'FixedIncome']", context, List.class);
        System.out.println("Rule result: " + (result1.isTriggered() ? "Triggered" : "Not triggered"));

        // Collection projection - get all product names
        com.rulesengine.core.engine.RuleResult result2 = evaluatorService.evaluateWithResult("#products.![name]", context, List.class);
        System.out.println("Rule result: " + (result2.isTriggered() ? "Triggered" : "Not triggered"));

        // Combining selection and projection - names of equity products
        com.rulesengine.core.engine.RuleResult result3 = evaluatorService.evaluateWithResult("#products.?[category == 'Equity'].![name]", context, List.class);
        System.out.println("Rule result: " + (result3.isTriggered() ? "Triggered" : "Not triggered"));

        // First and last elements
        context.setVariable("priceThreshold", 500.0);
        com.rulesengine.core.engine.RuleResult result4 = evaluatorService.evaluateWithResult("#products.^[price > #priceThreshold].name", context, String.class);
        System.out.println("First expensive product: " + result4.getMessage());

        com.rulesengine.core.engine.RuleResult result5 = evaluatorService.evaluateWithResult("#products.$[price < 200].name", context, String.class);
        System.out.println("Last cheap product: " + result5.getMessage());
    }

    /**
     * Demonstrates advanced rule engine with collection filtering.
     */
    private void demonstrateAdvancedRuleEngine() {
        System.out.println("\n=== Advanced Rule Engine with Collection Filtering ===");

        // Get data from service
        List<Product> inventory = dataServiceManager.requestData("inventory");
        Customer customer = dataServiceManager.requestData("customer");

        // Create context with variables
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("inventory", inventory);
        context.setVariable("customer", customer);

        // Create rules using DemoRuleConfiguration
        List<Rule> rules = new ArrayList<>();
        rules.add(com.rulesengine.demo.integration.DemoRuleConfiguration.createInvestmentRecommendationsRule());
        rules.add(com.rulesengine.demo.integration.DemoRuleConfiguration.createGoldTierInvestorOffersRule());
        rules.add(com.rulesengine.demo.integration.DemoRuleConfiguration.createLowCostInvestmentOptionsRule());

        // Evaluate rules
        ruleEngineService.evaluateRules(rules, context);
    }

    /**
     * Demonstrates dynamic method resolution and execution.
     */
    private void demonstrateDynamicMethodExecution(PricingServiceDemo pricingService) {
        System.out.println("\n=== Dynamic Method Resolution and Execution ===");

        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("pricingService", pricingService);
        context.setVariable("basePrice", 100.0);

        // Dynamic method call based on pricing strategy
        String[] pricingStrategies = {"Standard", "Premium", "Sale", "Clearance"};
        for (String strategy : pricingStrategies) {
            String methodName = "calculate" + strategy + "Price";
            String expression = "#pricingService." + methodName + "(#basePrice)";

            Double price = evaluatorService.evaluate(expression, context, Double.class);
            System.out.println(strategy + " price: $" + price);
        }
    }

    /**
     * Demonstrates template expressions with placeholders.
     */
    private void demonstrateTemplateExpressions() {
        System.out.println("\n=== Template Expressions with Placeholders ===");

        // Get customer from data service
        Customer customer = dataServiceManager.requestData("templateCustomer");

        // Create context with variables
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("customer", customer);

        // Process template with customer information
        String template = "Dear #{#customer.name},\n\n" +
                "Thank you for being a valued #{#customer.membershipLevel} member for #{#customer.age} years.\n" +
                "We have selected some investment opportunities in #{#customer.preferredCategories[0]} " +
                "and #{#customer.preferredCategories[1]} that might interest you.\n\n" +
                "Your current discount: #{#customer.membershipLevel == 'Gold' ? '20%' : '10%'}\n\n" +
                "Sincerely,\nInvestment Team";

        String result = templateProcessorService.processTemplate(template, context);
        System.out.println(result);
    }

    /**
     * Demonstrates XML template expressions with placeholders.
     */
    private void demonstrateXmlTemplateExpressions() {
        System.out.println("\n=== XML Template Expressions with Placeholders ===");

        // Get customer and products from data service
        Customer customer = dataServiceManager.requestData("templateCustomer");
        List<Product> products = dataServiceManager.requestData("products");

        // Create context with variables
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("customer", customer);
        context.setVariable("products", products);

        // Process XML template with customer and product information
        String xmlTemplate = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<investment-recommendations>\n" +
                "    <customer>\n" +
                "        <name>#{#customer.name}</name>\n" +
                "        <membership>#{#customer.membershipLevel}</membership>\n" +
                "        <age>#{#customer.age}</age>\n" +
                "    </customer>\n" +
                "    <preferred-categories>\n" +
                "        <category>#{#customer.preferredCategories[0]}</category>\n" +
                "        <category>#{#customer.preferredCategories[1]}</category>\n" +
                "    </preferred-categories>\n" +
                "    <recommended-products>\n" +
                "        <product>\n" +
                "            <name>#{#products[0].name}</name>\n" +
                "            <price>#{#products[0].price}</price>\n" +
                "            <category>#{#products[0].category}</category>\n" +
                "        </product>\n" +
                "        <product>\n" +
                "            <name>#{#products[1].name}</name>\n" +
                "            <price>#{#products[1].price}</price>\n" +
                "            <category>#{#products[1].category}</category>\n" +
                "        </product>\n" +
                "    </recommended-products>\n" +
                "</investment-recommendations>";

        String result = templateProcessorService.processXmlTemplate(xmlTemplate, context);
        System.out.println(result);
    }

    /**
     * Demonstrates JSON template expressions with placeholders.
     */
    private void demonstrateJsonTemplateExpressions() {
        System.out.println("\n=== JSON Template Expressions with Placeholders ===");

        // Get customer and products from data service
        Customer customer = dataServiceManager.requestData("templateCustomer");
        List<Product> products = dataServiceManager.requestData("products");

        // Create context with variables
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("customer", customer);
        context.setVariable("products", products);

        // Process JSON template with customer and product information
        String jsonTemplate = "{\n" +
                "  \"customer\": {\n" +
                "    \"name\": \"#{#customer.name}\",\n" +
                "    \"membership\": \"#{#customer.membershipLevel}\",\n" +
                "    \"age\": #{#customer.age},\n" +
                "    \"preferredCategories\": [\n" +
                "      \"#{#customer.preferredCategories[0]}\",\n" +
                "      \"#{#customer.preferredCategories[1]}\"\n" +
                "    ]\n" +
                "  },\n" +
                "  \"recommendedProducts\": [\n" +
                "    {\n" +
                "      \"name\": \"#{#products[0].name}\",\n" +
                "      \"price\": #{#products[0].price},\n" +
                "      \"category\": \"#{#products[0].category}\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"#{#products[1].name}\",\n" +
                "      \"price\": #{#products[1].price},\n" +
                "      \"category\": \"#{#products[1].category}\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"discountPercentage\": #{#customer.membershipLevel == 'Gold' ? 20 : 10}\n" +
                "}";

        String result = templateProcessorService.processJsonTemplate(jsonTemplate, context);
        System.out.println(result);
    }

    /**
     * Demonstrates dynamic lookup service.
     */
    private void demonstrateDynamicLookupService() {
        System.out.println("\n=== Dynamic Lookup Service ===");

        // Get lookup services from data service
        List<LookupService> lookupServices = dataServiceManager.requestData("lookupServices");

        // Create context with variables
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("lookupServices", lookupServices);

        // Dynamically find lookup service by name
        String lookupName = "InstrumentTypes";
        context.setVariable("lookupName", lookupName);

        RuleResult result = evaluatorService.evaluateWithResult(
            "#lookupServices.?[name == #lookupName][0]", context, LookupService.class);

        if (result.isTriggered()) {
            LookupService lookupService = evaluatorService.evaluate(
                "#lookupServices.?[name == #lookupName][0]", context, LookupService.class);

            if (lookupService != null) {
                System.out.println("Found lookup service: " + lookupService.getName());
                System.out.println("Values: " + lookupService.getLookupValues());

                // Test validation
                String testValue = "Equity";
                context.setVariable("testValue", testValue);

                Boolean isValid = evaluatorService.evaluate(
                    "#lookupServices.?[name == #lookupName][0].validate(#testValue)", 
                    context, Boolean.class);

                System.out.println("Is '" + testValue + "' valid? " + isValid);
            }
        } else {
            System.out.println("Lookup service not found: " + lookupName);
        }
    }
}
