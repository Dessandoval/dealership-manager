package model;

import java.math.BigDecimal;

public class SalesContract extends Contract {

    private BigDecimal salesTaxAmount;
    private BigDecimal recordingFee;
    private boolean isFinanced;

    public SalesContract(BigDecimal salesTaxAmount, BigDecimal recordingFee, boolean isFinanced) {
        this.salesTaxAmount = salesTaxAmount;
        this.recordingFee = recordingFee;
        this.isFinanced = isFinanced;
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
        return isFinanced;
    }

    public void setFinanced(boolean financed) {
        isFinanced = financed;
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        return null;
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
