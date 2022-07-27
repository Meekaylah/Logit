package com.logit_attendance.logit.utilities;

import android.content.Context;
import android.database.Cursor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.logit_attendance.logit.database.DailyLog;
import com.logit_attendance.logit.database.Table;

import java.util.ArrayList;

public abstract class LocalStorageManager extends Table {

    private static MyToast toast;

    public static final String referenceURL = "https://logit-96fe1-default-rtdb.firebaseio.com";

    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(referenceURL);
    private final FirebaseAuth firebaseAuth = null;

    public LocalStorageManager(Context context) {
        super(context);
    }

    public ArrayList<DailyLog> getRegNumberDailyLog(String regNumber){
        if(super.isCurrentUserExits(regNumber)){
            Cursor cursor = super.selectAllFromTableWhereColumnEquals(DAILY_LOG, DAILY_LOG_COLUMN_REG_NUMBER, regNumber);
            ArrayList<DailyLog> dailyLogs = new ArrayList<>();
            if(cursor.moveToNext()){
                do{
                    int id = cursor.getInt(0);
                    String rn = cursor.getString(1);
                    String course = cursor.getString(2);
                    String signedIn = cursor.getString(3);
                    String signedOut = cursor.getString(4);
                    String date = cursor.getString(5);
                    boolean isCurrentLog = cursor.getInt(6) == 1;
                    boolean isSynced = cursor.getInt(7) == 1;
                    dailyLogs.add(new DailyLog(id, rn, course, signedIn, signedOut, date, isCurrentLog, isSynced));
                }while(cursor.moveToNext());
            }
            return dailyLogs;
        }
        return null;
    }

    public void sendRegNumberDailyLogToFirebase(String regNumber, ArrayList<DailyLog> dailyLogs){
        if(super.isCurrentUserExits(regNumber)){
            ArrayList<Log> logs = new ArrayList<>();
            for(DailyLog dailyLog : dailyLogs){
                String course = dailyLog.getCourse();
                String signedIn = dailyLog.getSignedIn();
                String signedOut = dailyLog.getSignedOut();
                String date = dailyLog.getDate();
                boolean isCurrentLog = dailyLog.isCurrentLog();
                logs.add(new Log());
            }
            databaseReference.child("users").child(regNumber).child("logs").setValue(logs);
        }
    }
}
