package lk.ijse.computershop.entity;

public class Employee implements SuperEntity{
    private String employeeId;
    private String name;
    private String address;
    private String nic;
    private String telephoneNumber;
    private String rank;

    public Employee() {
    }

    public Employee(String employeeId, String name, String address, String nic, String telephoneNumber, String rank) {
        this.employeeId = employeeId;
        this.name = name;
        this.address = address;
        this.nic = nic;
        this.telephoneNumber = telephoneNumber;
        this.rank = rank;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
    @Override
    public String toString(){
        return employeeId+" "+
                name+" "+
                address+" "+
                nic+" "+
                telephoneNumber+" "+
                rank;
    }
}
