package com.logit_attendance.logit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.logit_attendance.logit.R;
import com.logit_attendance.logit.database.CurrentUser;
import com.logit_attendance.logit.database.Table;
import com.logit_attendance.logit.utilities.FirebaseManager;
import com.logit_attendance.logit.utilities.MyToast;

public class Home extends AppCompatActivity {

    private final MyToast toast = new MyToast(this);
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(FirebaseManager.referenceURL);
    private Table table;
    private CurrentUser currentUser;

    private TextView regNo;
    private TextView name;
    private TextView email;
    private Button logout;

    private String rn = "";
    private final String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        table = new Table(this);
        currentUser = table.getCurrentUser();

        regNo = findViewById(R.id.regNo);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        logout = findViewById(R.id.logout);
        rn = currentUser.getRegNumber();

        if(rn != null){
            regNo.setText(rn.toUpperCase());
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name.setText(snapshot.child(rn).child("lastName").getValue(String.class).
                            concat(" " + snapshot.child(rn).child("firstName").getValue(String.class)));
                    email.setText(snapshot.child(rn).child("email").getValue(String.class));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table.signOutCurrentUser();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                toast.makeLong("Logged Out Successfully");
            }
        });
    }
    
}