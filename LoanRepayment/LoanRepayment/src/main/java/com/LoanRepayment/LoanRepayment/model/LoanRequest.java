package com.LoanRepayment.LoanRepayment.model;

import com.LoanRepayment.LoanRepayment.enums.RepaymentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanRequest {
    @NotNull
    @Min(1)
    @JsonProperty("loanAmount")
    private Double loanAmount;

    @NotNull
    @Min(1)
    @JsonProperty("tenureMonths")
    private Integer tenureMonths;

    @NotNull
    @Min(0)
    @JsonProperty("interestRate")
    private Double interestRate;

    @NotNull
    @JsonProperty("repaymentType")
    private RepaymentType repaymentType;

    public Double getLoanAmount() {
        return loanAmount;
    }

    public Integer getTenureMonths() {
        return tenureMonths;
    }
    public Double getInterestRate() {
        return interestRate;
    }

    public RepaymentType getRepaymentType() {
        return repaymentType;
    }
}
