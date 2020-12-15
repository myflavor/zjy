package cn.myflavor.zjy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class User {
    private Integer id;
    private String openid;
    private String mail;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expiry;

    public User(Integer id, String openid, String mail, Date expiry) {
        this.id = id;
        this.openid = openid;
        this.mail = mail;
        this.expiry = expiry;
    }

    public User(String openid, String mail, Date expiry) {
        this.openid = openid;
        this.mail = mail;
        this.expiry = expiry;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", mail='" + mail + '\'' +
                ", expiry=" + expiry +
                '}';
    }
}
