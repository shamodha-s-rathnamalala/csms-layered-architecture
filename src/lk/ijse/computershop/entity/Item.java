package lk.ijse.computershop.entity;

public class Item implements SuperEntity{
    private String itemCode;
    private String itemType;
    private String description;
    private double unitPrice;
    private int qtyOnStock;

    public Item() {
    }

    public Item(String itemCode, String itemType, String description, double unitPrice, int qtyOnStock) {
        this.itemCode = itemCode;
        this.itemType = itemType;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnStock = qtyOnStock;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnStock() {
        return qtyOnStock;
    }

    public void setQtyOnStock(int qtyOnStock) {
        this.qtyOnStock = qtyOnStock;
    }
}
