package com.logit_attendance.logit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.logit_attendance.logit.R;
import com.logit_attendance.logit.database.CurrentUser;
import com.logit_attendance.logit.database.Table;
import com.logit_attendance.logit.utilities.FirebaseManager;
import com.logit_attendance.logit.utilities.Internet;
import com.logit_attendance.logit.utilities.Log;
import com.logit_attendance.logit.utilities.MyToast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private final MyToast toast = new MyToast(this);

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(FirebaseManager.referenceURL);
    private FirebaseAuth firebaseAuth = null;
    private Table table;

    private ImageView logitIcon = null;
    private ImageView leftArrow = null;
    private EditText regNo = null;
    private EditText password = null;
    private CheckBox remember = null;
    private TextView forgot = null;
    private Button login = null;
    private Button signUp = null;
    private ProgressBar progressBar = null;

    private String id = "";
    private String fn = "";
    private String mn = "";
    private String ln = "";
    private String email = "";
    private String mo = "";
    private String bd = "";
    private String dID = "";
    private String rn = "";
    private String pass = "";
    private final ArrayList<Log> logs = new ArrayList<>();
    private boolean passwordVisible;

    @Override
    protected void onStart() {
        super.onStart();
        rn = regNo.getText().toString().toLowerCase().trim();

        if (!table.isCurrentUserEmpty()) {
            rn = table.getCurrentUser().getRegNumber().toLowerCase();
            regNo.setText(rn);
        }

        if (!rn.isEmpty())
            if (table.isCurrentUserExits(rn)) {
                CurrentUser currentUser = table.getCurrentUserByRegNumber(rn);
                if (currentUser.isRememberPassword()) {
                    remember.setChecked(true);
                }
            }

//        toast.makeLong(table.isCurrentUserExits(rn) ? "Exists" : "Not Exists");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        table = new Table(this);

        logitIcon = findViewById(R.id.logitIcon);
        leftArrow = findViewById(R.id.leftArrow);
        regNo = findViewById(R.id.regNo);
        password = findViewById(R.id.password);
        remember = findViewById(R.id.remember);
        forgot = findViewById(R.id.forgot);
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);
        progressBar = findViewById(R.id.progressBar);

        logitIcon.setOnClickListener(onClickListener);
        leftArrow.setOnClickListener(onClickListener);
        login.setOnClickListener(onClickListener);
        signUp.setOnClickListener(onClickListener);
        forgot.setOnClickListener(onClickListener);

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= password.getRight() - password.getCompoundDrawables()[RIGHT].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (passwordVisible) {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.ic_invisible, 0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.ic_visible, 0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.logitIcon:
//                    table.showAllCurrentUsers();
                    FirebaseManager.showUserData(Login.this, rn);
                    break;
                case R.id.leftArrow:
                    finish();
                    break;
                case R.id.login:
                    if (Internet.isConnected(Login.this))
                        login();
                    else
                        toast.makeLong("No Internet Connection");
                    break;
                case R.id.forgot:
                    startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
                    break;
                case R.id.signUp:
                    startActivity(new Intent(getApplicationContext(), SignUp.class));
                    break;
            }
        }
    };

    private void login() {
        rn = regNo.getText().toString().toLowerCase().trim();

        CurrentUser currentUser = table.getCurrentUserByRegNumber(rn);
        if (currentUser != null && currentUser.isRememberPassword())
            password.setText(currentUser.getPassword());

        if (validLoginInput()) {
            showProgressBar(true);
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(rn)) {
                        showProgressBar(false);

                        id = snapshot.child(rn).child("id").getValue(String.class);
                        fn = snapshot.child(rn).child("firstName").getValue(String.class);
                        mn = snapshot.child(rn).child("middleName").getValue(String.class);
                        ln = snapshot.child(rn).child("lastName").getValue(String.class);
                        email = snapshot.child(rn).child("email").getValue(String.class);
                        mo = snapshot.child(rn).child("mobile").getValue(String.class);
                        bd = snapshot.child(rn).child("brand").getValue(String.class);
                        dID = snapshot.child(rn).child("deviceID").getValue(String.class);
                        pass = snapshot.child(rn).child("password").getValue(String.class);

                        if (pass.equals(password.getText().toString().trim())) {
                            showProgressBar(true);
                            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if (firebaseAuth.getCurrentUser() == null)
                                        toast.makeLong("Unable to Login");
                                    else {
                                        setCurrentUser();
                                        startActivity(new Intent(getApplicationContext(), Home.class));
                                        toast.makeLong("Logged In Successfully");
                                        finish();
                                    }
                                    showProgressBar(false);
                                }
                            });
                        } else {
                            showError(password, "Incorrect Password");
                            showProgressBar(false);
                        }
                    } else {
                        showError(regNo, "Registration Number Does not Exist");
                        showProgressBar(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    private boolean validLoginInput() {
        if (rn.isEmpty()) {
            showError(regNo, "Enter a Registration Number");
            return false;
        } else if (password.getText().toString().trim().isEmpty()) {
            showError(password, "Please Enter Your Password");
            return false;
        } else
            return true;
    }

    private void showError(EditText editText, String message) {
        editText.setError(message);
        editText.requestFocus();
    }

    private void setCurrentUser() {
        if (!table.isCurrentUserExits(rn)) {
            table.deleteAllCurrentUsers();
            table.addToCurrentUser(new CurrentUser(fn, mn, ln, email, mo, bd, dID, rn, pass).setId(id).setRememberPassword(false).setLoggedIn(true));
        }

        if (remember.isChecked())
            table.updateCurrentUser(new CurrentUser(fn, mn, ln, email, mo, bd, dID, rn, pass).setId(id).setRememberPassword(true).setLoggedIn(true));
        else
            table.updateCurrentUser(new CurrentUser(fn, mn, ln, email, mo, bd, dID, rn, pass).setId(id).setRememberPassword(false).setLoggedIn(true));
    }

    private void showProgressBar(boolean bool) {
        progressBar.setVisibility(bool ? View.VISIBLE : View.GONE);
        regNo.setEnabled(!bool);
        password.setEnabled(!bool);
        login.setEnabled(!bool);
        signUp.setEnabled(!bool);
    }
}