package com.logit_attendance.logit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.logit_attendance.logit.R;
import com.logit_attendance.logit.database.CurrentUser;
import com.logit_attendance.logit.utilities.FirebaseManager;
import com.logit_attendance.logit.utilities.MyToast;
import com.logit_attendance.logit.utilities.Sibling;

import org.jetbrains.annotations.NotNull;

public class SignUp extends AppCompatActivity {

    private final MyToast toast = new MyToast(this);

    private Sibling<ConstraintLayout> sibling = null;
    private ConstraintLayout current = null;

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(FirebaseManager.referenceURL);
    private FirebaseAuth firebaseAuth = null;
    private CurrentUser currentUser = null;

    private boolean emailExists = false;
    private final static int RC_SIGN_IN = 123;

    private ImageView leftArrow = null;
    private Button back = null;
    private Button next = null;

    private LinearLayout agreement = null;
    private TextView terms = null;
    private TextView policy = null;

    private Button signUp = null;
    private TextView already = null;
    private TextView loginLink = null;

    // NAME
    private ConstraintLayout nameDetails = null;
    private EditText firstName = null;
    private EditText middleName = null;
    private EditText lastName = null;

    // CONTACT
    private ConstraintLayout contactDetails = null;
    private EditText email = null;
    private EditText mobile = null;

    // LAPTOP
    private ConstraintLayout laptopDetails = null;
    private EditText brand = null;
    private EditText deviceID = null;

    // LOGIN
    private ConstraintLayout loginDetails = null;
    private EditText regNo = null;
    private EditText password = null;
    private EditText confirmPassword = null;

