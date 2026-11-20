package com.pluralsight.dealershipmanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalesContract extends Contract {

    private static final BigDecimal PRICE_THRESHOLD = new BigDecimal("10000");
    private BigDecimal salesTaxAmount;
    private BigDecimal recordingFee;
    private boolean financed;

    public SalesContract(LocalDate contractDate, String customerName, String customerEmail, Vehicle vehicleSold, BigDecimal salesTaxAmount, BigDecimal recordingFee, boolean financed) {
        super(contractDate, customerName, customerEmail, vehicleSold);
        this.salesTaxAmount = salesTaxAmount;
        this.recordingFee = recordingFee;
        this.financed = financed;
    }

    public BigDecimal getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public void setSalesTaxAmount(BigDecimal salesTaxAmount) {
        this.salesTaxAmount = salesTaxAmount;
    }

    public BigDecimal getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(BigDecimal recordingFee) {
        this.recordingFee = recordingFee;
    }

    public boolean isFinanced() {
        return financed;
    }

    public void setFinanced(boolean financed) {
        this.financed = financed;
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        if (!financed) {
            return BigDecimal.ZERO;
        }

        if (getVehicleSold().getPrice().compareTo(PRICE_THRESHOLD) >= 0) {
            return LoanCalculator.calculateMonthlyPayment(getTotalPrice(),new BigDecimal(".0425"), 48);
        }else {
            return LoanCalculator.calculateMonthlyPayment(getTotalPrice(),new BigDecimal(".0525"), 24);
        }
    }

    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal price = getVehicleSold().getPrice();
        BigDecimal salesTax = price.multiply(new BigDecimal(".05"));
        BigDecimal recordingFee = new BigDecimal("100.00");
        BigDecimal processingFee = getProcessingFee();

        return price.add(salesTax).add(recordingFee).add(processingFee);
    }

    public BigDecimal getProcessingFee() {
        if (getVehicleSold().getPrice().compareTo(PRICE_THRESHOLD) < 0) {
            return new BigDecimal("295.00");
        }else {
            return new BigDecimal("495.00");
        }
    }
}
