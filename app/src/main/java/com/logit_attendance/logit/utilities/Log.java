package com.logit_attendance.logit.utilities;

public class Log {

    private String course;
    private String signedIn;
    private String signedOut;
    private String date;
    private boolean isCurrentLog;

    public Log(){}

    public Log(String course, String signedIn, String signedOut, String date, boolean isCurrentLog) {
        this.course = course;
        this.signedIn = signedIn;
        this.signedOut = signedOut;
        this.date = date;
        this.isCurrentLog = isCurrentLog;
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
}
