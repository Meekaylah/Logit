package com.logit_attendance.logit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.logit_attendance.logit.R;
import com.logit_attendance.logit.database.Table;

public class GetStarted extends AppCompatActivity {


    private Table table;

    private ImageView leftArrow = null;
    private Button signUp = null;
    private Button login = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        table = new Table(this);

        leftArrow = findViewById(R.id.leftArrow);
        signUp = findViewById(R.id.signUp);
        login = findViewById(R.id.login);

        leftArrow.setOnClickListener(onClickListener);
        signUp.setOnClickListener(onClickListener);
        login.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.leftArrow:
                    finish();
                    break;
                case R.id.signUp:
                    callActivity(SignUp.class);
                    break;
                case R.id.login:
                    callActivity(Login.class);
                    break;
            }
        }
    };

    public void callActivity(Class<?> sClass){
        startActivity(new Intent(getApplicationContext(), sClass));
        table.setDoneWithInstructions(true);
        finish();
    }
}