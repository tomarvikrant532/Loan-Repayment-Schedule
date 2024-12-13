package com.LoanRepayment.LoanRepayment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class RepaymentSchedule {
    private List<RepaymentScheduleEntry> schedule;
    public RepaymentSchedule(List<RepaymentScheduleEntry> schedule) {
        this.schedule = schedule;
    }
}
