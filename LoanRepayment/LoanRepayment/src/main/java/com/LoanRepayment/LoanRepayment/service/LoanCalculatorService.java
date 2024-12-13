package com.LoanRepayment.LoanRepayment.service;

import com.LoanRepayment.LoanRepayment.enums.RepaymentType;
import com.LoanRepayment.LoanRepayment.model.LoanRequest;
import com.LoanRepayment.LoanRepayment.model.RepaymentScheduleEntry;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class LoanCalculatorService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public List<RepaymentScheduleEntry> generateSchedule(LoanRequest request) {
        validateLoanRequest(request);
        double emi = calculateEMI(request);
        List<RepaymentScheduleEntry> schedule = new ArrayList<>();
        LocalDate disbursementDate = LocalDate.now();
        LocalDate firstDueDate = calculateFirstDueDate(disbursementDate, request.getRepaymentType());

        double outstanding = request.getLoanAmount();
        int totalPeriods = calculateTotalPeriods(request);
        double periodicInterestRate = calculatePeriodicInterestRate(request);

        LocalDate currentDate = firstDueDate;

        for (int i = 0; i < totalPeriods; i++) {
            int daysBetween;
            if (i == 0) {
                daysBetween = (int) DAYS.between(disbursementDate, currentDate);
            } else {
                LocalDate previousDate = LocalDate.parse(schedule.get(i - 1).getDate(), DATE_FORMATTER);
                daysBetween = (int) DAYS.between(previousDate, currentDate);
            }

            double interest = calculateInterestForPeriod(outstanding, periodicInterestRate, daysBetween);
            double principal = emi - interest;

            if (i == totalPeriods - 1) {
                principal = outstanding;
                emi = principal + interest;
            }

            RepaymentScheduleEntry entry = new RepaymentScheduleEntry();
            entry.setSno(i + 1);
            entry.setDate(currentDate.format(DATE_FORMATTER));
            entry.setDay(currentDate.getDayOfWeek().toString());
            entry.setOutstandingStart(roundToTwoDecimals(outstanding));
            entry.setDueInterest(roundToTwoDecimals(interest));
            entry.setEmi(roundToTwoDecimals(emi));
            entry.setOutstandingEnd(roundToTwoDecimals(outstanding - principal));
            schedule.add(entry);

            outstanding -= principal;
            currentDate = updateDate(currentDate, request.getRepaymentType());
        }

        return schedule;
    }

    private void validateLoanRequest(LoanRequest request) {
        if (request.getLoanAmount() <= 0) {
            throw new IllegalArgumentException("Loan amount must be greater than 0.");
        }
        if (request.getTenureMonths() <= 0) {
            throw new IllegalArgumentException("Tenure months must be greater than 0.");
        }
        if (request.getInterestRate() < 0) {
            throw new IllegalArgumentException("Interest rate must be greater than or equal to 0.");
        }
        if (!isValidRepaymentType(request.getRepaymentType())) {
            throw new IllegalArgumentException("Repayment type must be either 'WEEKLY' or 'MONTHLY'.");
        }
    }
    private boolean isValidRepaymentType(RepaymentType repaymentType) {
        return repaymentType == RepaymentType.WEEKLY || repaymentType == RepaymentType.MONTHLY;
    }



    private LocalDate calculateFirstDueDate(LocalDate disbursementDate, RepaymentType repaymentType) {
        if (repaymentType == RepaymentType.WEEKLY) {
            return disbursementDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        } else {
            return disbursementDate.plusMonths(1).withDayOfMonth(5);
        }
    }

    private double calculateInterestForPeriod(double outstanding, double periodicInterestRate, int daysBetween) {
        double dailyRate = periodicInterestRate / 30.0; // Assuming 30 days per month
        return outstanding * dailyRate * daysBetween;
    }

    private double calculatePeriodicInterestRate(LoanRequest request) {
        double annualRate = request.getInterestRate() / 100;
        return request.getRepaymentType() == RepaymentType.WEEKLY ?
                annualRate / 52 :
                annualRate / 12;
    }

    private int calculateTotalPeriods(LoanRequest request) {
        return request.getRepaymentType() == RepaymentType.WEEKLY ?
                (int) (request.getTenureMonths() * 52.0 / 12.0) :
                request.getTenureMonths();
    }

    private LocalDate updateDate(LocalDate currentDate, RepaymentType repaymentType) {
        return repaymentType == RepaymentType.WEEKLY ?
                currentDate.plusWeeks(1) :
                currentDate.plusMonths(1).withDayOfMonth(5);
    }

    private double calculateEMI(LoanRequest request) {
        double principal = request.getLoanAmount();
        double periodicInterestRate = calculatePeriodicInterestRate(request);
        int totalPeriods = calculateTotalPeriods(request);

        double numerator = principal * periodicInterestRate * Math.pow(1 + periodicInterestRate, totalPeriods);
        double denominator = Math.pow(1 + periodicInterestRate, totalPeriods) - 1;

        return roundToTwoDecimals(numerator / denominator);
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}


