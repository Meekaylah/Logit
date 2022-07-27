package com.logit_attendance.logit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.logit_attendance.logit.R;
import com.logit_attendance.logit.utilities.MyToast;

import org.jetbrains.annotations.NotNull;

public class ForgotPassword extends AppCompatActivity {

    private final MyToast toast = new MyToast(this);

    private FirebaseAuth firebaseAuth = null;
    private ImageView leftArrow = null;
    private EditText email = null;
    private Button reset = null;
    private ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();

        leftArrow = findViewById(R.id.leftArrow);
        email = findViewById(R.id.email);
        reset = findViewById(R.id.reset);
        progressBar = findViewById(R.id.progressBar);

        email.setOnClickListener(onClickListener);
        reset.setOnClickListener(onClickListener);

        email.setText("okaforrichard76@gmail.com");
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.leftArrow:
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                    break;
                case R.id.reset:
                    reset();
                    break;
            }
        }
    };

    private void reset(){
        String mail = email.getText().toString().trim();
        if(!mail.isEmpty()){
            showProgressBar(true);
            firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    showProgressBar(false);
                    toast.makeLong("Password reset link as been sent to your email");
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
            });
        }else{
            email.setError("Invalid email");
        }
    }

    private void showProgressBar(boolean bool){
        if(bool){
            leftArrow.setEnabled(false);
            reset.setEnabled(false);
            email.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            leftArrow.setEnabled(true);
            reset.setEnabled(true);
            email.setEnabled(true);
            progressBar.setVisibility(View.GONE);
        }
    }
}