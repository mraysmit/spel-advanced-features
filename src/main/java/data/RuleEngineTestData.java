package data;

import model.LoanApplication;
import model.OrderInfo;
import model.InvestmentScenario;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for providing test data for business rules.
 * This class centralizes test data creation and management, separating it from rule processing.
 */
public class RuleEngineTestData {

    /**
     * Get loan application test data.
     * 
     * @return List of loan application data
     */
    public static List<LoanApplication> getLoanApplications() {
        // Loan application 1: Should be approved (good credit, low debt-to-income ratio)
        LoanApplication application1 = new LoanApplication(
            "John Smith",
            250000.0,
            720,
            0.32,
            5,
            85000.0
        );

        // Loan application 2: Should be referred (moderate credit, acceptable debt-to-income ratio)
        LoanApplication application2 = new LoanApplication(
            "Jane Doe",
            150000.0,
            680,
            0.35,
            3,
            95000.0
        );

        // Loan application 3: Should be rejected (debt-to-income ratio too high)
        LoanApplication application3 = new LoanApplication(
            "Bob Johnson",
            50000.0,
            720,
            0.45,
            4,
            75000.0
        );

        return Arrays.asList(application1, application2, application3);
    }

    /**
     * Get order test data.
     * 
     * @return List of order data
     */
    public static List<OrderInfo> getOrders() {
        // Order 1: Large order (should get 15% discount)
        OrderInfo order1 = new OrderInfo(
            "Acme Corp",
            1500.0,
            3
        );

        // Order 2: Loyal customer (should get 10% discount)
        OrderInfo order2 = new OrderInfo(
            "XYZ Industries",
            750.0,
            7
        );

        // Order 3: New customer (should get 5% discount)
        OrderInfo order3 = new OrderInfo(
            "New Customer LLC",
            500.0,
            0
        );

        // Order 4: Standard order (no discount)
        OrderInfo order4 = new OrderInfo(
            "Regular Customer Inc",
            300.0,
            2
        );

        return Arrays.asList(order1, order2, order3, order4);
    }

    /**
     * Get rule group test data.
     * 
     * @return List of rule group test scenarios
     */
    public static List<InvestmentScenario> getRuleGroupTestScenarios() {
        // Scenario 1: High-value retirement investment
        InvestmentScenario scenario1 = new InvestmentScenario(
            "High-value retirement investment",
            150000.0,
            "retirement",
            3,
            0.1,
            true
        );

        // Scenario 2: High-risk client with volatile market
        InvestmentScenario scenario2 = new InvestmentScenario(
            "High-risk client with volatile market",
            75000.0,
            "standard",
            8,
            0.25,
            true
        );

        // Scenario 3: KYC verification required
        InvestmentScenario scenario3 = new InvestmentScenario(
            "KYC verification required",
            50000.0,
            "standard",
            5,
            0.15,
            false
        );

        return Arrays.asList(scenario1, scenario2, scenario3);
    }

    /**
     * Get combined rules test data.
     * 
     * @return List of combined rules test scenarios
     */
    public static List<InvestmentScenario> getCombinedRulesTestScenarios() {
        // Scenario 1: High-value investment with high risk
        InvestmentScenario scenario1 = new InvestmentScenario(
            "High-value investment with high risk",
            200000.0,
            8,
            45,
            10
        );

        // Scenario 2: Low-value investment with low risk
        InvestmentScenario scenario2 = new InvestmentScenario(
            "Low-value investment with low risk",
            50000.0,
            3,
            35,
            5
        );

        // Scenario 3: Senior investor with moderate risk
        InvestmentScenario scenario3 = new InvestmentScenario(
            "Senior investor with moderate risk",
            100000.0,
            5,
            68,
            20
        );

        return Arrays.asList(scenario1, scenario2, scenario3);
    }
}
