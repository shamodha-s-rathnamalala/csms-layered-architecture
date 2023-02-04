package lk.ijse.computershop.entity;


import java.sql.Date;
import java.sql.Time;

public class Supplies implements SuperEntity{
    private String suppliesId;
    private Date date;
    private Time time;
    private String supplierId;

    public Supplies() {
    }

    public Supplies(String suppliesId, Date date, Time time, String supplierId) {
        this.suppliesId = suppliesId;
        this.date = date;
        this.time = time;
        this.supplierId = supplierId;
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
}
