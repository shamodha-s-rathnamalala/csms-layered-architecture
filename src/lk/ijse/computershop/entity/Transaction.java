package lk.ijse.computershop.entity;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Transaction implements SuperEntity{
    private String transactionId;
    private Date date;
    private Time time;
    private String customerId;
    private String type;

    public Transaction() {
    }

    public Transaction(String transactionId, Date date, Time time, String customerId, String type) {
        this.transactionId = transactionId;
        this.date = date;
        this.time = time;
        this.customerId = customerId;
        this.type = type;
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
}
