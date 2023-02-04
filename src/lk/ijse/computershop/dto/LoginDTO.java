package lk.ijse.computershop.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

public class LoginDTO {
    private String loginId;
    private String userId;
    private String userName;
    private LocalDateTime dateTime;
    private String telephoneNumber;
    private String rank;

    public LoginDTO() {
    }

    public LoginDTO(String loginId, String userId, String userName, LocalDateTime dateTime, String telephoneNumber, String rank) {
        this.loginId = loginId;
        this.userId = userId;
        this.userName = userName;
        this.dateTime = dateTime;
        this.telephoneNumber = telephoneNumber;
        this.rank = rank;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
    public String toString() {
        return "LoginTM{" +
                "loginId='" + loginId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", dateTime=" + dateTime +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
