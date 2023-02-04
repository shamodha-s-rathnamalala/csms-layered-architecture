package lk.ijse.computershop.view.tm;

public class SuppliesCartTM {
    private String itemCode;
    private String description;
    private String type;
    private double unitPrice;
    private int oldQuantity;
    private int quantity;
    private int newQuantity;
    private double total;

    public SuppliesCartTM() {
    }


    public SuppliesCartTM(String itemCode, String description, String type, double unitPrice, int oldQuantity, int quantity, int newQuantity, double total) {
        this.itemCode = itemCode;
        this.description = description;
        this.type = type;
        this.unitPrice = unitPrice;
        this.oldQuantity = oldQuantity;
        this.quantity = quantity;
        this.newQuantity = newQuantity;
        this.total = total;
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

    public int getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(int oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    public int getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
