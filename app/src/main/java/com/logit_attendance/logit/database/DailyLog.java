package com.logit_attendance.logit.database;

public class DailyLog {

    private int id;
    private String regNumber;
    private String course;
    private String signedIn;
    private String signedOut;
    private String date;
    private boolean isCurrentLog;
    private boolean isSynced;

    public DailyLog(){}

    public DailyLog(int id, String regNumber, String course, String signedIn, String signedOut, String date, boolean isCurrentLog, boolean isSynced) {
        this.id = id;
        this.regNumber = regNumber;
        this.course = course;
        this.signedIn = signedIn;
        this.signedOut = signedOut;
        this.date = date;
        this.isCurrentLog = isCurrentLog;
        this.isSynced = isSynced;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSignedIn() {
        return signedIn;
    }

    public void setSignedIn(String signedIn) {
        this.signedIn = signedIn;
    }

    public String getSignedOut() {
        return signedOut;
    }

    public void setSignedOut(String signedOut) {
        this.signedOut = signedOut;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCurrentLog() {
        return isCurrentLog;
    }

    public void setCurrentLog(boolean currentLog) {
        isCurrentLog = currentLog;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }
}
