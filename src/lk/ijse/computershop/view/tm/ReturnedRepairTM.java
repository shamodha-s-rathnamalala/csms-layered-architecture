package lk.ijse.computershop.view.tm;

import java.time.LocalDate;

public class ReturnedRepairTM {
    private String repairId;
    private String transactionId;
    private String description;
    private String receiveCustomerId;
    private LocalDate receiveDate;
    private String returnedCustomerId;
    private LocalDate trueReturnDate;
    private double price;

    public ReturnedRepairTM() {
    }

    public ReturnedRepairTM(String repairId, String transactionId, String description, String receiveCustomerId, LocalDate receiveDate, String returnedCustomerId, LocalDate trueReturnDate, double price) {
        this.repairId = repairId;
        this.transactionId = transactionId;
        this.description = description;
        this.receiveCustomerId = receiveCustomerId;
        this.receiveDate = receiveDate;
        this.returnedCustomerId = returnedCustomerId;
        this.trueReturnDate = trueReturnDate;
        this.price = price;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiveCustomerId() {
        return receiveCustomerId;
    }

    public void setReceiveCustomerId(String receiveCustomerId) {
        this.receiveCustomerId = receiveCustomerId;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReturnedCustomerId() {
        return returnedCustomerId;
    }

    public void setReturnedCustomerId(String returnedCustomerId) {
        this.returnedCustomerId = returnedCustomerId;
    }

    public LocalDate getTrueReturnDate() {
        return trueReturnDate;
    }

    public void setTrueReturnDate(LocalDate trueReturnDate) {
        this.trueReturnDate = trueReturnDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ReturnRepairTm{" +
                "repairId='" + repairId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", description='" + description + '\'' +
                ", receiveCustomerId='" + receiveCustomerId + '\'' +
                ", receiveDate=" + receiveDate +
                ", returnedCustomerId='" + returnedCustomerId + '\'' +
                ", trueReturnDate=" + trueReturnDate +
                ", price=" + price +
                '}';
    }
}
