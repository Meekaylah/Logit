package com.logit_attendance.logit;

import android.graphics.drawable.Drawable;

public class LogsModelClass {

    private String class_name;
    private String date;
    private Drawable background;



    LogsModelClass(String class_name, String date, Drawable background) {
        this.class_name = class_name;
        this.date = date;
        this.background = background;
    }

    public String getClass_name() {
        return class_name;
    }

    public String getDate()
    {
        return date;
    }

    public Drawable getBackground(){
        return background;
    }
}
