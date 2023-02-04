package lk.ijse.computershop.dto;

public class UserDTO {
    private String userId;
    private String userName;
    private String userPassword;
    private String nic;
    private String telephoneNumber;
    private String email;
    private String rank;

    public UserDTO() {
    }

    public UserDTO(String userId, String userName, String userPassword, String nic, String telephoneNumber, String email, String rank) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.nic = nic;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.rank = rank;
    }

    public UserDTO(String userName, String userPassword, String rank) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.rank = rank;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
