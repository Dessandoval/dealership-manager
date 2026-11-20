package model;

import java.math.BigDecimal;

public class SalesContract extends Contract {

    private BigDecimal salesTaxAmount;
    private BigDecimal recordingFee;
    private boolean financed;

    public SalesContract(BigDecimal salesTaxAmount, BigDecimal recordingFee, boolean financed) {
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

        if (getVehicleSold().getPrice().compareTo(new BigDecimal("10000")) >= 0) {
            return LoanCalculator.calculateMonthlyPayment(getTotalPrice(),new BigDecimal("4.25"), 48);
        }
        return LoanCalculator.calculateMonthlyPayment(getTotalPrice(),new BigDecimal("5.25"), 24);
    }

    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal price = getVehicleSold().getPrice();
        BigDecimal salesTax = price.multiply(new BigDecimal("0.05"));
        BigDecimal recordingFee = new BigDecimal("100.00");
        BigDecimal processingFee = getProcessingFee();

        return price.add(salesTax).add(recordingFee).add(processingFee);
    }

    public BigDecimal getProcessingFee() {
        if (getVehicleSold().getPrice().compareTo(new BigDecimal("10000")) < 0) {
            return new BigDecimal("295.00");
        }
        return new BigDecimal("495.00");
    }
}
