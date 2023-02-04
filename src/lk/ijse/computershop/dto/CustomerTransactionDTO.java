package lk.ijse.computershop.dto;

public class CustomerTransactionDTO {
    private String name;
    private double total;
    private String type;

    public CustomerTransactionDTO() {
    }

    public CustomerTransactionDTO(String name, double total, String type) {
        this.name = name;
        this.total = total;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
