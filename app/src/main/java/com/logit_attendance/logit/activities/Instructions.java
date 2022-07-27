package com.logit_attendance.logit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.logit_attendance.logit.R;
import com.logit_attendance.logit.utilities.MyToast;
import com.logit_attendance.logit.utilities.Sibling;

public class Instructions extends AppCompatActivity {

    private final MyToast toast = new MyToast(this);

    private ImageView leftArrow = null;
    private Button skip = null;
    private Button back = null;
    private Button next = null;
    private Button getStarted = null;

    // LAYOUTS
    private Sibling<ConstraintLayout> layoutSibling = null;
    private ConstraintLayout currentLayout = null;
    private ConstraintLayout welcomeToLogit = null;
    private ConstraintLayout createAnAccount = null;
    private ConstraintLayout uniqueQRCode = null;
    private ConstraintLayout logYourArrival = null;
    private ConstraintLayout checkYourLogbook = null;

    // DOTS
    private Sibling<Button> dotSibling = null;
    private Button currentDot = null;
    private Button welcomeDot = null;
    private Button createDot = null;
    private Button uniqueDot = null;
    private Button arrivalDot = null;
    private Button logbookDot = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        leftArrow = findViewById(R.id.leftArrow);
        skip = findViewById(R.id.skip);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        getStarted = findViewById(R.id.getStarted);

        welcomeToLogit = findViewById(R.id.welcomeToLogit);
        createAnAccount = findViewById(R.id.createAnAccount);
        uniqueQRCode = findViewById(R.id.uniqueQRCode);
        logYourArrival = findViewById(R.id.logYourArrival);
        checkYourLogbook = findViewById(R.id.checkYourLogbook);
        layoutSibling = new Sibling<>(welcomeToLogit, createAnAccount, uniqueQRCode, logYourArrival, checkYourLogbook).loopSibling(false);
        currentLayout = welcomeToLogit;

        welcomeDot = findViewById(R.id.welcomeDot);
        createDot = findViewById(R.id.createDot);
        uniqueDot = findViewById(R.id.uniqueDot);
        arrivalDot = findViewById(R.id.arrivalDot);
        logbookDot = findViewById(R.id.logbookDot);
        dotSibling = new Sibling<>(welcomeDot, createDot, uniqueDot, arrivalDot, logbookDot).loopSibling(false);
        currentDot = welcomeDot;

        leftArrow.setOnClickListener(onClickListener);
        skip.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener);
        next.setOnClickListener(onClickListener);
        getStarted.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.leftArrow:
                    leftArrow();
                    break;
                case R.id.skip:
                case R.id.getStarted:
                    startActivity(new Intent(Instructions.this, GetStarted.class));
                    finish();
                    break;
                case R.id.back:
                    back();
                    break;
                case R.id.next:
                    next();
                    break;
            }
        }
    };

    private void leftArrow() {
        back();
    }

    private void back() {
        if (currentLayout.equals(welcomeToLogit)){
            finish();
        }else{
            currentLayout.setVisibility(View.GONE);
            currentLayout = layoutSibling.previous(currentLayout);
            currentLayout.setVisibility(View.VISIBLE);
            setCurrentDot(currentLayout);

            skip.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            getStarted.setVisibility(View.GONE);

            if (currentLayout.equals(welcomeToLogit))
                back.setVisibility(View.GONE);
        }
    }

    private void next() {
        currentLayout.setVisibility(View.GONE);
        currentLayout = layoutSibling.next(currentLayout);
        currentLayout.setVisibility(View.VISIBLE);
        setCurrentDot(currentLayout);
        back.setVisibility(View.VISIBLE);
        if (currentLayout.equals(checkYourLogbook)) {
            skip.setVisibility(View.GONE);
            back.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            getStarted.setVisibility(View.VISIBLE);
        }
    }

    private void setCurrentDot(ConstraintLayout layout){
        if(layout.equals(welcomeToLogit))
            currentDot = welcomeDot;
        else if(layout.equals(createAnAccount))
            currentDot = createDot;
        else if(layout.equals(uniqueQRCode))
            currentDot = uniqueDot;
        else if(layout.equals(logYourArrival))
            currentDot = arrivalDot;
        else
            currentDot = logbookDot;
        selectDot(currentDot);
    }

    private void selectDot(Button dot){
        welcomeDot.setBackgroundResource(R.drawable.unselected_dot);
        createDot.setBackgroundResource(R.drawable.unselected_dot);
        uniqueDot.setBackgroundResource(R.drawable.unselected_dot);
        arrivalDot.setBackgroundResource(R.drawable.unselected_dot);
        logbookDot.setBackgroundResource(R.drawable.unselected_dot);
        dot.setBackgroundResource(R.drawable.selected_dot);
    }
}