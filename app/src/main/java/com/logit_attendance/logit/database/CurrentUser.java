package com.logit_attendance.logit.database;

import com.logit_attendance.logit.utilities.Log;

import java.util.ArrayList;

public class CurrentUser {

    private String id = "";
    private String firstName = "";
    private String middleName = "";
    private String lastName = "";
    private String email = "";
    private String mobile = "";
    private String brand = "";
    private String deviceID = "";
    private String regNumber = "";
    private String password = "";
    private boolean isRememberPassword = false;
    private boolean isLoggedIn = false;
    private ArrayList<Log> logs = new ArrayList<>();

    public CurrentUser() {}

    public CurrentUser(
           String firstName,
           String middleName,
           String lastName,
           String email,
           String mobile,
           String brand,
           String deviceID,
           String regNumber,
           String password) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.brand = brand;
        this.deviceID = deviceID;
        this.regNumber = regNumber;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public CurrentUser setId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberPassword() {
        return isRememberPassword;
    }

    public CurrentUser setRememberPassword(boolean rememberPassword) {
        isRememberPassword = rememberPassword;
        return this;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public CurrentUser setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
        return this;
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public CurrentUser setLogs(ArrayList<Log> logs) {
        this.logs = logs;
        return this;
    }
}
