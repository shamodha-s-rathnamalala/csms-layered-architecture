package lk.ijse.computershop.dto;


import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class SuppliesDTO {
    private String suppliesId;
    private Date date;
    private Time time;
    private String supplierId;
    private double total;
    private ArrayList<SuppliesDetailsDTO> suppliesDetailsArrayList;

    public SuppliesDTO() {
    }

    public SuppliesDTO(String suppliesId, Date date, Time time, String supplierId, double total, ArrayList<SuppliesDetailsDTO> suppliesDetailsArrayList) {
        this.suppliesId = suppliesId;
        this.date = date;
        this.time = time;
        this.supplierId = supplierId;
        this.total = total;
        this.suppliesDetailsArrayList = suppliesDetailsArrayList;
    }

    public SuppliesDTO(String suppliesId, Date date, Time time, String supplierId, ArrayList<SuppliesDetailsDTO> suppliesDetailsArrayList) {
        this.suppliesId = suppliesId;
        this.date = date;
        this.time = time;
        this.supplierId = supplierId;
        this.suppliesDetailsArrayList = suppliesDetailsArrayList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getSuppliesId() {
        return suppliesId;
    }

    public void setSuppliesId(String suppliesId) {
        this.suppliesId = suppliesId;
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public ArrayList<SuppliesDetailsDTO> getSuppliesDetailsArrayList() {
        return suppliesDetailsArrayList;
    }

    public void setSuppliesDetailsArrayList(ArrayList<SuppliesDetailsDTO> suppliesDetailsArrayList) {
        this.suppliesDetailsArrayList = suppliesDetailsArrayList;
    }
}
