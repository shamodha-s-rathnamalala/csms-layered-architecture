package lk.ijse.computershop.view.tm;

import java.time.LocalDate;

public class RepairTM {
    private String repairId;
    private LocalDate receiveDate;
    private LocalDate returnDate;
    private String status;
    private String description;
    private String customerId;

    public RepairTM() {
    }

    public RepairTM(String repairId, LocalDate receiveDate, LocalDate returnDate, String status, String description, String customerId) {
        this.repairId = repairId;
        this.receiveDate = receiveDate;
        this.returnDate = returnDate;
        this.status = status;
        this.description = description;
        this.customerId = customerId;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "RepairTm{" +
                "repairId='" + repairId + '\'' +
                ", receiveDate=" + receiveDate +
                ", returnDate=" + returnDate +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
