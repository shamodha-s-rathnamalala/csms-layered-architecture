package lk.ijse.computershop.view.tm;

public class SuppliesTM {
    private String suppliesId;
    private String dateTime;
    private String supplierId;
    private double total;

    public SuppliesTM() {
    }

    public SuppliesTM(String suppliesId, String dateTime, String supplierId, double total) {
        this.suppliesId = suppliesId;
        this.dateTime = dateTime;
        this.supplierId = supplierId;
        this.total = total;
    }

    public String getSuppliesId() {
        return suppliesId;
    }

    public void setSuppliesId(String suppliesId) {
        this.suppliesId = suppliesId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
