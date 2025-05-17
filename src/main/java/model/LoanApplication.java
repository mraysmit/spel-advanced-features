package model;

import java.util.UUID;

/**
 * Represents a loan application with applicant information and loan details.
 */
public class LoanApplication {
    private final UUID id;
    private String applicantName;
    private double loanAmount;
    private int creditScore;
    private double debtToIncomeRatio;
    private int yearsEmployed;
    private double annualIncome;

    /**
     * Constructor with all fields.
     * 
     * @param applicantName The name of the loan applicant
     * @param loanAmount The requested loan amount
     * @param creditScore The applicant's credit score
     * @param debtToIncomeRatio The applicant's debt-to-income ratio
     * @param yearsEmployed The number of years the applicant has been employed
     * @param annualIncome The applicant's annual income
     */
    public LoanApplication(String applicantName, double loanAmount, int creditScore, 
                          double debtToIncomeRatio, int yearsEmployed, double annualIncome) {
        this.id = UUID.randomUUID();
        this.applicantName = applicantName;
        this.loanAmount = loanAmount;
        this.creditScore = creditScore;
        this.debtToIncomeRatio = debtToIncomeRatio;
        this.yearsEmployed = yearsEmployed;
        this.annualIncome = annualIncome;
    }

    public UUID getId() {
        return id;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public double getDebtToIncomeRatio() {
        return debtToIncomeRatio;
    }

    public int getYearsEmployed() {
        return yearsEmployed;
    }

    public double getAnnualIncome() {
        return annualIncome;
    }

    /**
     * Determines if the loan application should be approved based on credit score and debt-to-income ratio.
     * 
     * @return true if the application should be approved, false otherwise
     */
    public boolean shouldApprove() {
        return creditScore >= 700 && debtToIncomeRatio <= 0.36;
    }

    /**
     * Determines if the loan application should be referred for manual review.
     * 
     * @return true if the application should be referred, false otherwise
     */
    public boolean shouldRefer() {
        return creditScore >= 650 && creditScore < 700 && debtToIncomeRatio <= 0.40;
    }
}