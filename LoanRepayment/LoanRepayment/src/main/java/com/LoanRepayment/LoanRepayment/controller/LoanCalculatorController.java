package com.LoanRepayment.LoanRepayment.controller;

import com.LoanRepayment.LoanRepayment.model.LoanRequest;
import com.LoanRepayment.LoanRepayment.model.RepaymentScheduleEntry;
import com.LoanRepayment.LoanRepayment.service.LoanCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loan")
public class LoanCalculatorController {

    @Autowired
    private LoanCalculatorService loanCalculatorService;

    @PostMapping("/calculate-schedule")
    public ResponseEntity<List<RepaymentScheduleEntry>> calculateSchedule( @RequestBody LoanRequest request) {
        List<RepaymentScheduleEntry> schedule = loanCalculatorService.generateSchedule(request);
        return ResponseEntity.ok(schedule);
    }
}

