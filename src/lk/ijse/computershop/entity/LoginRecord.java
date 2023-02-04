package lk.ijse.computershop.entity;

import com.sun.org.glassfish.external.statistics.TimeStatistic;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

public class LoginRecord implements SuperEntity{
    private String loginId;
    private Date date;
    private Time time;

    private String userId;

    public LoginRecord() {
    }

    public LoginRecord(String loginId, Date date, Time time, String userId) {
        this.loginId = loginId;
        this.date = date;
        this.time = time;
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
