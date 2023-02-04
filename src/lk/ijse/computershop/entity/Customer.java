package lk.ijse.computershop.entity;

public class Customer implements SuperEntity{
    private String customerId;
    private String name;
    private String address;
    private String nic;
    private String telephoneNumber;
    private String email;

    public Customer() {
    }

    public Customer(String customerId, String name, String address, String nic, String telephoneNumber, String email) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.nic = nic;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
