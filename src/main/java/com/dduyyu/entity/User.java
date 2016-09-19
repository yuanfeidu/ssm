package com.dduyyu.entity;

import java.util.Date;

public class User {
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String mobile;

    private String lastestLoginIp;

    private Date lastestLoginDate;

    private String status;

    private String loginStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getLastestLoginIp() {
        return lastestLoginIp;
    }

    public void setLastestLoginIp(String lastestLoginIp) {
        this.lastestLoginIp = lastestLoginIp == null ? null : lastestLoginIp.trim();
    }

    public Date getLastestLoginDate() {
        return lastestLoginDate;
    }

    public void setLastestLoginDate(Date lastestLoginDate) {
        this.lastestLoginDate = lastestLoginDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus == null ? null : loginStatus.trim();
    }
}