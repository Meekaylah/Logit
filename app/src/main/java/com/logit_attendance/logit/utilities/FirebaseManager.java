package com.logit_attendance.logit.utilities;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.logit_attendance.logit.database.CurrentUser;

public abstract class FirebaseManager {

    private static MyToast toast;

    public static final String referenceURL = "https://logit-96fe1-default-rtdb.firebaseio.com/";

    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(referenceURL);
    private final FirebaseAuth firebaseAuth = null;

    public static void sendUserDataToLocalStorage(String regNumber){
        if(!regNumber.isEmpty()){
            CurrentUser currentUser = new CurrentUser();
            databaseReference.child("users").child(regNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    currentUser.setId(snapshot.child("id").getValue(String.class));
                    currentUser.setFirstName(snapshot.child("firstName").getValue(String.class));
                    currentUser.setMiddleName(snapshot.child("middleName").getValue(String.class));
                    currentUser.setLastName(snapshot.child("lastName").getValue(String.class));
                    currentUser.setEmail(snapshot.child("email").getValue(String.class));
                    currentUser.setMobile(snapshot.child("mobile").getValue(String.class));
                    currentUser.setBrand(snapshot.child("brand").getValue(String.class));
                    currentUser.setDeviceID(snapshot.child("deviceID").getValue(String.class));
                    currentUser.setRegNumber(snapshot.child("regNumber").getValue(String.class));
                    currentUser.setPassword(snapshot.child("password").getValue(String.class));

                    Iterable<DataSnapshot> dataSnapshots = snapshot.child("logs").getChildren();
                    for (DataSnapshot dataSnapshot : dataSnapshots) {
                        String course = dataSnapshot.child("course").getValue(String.class);
                        String signedIn = dataSnapshot.child("signedIn").getValue(String.class);
                        String signedOut = dataSnapshot.child("signedOut").getValue(String.class);
                        String date = dataSnapshot.child("date").getValue(String.class);
                        Boolean isCurrentLog = dataSnapshot.child("currentLog").getValue(Boolean.class);
                        currentUser.getLogs().add(new Log(course, signedIn, signedOut, date, Boolean.TRUE.equals(isCurrentLog)));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }

    public static void showUserData(Context context, String regNumber){
        if(!regNumber.isEmpty()){
            CurrentUser currentUser = new CurrentUser();
            databaseReference.child("users").child(regNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    currentUser.setId(snapshot.child("id").getValue(String.class));
                    currentUser.setFirstName(snapshot.child("firstName").getValue(String.class));
                    currentUser.setMiddleName(snapshot.child("middleName").getValue(String.class));
                    currentUser.setLastName(snapshot.child("lastName").getValue(String.class));
                    currentUser.setEmail(snapshot.child("email").getValue(String.class));
                    currentUser.setMobile(snapshot.child("mobile").getValue(String.class));
                    currentUser.setBrand(snapshot.child("brand").getValue(String.class));
                    currentUser.setDeviceID(snapshot.child("deviceID").getValue(String.class));
                    currentUser.setRegNumber(snapshot.child("regNumber").getValue(String.class));
                    currentUser.setPassword(snapshot.child("password").getValue(String.class));

                    Iterable<DataSnapshot> dataSnapshots = snapshot.child("logs").getChildren();
                    for (DataSnapshot dataSnapshot : dataSnapshots) {
                        String course = dataSnapshot.child("course").getValue(String.class);
                        String signedIn = dataSnapshot.child("signedIn").getValue(String.class);
                        String signedOut = dataSnapshot.child("signedOut").getValue(String.class);
                        String date = dataSnapshot.child("date").getValue(String.class);
                        Boolean isCurrentLog = dataSnapshot.child("currentLog").getValue(Boolean.class);
                        currentUser.getLogs().add(new Log(course, signedIn, signedOut, date, Boolean.TRUE.equals(isCurrentLog)));
                    }

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("ID: " + currentUser.getId() + "\n");
                    buffer.append("First Name: " + currentUser.getFirstName() + "\n");
                    buffer.append("Middle Name: " + currentUser.getMiddleName() + "\n");
                    buffer.append("Last Name: " + currentUser.getLastName() + "\n");
                    buffer.append("Email: " + currentUser.getEmail() + "\n");
                    buffer.append("Phone: " + currentUser.getMobile() + "\n");
                    buffer.append("Brand: " + currentUser.getBrand() + "\n");
                    buffer.append("Device ID: " + currentUser.getDeviceID() + "\n");
                    buffer.append("Registration Number: " + currentUser.getRegNumber() + "\n");
                    buffer.append("Password: " + currentUser.getPassword() + "\n");
                    for(Log log : currentUser.getLogs()){
                        buffer.append("Log: " + log.getCourse() + "\n");
                    }
                    buffer.append("\n");

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("CurrentUser Record");
                    builder.setMessage(buffer.toString());
                    builder.show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }
}
