package lk.ijse.computershop.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class TransactionDTO {
    private String transactionId;
    private Date date;
    private Time time;
    private String customerId;
    private String customerName;
    private String telNumber;
    private String type;
    private double total;
    private ArrayList<ItemTransactionDetailsDTO>itemTransactionDetailsArrayList;
    private ArrayList<RepairTransactionDetailsDTO> repairTransactionDetailsDTOArrayList;
    public TransactionDTO() {
    }

    public TransactionDTO(String transactionId, Date date, Time time, String customerId, String customerName, String telNumber, String type, double total, ArrayList<ItemTransactionDetailsDTO> itemTransactionDetailsArrayList, ArrayList<RepairTransactionDetailsDTO> repairTransactionDetailsDTOArrayList) {
        this.transactionId = transactionId;
        this.date = date;
        this.time = time;
        this.customerId = customerId;
        this.customerName = customerName;
        this.telNumber = telNumber;
        this.type = type;
        this.total = total;
        this.itemTransactionDetailsArrayList = itemTransactionDetailsArrayList;
        this.repairTransactionDetailsDTOArrayList = repairTransactionDetailsDTOArrayList;
    }

    public TransactionDTO(String transactionId, Date date, Time time, String customerId, String type) {
        this.setTransactionId(transactionId);
        this.setDate(date);
        this.setTime(time);
        this.setCustomerId(customerId);
        this.setType(type);
    }

    public TransactionDTO(String transactionId, Date date, Time time, String customerId, String type, double total, ArrayList<ItemTransactionDetailsDTO> itemTransactionDetailsArrayList, ArrayList<RepairTransactionDetailsDTO> repairTransactionDetailsDTOArrayList) {
        this.setTransactionId(transactionId);
        this.setDate(date);
        this.setTime(time);
        this.setCustomerId(customerId);
        this.setType(type);
        this.setTotal(total);
        this.setItemTransactionDetailsArrayList(itemTransactionDetailsArrayList);
        this.setRepairTransactionDetailsDTOArrayList(repairTransactionDetailsDTOArrayList);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<ItemTransactionDetailsDTO> getItemTransactionDetailsArrayList() {
        return itemTransactionDetailsArrayList;
    }

    public void setItemTransactionDetailsArrayList(ArrayList<ItemTransactionDetailsDTO> itemTransactionDetailsArrayList) {
        this.itemTransactionDetailsArrayList = itemTransactionDetailsArrayList;
    }

    public ArrayList<RepairTransactionDetailsDTO> getRepairTransactionDetailsArrayList() {
        return getRepairTransactionDetailsDTOArrayList();
    }

    public void setRepairTransactionDetailsArrayList(ArrayList<RepairTransactionDetailsDTO> repairTransactionDetailsDTOArrayList) {
        this.setRepairTransactionDetailsDTOArrayList(repairTransactionDetailsDTOArrayList);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public ArrayList<RepairTransactionDetailsDTO> getRepairTransactionDetailsDTOArrayList() {
        return repairTransactionDetailsDTOArrayList;
    }

    public void setRepairTransactionDetailsDTOArrayList(ArrayList<RepairTransactionDetailsDTO> repairTransactionDetailsDTOArrayList) {
        this.repairTransactionDetailsDTOArrayList = repairTransactionDetailsDTOArrayList;
    }
}