    // PROCEED
    private ConstraintLayout signUpImage = null;
    private ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        leftArrow = findViewById(R.id.leftArrow);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);

        agreement = findViewById(R.id.agreement);
        terms = findViewById(R.id.terms);
        policy = findViewById(R.id.policy);

        signUp = findViewById(R.id.signUp);
        already = findViewById(R.id.already);
        loginLink = findViewById(R.id.loginLink);

        // NAME
        nameDetails = findViewById(R.id.nameDetails);
        firstName = findViewById(R.id.firstName);
        middleName = findViewById(R.id.middleName);
        lastName = findViewById(R.id.lastName);

        // CONTACT
        contactDetails = findViewById(R.id.contactDetails);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);

        // LAPTOP
        laptopDetails = findViewById(R.id.laptopDetails);
        brand = findViewById(R.id.brand);
        deviceID = findViewById(R.id.deviceID);

        // LOGIN
        loginDetails = findViewById(R.id.loginDetails);
        regNo = findViewById(R.id.regNo);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        // PROCEED
        signUpImage = findViewById(R.id.signUpImage);
        progressBar = findViewById(R.id.progressBar);

        sibling = new Sibling<>(nameDetails, contactDetails, laptopDetails, loginDetails, signUpImage).loopSibling(false);
        current = nameDetails;
        back.setVisibility(View.GONE);

        leftArrow.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener);
        next.setOnClickListener(onClickListener);
        signUp.setOnClickListener(onClickListener);
        already.setOnClickListener(onClickListener);
        loginLink.setOnClickListener(onClickListener);

        right();
    }

    private void right() {
        firstName.setText("Richard");
        middleName.setText("Tochukwu");
        lastName.setText("Okafor");
        email.setText("okaforrichard76@gmail.com");
        mobile.setText("09097114626");
        brand.setText("HP");
        deviceID.setText("85F0F626-6D0A-477B-B9E9-E3A3BA09B28F");
        regNo.setText("R44");
        password.setText("rekpass");
        confirmPassword.setText("rekpass");
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.leftArrow:
                    leftArrow();
                    break;
                case R.id.back:
                    back();
                    break;
                case R.id.next:
                    next();
                    break;
                case R.id.signUp:
                    signUp();
                    break;
                case R.id.already:
                case R.id.loginLink:
                    startActivity(new Intent(SignUp.this, Login.class));
                    finish();
                    break;
            }
        }
    };

    private void leftArrow() {
        back();
    }

    private void back() {
        if (current.equals(nameDetails)){
            finish();
        }else{
            current.setVisibility(View.GONE);
            current = sibling.previous(current);
            current.setVisibility(View.VISIBLE);

            back.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            agreement.setVisibility(View.GONE);
            signUp.setVisibility(View.GONE);

            if (current.equals(nameDetails))
                back.setVisibility(View.GONE);
        }
        hideViews();
    }

    private void next() {
        back.setVisibility(View.VISIBLE);
        if (current.equals(nameDetails)) {
            if (validNameInput()) {
                resetCurrent();
            }
        } else if (current.equals(contactDetails)) {
            if (validContactInput()) {
                showProgressBar(true);
                firebaseAuth.fetchSignInMethodsForEmail(email.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                emailExists = !task.getResult().getSignInMethods().isEmpty();
                                if(emailExists)
                                    showError(email, "Email Already Exist");
                                else {
                                    resetCurrent();
                                }
                                showProgressBar(false);
                            }
                        });
            }
        } else if (current.equals(loginDetails)) {
            if (validLoginInput()) {
                showProgressBar(true);
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(regNo.getText().toString().toLowerCase().trim()))
                            showError(email, "Registration Number Already Exist");
                        else {
                            resetCurrent();
                            hideViews();
                        }
                        showProgressBar(false);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        toast.makeShort("CANCELLED");
                    }
                });
            }
        } else {
            resetCurrent();
        }
    }

    private void resetCurrent() {
        current.setVisibility(View.GONE);
        current = sibling.next(current);
        current.setVisibility(View.VISIBLE);
    }

    private void hideViews() {
        if (current.equals(signUpImage)) {
            next.setVisibility(View.GONE);
            agreement.setVisibility(View.VISIBLE);
            signUp.setVisibility(View.VISIBLE);
        } else {
            next.setVisibility(View.VISIBLE);
            agreement.setVisibility(View.GONE);
            signUp.setVisibility(View.GONE);
        }
    }

    private boolean validNameInput() {
        if (firstName.getText().toString().trim().isEmpty()) {
            showError(firstName, "Invalid First Name");
            return false;
        } else if (lastName.getText().toString().trim().isEmpty()) {
            showError(lastName, "Invalid Last Name");
            return false;
        } else
            return true;
    }

    private boolean validContactInput() {
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            showError(email, "Invalid Email");
            return false;
        } else if (!Patterns.PHONE.matcher(mobile.getText().toString().trim()).matches()) {
            showError(mobile, "Invalid Mobile Number");
            return false;
        } else
            return true;
    }

    private boolean validLoginInput() {
        if (regNo.getText().toString().trim().isEmpty()) {
            showError(regNo, "Invalid Registration Number");
            return false;
        } else if (password.getText().toString().trim().isEmpty()) {
            showError(password, "Enter a Password");
            return false;
        } else if (password.getText().toString().trim().length() < 6) {
            showError(password, "Min password length should be 6 characters!");
            return false;
        } else if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
            showError(confirmPassword, "Password Mis-matched");
            return false;
        } else
            return true;
    }

    private void showError(EditText editText, String message) {
        editText.setError(message);
        editText.requestFocus();
    }

    private void showProgressBar(Boolean bool) {
        if (bool)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    private void signUp() {
        if (validNameInput() && validContactInput() && validLoginInput()) {
            insertUserIntoFirebaseDatabase();
        }
    }

    private void insertUserIntoFirebaseDatabase(){
        showProgressBar(true);

        String fName = firstName.getText().toString().trim();
        String mName = middleName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String mobile = this.mobile.getText().toString().trim();
        String brd = brand.getText().toString().trim();
        String dID = deviceID.getText().toString().trim();
        String rn = regNo.getText().toString().toLowerCase().trim();
        String pass = confirmPassword.getText().toString().trim();

        currentUser = new CurrentUser(fName, mName, lName, mail, mobile, brd, dID, rn, pass);

        firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    currentUser.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    databaseReference.child("users").child(rn).setValue(currentUser)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                toast.makeLong("Account Created Successfully");
                                showProgressBar(false);
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                finish();
                            }
                            else {
                                toast.makeLong("Unable To Create Account");
                                showProgressBar(false);
                            }
                        }
                    });
                }else {
                    toast.makeLong("Unable To Create Account");
                    showProgressBar(false);
                }
            }
        });
    }


}