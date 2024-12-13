package com.LoanRepayment.LoanRepayment.model;

import lombok.Builder;
import lombok.Data;

public class RepaymentScheduleEntry {
    private int sno;
    private String date;
    private String day;
    private double outstandingStart;
    private double dueInterest;
    private double emi;
    private double outstandingEnd;

    public RepaymentScheduleEntry()
    {

    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    // Getter and Setter for date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getter and Setter for day
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    // Getter and Setter for outstandingStart
    public double getOutstandingStart() {
        return outstandingStart;
    }

    public void setOutstandingStart(double outstandingStart) {
        this.outstandingStart = outstandingStart;
    }

    // Getter and Setter for dueInterest
    public double getDueInterest() {
        return dueInterest;
    }

    public void setDueInterest(double dueInterest) {
        this.dueInterest = dueInterest;
    }

    // Getter and Setter for emi
    public double getEmi() {
        return emi;
    }

    public void setEmi(double emi) {
        this.emi = emi;
    }

    // Getter and Setter for outstandingEnd
    public double getOutstandingEnd() {
        return outstandingEnd;
    }

    public void setOutstandingEnd(double outstandingEnd) {
        this.outstandingEnd = outstandingEnd;
    }
}
