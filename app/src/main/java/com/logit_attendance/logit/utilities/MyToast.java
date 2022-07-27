package com.logit_attendance.logit.utilities;

import android.content.Context;
import android.widget.Toast;

public class MyToast{

    private Context context = null;

    public MyToast(Context context) {
        this.context = context;
    }

    public void makeLong(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void makeShort(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
