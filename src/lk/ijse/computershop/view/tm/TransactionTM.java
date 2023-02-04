package lk.ijse.computershop.view.tm;

public class TransactionTM {
    private String customerId;
    private String transactionId;
    private String dataTime;
    private String Type;
    private String customerName;
    private String telNumber;
    private double total;

    public TransactionTM() {
    }

    public TransactionTM(String customerId, String transactionId, String dataTime, String type, String customerName, String telNumber, double total) {
        this.customerId = customerId;
        this.transactionId = transactionId;
        this.dataTime = dataTime;
        Type = type;
        this.customerName = customerName;
        this.telNumber = telNumber;
        this.total = total;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
