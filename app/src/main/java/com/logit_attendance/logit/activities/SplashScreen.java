package com.logit_attendance.logit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.logit_attendance.logit.R;
import com.logit_attendance.logit.database.Table;

public class SplashScreen extends AppCompatActivity {

    private Table table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        table = new Table(this);

        new CountDownTimer(100, 1000) {
            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                if(table.isLogitEmpty())
                    table.addToLogit();

                if(!table.isDoneWithInstructions())
                    startActivity(new Intent(SplashScreen.this, Instructions.class));
                else
                    if(table.isCurrentUserEmpty() || !table.getCurrentUser().isLoggedIn())
                        startActivity(new Intent(SplashScreen.this, Login.class));
                    else
                        startActivity(new Intent(SplashScreen.this, Home.class));

                finish();
            }
        }.start();
    }
}