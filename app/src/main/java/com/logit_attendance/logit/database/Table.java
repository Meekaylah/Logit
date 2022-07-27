package com.logit_attendance.logit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class Table extends SQLiteOpenHelper {

    private final Context context;

    // LOGIT APP TABLE
    private static final String LOGIT = "Logit";
    private static final String LOGIT_COLUMN_ID = "id";
    private static final String LOGIT_IS_INSTALLED = "isInstalled";
    private static final String LOGIT_IS_DONE_WITH_INSTRUCTIONS = "isDoneWithInstructions";
    private static final String CREATE_LOGIT = "Create Table " + LOGIT + "(" +
            LOGIT_COLUMN_ID + " integer primary key autoincrement, " +
            LOGIT_IS_INSTALLED + " bool, " +
            LOGIT_IS_DONE_WITH_INSTRUCTIONS + " bool)";

    // CURRENT USER TABLE
    private static final String CURRENT_USER = "CurrentUser";
    private static final String CURRENT_USER_COLUMN_ID = "id";
    private static final String CURRENT_USER_COLUMN_FIRST_NAME = "firstName";
    private static final String CURRENT_USER_COLUMN_MIDDLE_NAME = "middleName";
    private static final String CURRENT_USER_COLUMN_LAST_NAME = "lastName";
    private static final String CURRENT_USER_COLUMN_EMAIL = "email";
    private static final String CURRENT_USER_COLUMN_MOBILE = "mobile";
    private static final String CURRENT_USER_COLUMN_BRAND = "brand";
    private static final String CURRENT_USER_COLUMN_DEVICE_ID = "deviceID";
    private static final String CURRENT_USER_COLUMN_REG_NUMBER = "regNumber";
    private static final String CURRENT_USER_COLUMN_PASSWORD = "password";
    private static final String CURRENT_USER_COLUMN_IS_REMEMBER_PASSWORD = "isRememberPassword";
    private static final String CURRENT_USER_COLUMN_IS_LOGGED_IN = "isLoggedIn";
    private static final String CREATE_CURRENT_USER = "CREATE TABLE " + CURRENT_USER + "(" +
            CURRENT_USER_COLUMN_ID + " integer primary key autoincrement, " +
            CURRENT_USER_COLUMN_FIRST_NAME + " text, " +
            CURRENT_USER_COLUMN_MIDDLE_NAME + " text, " +
            CURRENT_USER_COLUMN_LAST_NAME + " text, " +
            CURRENT_USER_COLUMN_EMAIL + " text, " +
            CURRENT_USER_COLUMN_MOBILE + " text, " +
            CURRENT_USER_COLUMN_BRAND + " text, " +
            CURRENT_USER_COLUMN_DEVICE_ID + " text, " +
            CURRENT_USER_COLUMN_REG_NUMBER + " text, " +
            CURRENT_USER_COLUMN_PASSWORD + " text," +
            CURRENT_USER_COLUMN_IS_REMEMBER_PASSWORD + " bool," +
            CURRENT_USER_COLUMN_IS_LOGGED_IN + " bool)";


    // DAILY LOG TABLE
    protected static final String DAILY_LOG = "DailyLog";
    protected static final String DAILY_LOG_COLUMN_ID = "id";
    protected static final String DAILY_LOG_COLUMN_REG_NUMBER = "regNumber";
    protected static final String DAILY_LOG_COLUMN_COURSE = "course";
    protected static final String DAILY_LOG_COLUMN_SIGNED_IN = "signedIn";
    protected static final String DAILY_LOG_COLUMN_SIGNED_OUT = "signedOut";
    protected static final String DAILY_LOG_COLUMN_DATE = "date";
    protected static final String DAILY_LOG_COLUMN_IS_CURRENT_LOG = "isCurrentLog";
    protected static final String DAILY_LOG_COLUMN_IS_SYNCED = "isSynced";
    private static final String CREATE_DAILY_LOG = "CREATE TABLE " + DAILY_LOG + "(" +
            DAILY_LOG_COLUMN_ID + " integer primary key autoincrement, " +
            DAILY_LOG_COLUMN_REG_NUMBER + " text, " +
            DAILY_LOG_COLUMN_COURSE + " text, " +
            DAILY_LOG_COLUMN_SIGNED_IN + " text," +
            DAILY_LOG_COLUMN_SIGNED_OUT + " text," +
            DAILY_LOG_COLUMN_DATE + " text," +
            DAILY_LOG_COLUMN_IS_CURRENT_LOG + " bool," +
            DAILY_LOG_COLUMN_IS_SYNCED + " bool)";


    public Table(Context context) {
        super(context, "LogitDB.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOGIT);
        db.execSQL(CREATE_CURRENT_USER);
        db.execSQL(CREATE_DAILY_LOG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}





    // ALL TABLES
    protected boolean addToTable(String tableName, ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(tableName, null, cv);
        db.close();
        return result != -1; // result == -1 ? false : true
    }

    protected boolean isTableEmpty(String tableName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tableName + " limit 1", null);
        boolean isEmpty = !cursor.moveToNext();
        db.close();
        cursor.close();
        return isEmpty;
    }

    protected Cursor selectAll(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + tableName, null);
    }

    protected Cursor selectAllFromTableWhereColumnEquals(String tableName, String columnName, String equalsValue){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + tableName + " where " + columnName + " = ?", new String[]{equalsValue});
    }

    protected void deleteAllData(String tableName, String keyColumn) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = selectAll(tableName);
        if(cursor.moveToNext()){
            do{
                db.delete(tableName, keyColumn + " = ? ", new String[]{cursor.getString(0)});
            }while(cursor.moveToNext());
        }
    }

    protected void showAllData(String tableName){
        Cursor result = selectAll(tableName);
        if(!result.moveToNext()){
            Toast.makeText(context, "No Record Exists", Toast.LENGTH_LONG).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        do{
            buffer.append("ID: " + result.getInt(0) + "\n");
            buffer.append("Reg Number: " + result.getString(1) + "\n");
            buffer.append("Password: " + result.getString(2) + "\n");
            buffer.append("Remember Pass: " + (result.getInt(3) == 1?"true":"false") + "\n\n");
        } while (result.moveToNext());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("User Records");
        builder.setMessage(buffer.toString());
        builder.show();
    }





    // LOGIT TABLE
    public boolean addToLogit(){
        ContentValues cv = new ContentValues();
        cv.put(LOGIT_IS_INSTALLED, true);
        cv.put(LOGIT_IS_DONE_WITH_INSTRUCTIONS, false);
        return addToTable(LOGIT, cv);
    }

    public boolean isLogitEmpty(){
        return isTableEmpty(LOGIT);
    }

    public boolean isDoneWithInstructions(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + LOGIT + " limit 1", null);
        if(cursor.moveToNext()){
            return cursor.getInt(2) == 1;
        }
        return false;
    }

    public Boolean setDoneWithInstructions(boolean bool){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOGIT_IS_DONE_WITH_INSTRUCTIONS, bool);
        long result = db.update(LOGIT, cv, LOGIT_IS_INSTALLED + " = ?", new String[]{"1"});
        return result != -1;
    }

    public void showAllLogit() {
        showAllData(LOGIT);
    }





    // CURRENT USER TABLE
    public boolean addToCurrentUser(CurrentUser currentUser){
        ContentValues cv = new ContentValues();
        cv.put(CURRENT_USER_COLUMN_FIRST_NAME, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_MIDDLE_NAME, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_LAST_NAME, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_EMAIL, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_MOBILE, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_BRAND, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_DEVICE_ID, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_REG_NUMBER, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_PASSWORD, currentUser.getPassword());
        cv.put(CURRENT_USER_COLUMN_IS_REMEMBER_PASSWORD, currentUser.isRememberPassword());
        cv.put(CURRENT_USER_COLUMN_IS_LOGGED_IN, currentUser.isLoggedIn());
        return addToTable(CURRENT_USER, cv);
    }

    public boolean isCurrentUserEmpty(){
        return isTableEmpty(CURRENT_USER);
    }

    public boolean deleteCurrentUser(CurrentUser currentUser){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Delete from " + CURRENT_USER + " where " + CURRENT_USER_COLUMN_ID + " = " + currentUser.getId();
        Cursor cursor = db.rawQuery(query, null);
        boolean isDeleted = cursor.moveToNext();
        db.close();
        cursor.close();
        return isDeleted;
    }

    public CurrentUser getCurrentUserByRegNumber(String regNumber){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + CURRENT_USER + " where " + CURRENT_USER_COLUMN_REG_NUMBER + " = ? ";
        Cursor cursor = db.rawQuery(query, new String[]{regNumber});
        if(cursor.moveToNext()){
            String id = cursor.getString(0);
            String fn = cursor.getString(1);
            String mn = cursor.getString(2);
            String ln = cursor.getString(3);
            String em = cursor.getString(4);
            String mo = cursor.getString(5);
            String bd = cursor.getString(6);
            String dID = cursor.getString(7);
            String rn = cursor.getString(8);
            String pass = cursor.getString(9);
            boolean isRemPass = cursor.getInt(10) == 1;
            boolean isLoggedIn = cursor.getInt(11) == 1;
            return new CurrentUser(fn, mn, ln, em, mo, bd, dID, rn, pass).setRememberPassword(isRemPass).setLoggedIn(isLoggedIn);
        }
        db.close();
        cursor.close();
        return null;
    }

    public CurrentUser getCurrentUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + CURRENT_USER, null);
        if(cursor.moveToNext()){
            String id = cursor.getString(0);
            String fn = cursor.getString(1);
            String mn = cursor.getString(2);
            String ln = cursor.getString(3);
            String em = cursor.getString(4);
            String mo = cursor.getString(5);
            String bd = cursor.getString(6);
            String dID = cursor.getString(7);
            String rn = cursor.getString(8);
            String pass = cursor.getString(9);
            boolean isRemPass = cursor.getInt(10) == 1;
            boolean isLoggedIn = cursor.getInt(11) == 1;
            return new CurrentUser(fn, mn, ln, em, mo, bd, dID, rn, pass).setRememberPassword(isRemPass).setLoggedIn(isLoggedIn);
        }
        db.close();
        cursor.close();
        return null;
    }

    public boolean isCurrentUserExits(String regNumber){
        return getCurrentUserByRegNumber(regNumber) != null;
    }

    public boolean updateCurrentUser(CurrentUser currentUser){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CURRENT_USER_COLUMN_FIRST_NAME, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_MIDDLE_NAME, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_LAST_NAME, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_EMAIL, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_MOBILE, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_BRAND, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_DEVICE_ID, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_REG_NUMBER, currentUser.getRegNumber());
        cv.put(CURRENT_USER_COLUMN_PASSWORD, currentUser.getPassword());
        cv.put(CURRENT_USER_COLUMN_IS_REMEMBER_PASSWORD, currentUser.isRememberPassword());
        cv.put(CURRENT_USER_COLUMN_IS_LOGGED_IN, currentUser.isLoggedIn());
        long result = db.update(CURRENT_USER, cv, CURRENT_USER_COLUMN_REG_NUMBER+ " = ?", new String[]{currentUser.getRegNumber()});
        return result != -1;
    }

    public void deleteAllCurrentUsers() {
        deleteAllData(CURRENT_USER, CURRENT_USER_COLUMN_ID);
    }

    public void showAllCurrentUsers() {
        showAllData(CURRENT_USER);
    }

    public void signOutCurrentUser(){
        CurrentUser currentUser = getCurrentUser();
        currentUser.setLoggedIn(false);
        updateCurrentUser(currentUser);
    }





    // DAILY LOG TABLE
    public boolean addToDailyLog(DailyLog dailyLog){
        ContentValues cv = new ContentValues();
        cv.put(DAILY_LOG_COLUMN_REG_NUMBER, dailyLog.getRegNumber());
        cv.put(DAILY_LOG_COLUMN_COURSE, dailyLog.getCourse());
        cv.put(DAILY_LOG_COLUMN_SIGNED_IN, dailyLog.getSignedIn());
        cv.put(DAILY_LOG_COLUMN_SIGNED_OUT, dailyLog.getSignedOut());
        cv.put(DAILY_LOG_COLUMN_DATE, dailyLog.getDate());
        cv.put(DAILY_LOG_COLUMN_IS_CURRENT_LOG, dailyLog.isCurrentLog());
        cv.put(DAILY_LOG_COLUMN_IS_SYNCED, dailyLog.isSynced());
        return addToTable(DAILY_LOG, cv);
    }
}