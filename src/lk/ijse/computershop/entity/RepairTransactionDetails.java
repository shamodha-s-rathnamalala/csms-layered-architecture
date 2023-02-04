package lk.ijse.computershop.entity;

public class RepairTransactionDetails implements SuperEntity{
    private String transactionId;
    private String repairId;
    private double repairPrice;

    public RepairTransactionDetails() {
    }

    public RepairTransactionDetails(String transactionId, String repairId, double repairPrice) {
        this.transactionId = transactionId;
        this.repairId = repairId;
        this.repairPrice = repairPrice;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public double getRepairPrice() {
        return repairPrice;
    }

    public void setRepairPrice(double repairPrice) {
        this.repairPrice = repairPrice;
    }
}
