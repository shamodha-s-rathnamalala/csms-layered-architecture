package lk.ijse.computershop.entity;

public class SuppliesDetails implements SuperEntity{
    private String suppliesId;
    private String itemCode;
    private int quantity;
    private double unitPrice;

    public SuppliesDetails() {
    }

    public SuppliesDetails(String suppliesId, String itemCode, int quantity, double unitPrice) {
        this.suppliesId = suppliesId;
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getSuppliesId() {
        return suppliesId;
    }

    public void setSuppliesId(String suppliesId) {
        this.suppliesId = suppliesId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
