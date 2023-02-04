package lk.ijse.computershop.dto;

public class SuppliesDetailsDTO {
    private String suppliesId;
    private String itemCode;
    private int quantity;
    private double unitPrice;

    public SuppliesDetailsDTO() {
    }


    public SuppliesDetailsDTO(String suppliesId, String itemCode, int quantity, double unitPrice) {
        this.setSuppliesId(suppliesId);
        this.setItemCode(itemCode);
        this.setQuantity(quantity);
        this.setUnitPrice(unitPrice);
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
