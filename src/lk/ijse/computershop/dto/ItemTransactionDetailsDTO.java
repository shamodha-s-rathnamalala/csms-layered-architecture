package lk.ijse.computershop.dto;

public class ItemTransactionDetailsDTO {
    private String transactionId;
    private String itemCode;
    private int quantity;
    private double unitPrice;

    public ItemTransactionDetailsDTO() {
    }

    public ItemTransactionDetailsDTO(String transactionId, String itemCode, int quantity, double unitPrice) {
        this.transactionId = transactionId;
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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
