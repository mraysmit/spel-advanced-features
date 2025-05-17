package model;

import java.util.UUID;

/**
 * Represents an investment scenario with various investment parameters.
 */
public class InvestmentScenario {
    private final UUID id;
    private String scenarioName;
    private double investmentAmount;
    private String accountType;
    private int clientRiskScore;
    private double marketVolatility;
    private boolean kycVerified;
    private Integer clientAge;
    private Integer investmentExperience;

    /**
     * Constructor for rule group test scenarios.
     * 
     * @param scenarioName The name of the scenario
     * @param investmentAmount The amount to invest
     * @param accountType The type of account (retirement, standard, etc.)
     * @param clientRiskScore The client's risk score (1-10)
     * @param marketVolatility The market volatility (0.0-1.0)
     * @param kycVerified Whether the client's KYC (Know Your Customer) verification is complete
     */
    public InvestmentScenario(String scenarioName, double investmentAmount, String accountType,
                             int clientRiskScore, double marketVolatility, boolean kycVerified) {
        this.id = UUID.randomUUID();
        this.scenarioName = scenarioName;
        this.investmentAmount = investmentAmount;
        this.accountType = accountType;
        this.clientRiskScore = clientRiskScore;
        this.marketVolatility = marketVolatility;
        this.kycVerified = kycVerified;
        this.clientAge = null;
        this.investmentExperience = null;
    }

    /**
     * Constructor for combined rules test scenarios.
     * 
     * @param scenarioName The name of the scenario
     * @param investmentAmount The amount to invest
     * @param clientRiskScore The client's risk score (1-10)
     * @param clientAge The client's age
     * @param investmentExperience The client's years of investment experience
     */
    public InvestmentScenario(String scenarioName, double investmentAmount, int clientRiskScore,
                             int clientAge, int investmentExperience) {
        this.id = UUID.randomUUID();
        this.scenarioName = scenarioName;
        this.investmentAmount = investmentAmount;
        this.accountType = null;
        this.clientRiskScore = clientRiskScore;
        this.marketVolatility = 0.0;
        this.kycVerified = true;
        this.clientAge = clientAge;
        this.investmentExperience = investmentExperience;
    }

    public UUID getId() {
        return id;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public double getInvestmentAmount() {
        return investmentAmount;
    }

    public String getAccountType() {
        return accountType;
    }

    public int getClientRiskScore() {
        return clientRiskScore;
    }

    public double getMarketVolatility() {
        return marketVolatility;
    }

    public boolean isKycVerified() {
        return kycVerified;
    }

    public Integer getClientAge() {
        return clientAge;
    }

    public Integer getInvestmentExperience() {
        return investmentExperience;
    }

    /**
     * Determines if this is a high-value investment.
     * 
     * @return true if the investment amount is >= 100000, false otherwise
     */
    public boolean isHighValueInvestment() {
        return investmentAmount >= 100000;
    }

    /**
     * Determines if this is a high-risk investment.
     * 
     * @return true if the client risk score is >= 7 or market volatility is >= 0.2, false otherwise
     */
    public boolean isHighRiskInvestment() {
        return clientRiskScore >= 7 || marketVolatility >= 0.2;
    }

    /**
     * Determines if this is a retirement account.
     * 
     * @return true if the account type is "retirement", false otherwise
     */
    public boolean isRetirementAccount() {
        return "retirement".equalsIgnoreCase(accountType);
    }

    /**
     * Determines if the client is a senior investor.
     * 
     * @return true if the client age is >= 65, false otherwise or if age is not available
     */
    public boolean isSeniorInvestor() {
        return clientAge != null && clientAge >= 65;
    }

    /**
     * Determines if the client is an experienced investor.
     * 
     * @return true if the client has >= 10 years of investment experience, false otherwise or if experience is not available
     */
    public boolean isExperiencedInvestor() {
        return investmentExperience != null && investmentExperience >= 10;
    }
}