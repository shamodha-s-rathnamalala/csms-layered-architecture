package lk.ijse.computershop.entity;

import java.sql.Date;

public class Repair implements SuperEntity{
    private String repairId;
    private Date receiveDate;
    private Date returnDate;
    private String status;
    private String description;
    private String customerId;

    public Repair() {
    }

    public Repair(String repairId, Date receiveDate, Date returnDate, String status, String description, String customerId) {
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

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
