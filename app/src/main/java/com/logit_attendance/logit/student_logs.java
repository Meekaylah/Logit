package com.logit_attendance.logit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class student_logs extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<LogsModelClass> logsList;
    LogsAdapter logsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_logs);
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        logsAdapter = new LogsAdapter(logsList, new LogsAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(LogsModelClass logsList) {
                Intent intent = new Intent(student_logs.this, log_details.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                Toast.makeText(getBaseContext(),"Item Clicked", Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(logsAdapter);
        logsAdapter.notifyDataSetChanged();
    }

    private void initData() {
        logsList = new ArrayList<>();

        logsList.add(new LogsModelClass("MMS Android Development","NOT SIGNED IN", getDrawable(R.drawable.rectangle_1)));
        logsList.add(new LogsModelClass("MMS JSF","NOT SIGNED IN", getDrawable(R.drawable.rectangle_2)));
        logsList.add(new LogsModelClass("MMS Android Development","NOT SIGNED IN", getDrawable(R.drawable.rectangle_2)));
        logsList.add(new LogsModelClass("MMS JSF","NOT SIGNED IN", getDrawable(R.drawable.rectangle_2)));
        logsList.add(new LogsModelClass("MMS Android Development","NOT SIGNED IN", getDrawable(R.drawable.rectangle_2)));
        logsList.add(new LogsModelClass("MMS JSF","NOT SIGNED IN", getDrawable(R.drawable.rectangle_2)));
        logsList.add(new LogsModelClass("MMS Android Development","NOT SIGNED IN", getDrawable(R.drawable.rectangle_2)));
        logsList.add(new LogsModelClass("MMS Microsoft Office","NOT SIGNED IN", getDrawable(R.drawable.rectangle_2)));
        logsList.add(new LogsModelClass("MMS Microsoft Office","NOT SIGNED IN", getDrawable(R.drawable.rectangle_2)));
        logsList.add(new LogsModelClass("MMS Microsoft Office","NOT SIGNED IN", getDrawable(R.drawable.rectangle_2)));
        logsList.add(new LogsModelClass("MMS Microsoft Office","NOT SIGNED IN", getDrawable(R.drawable.rectangle_2)));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}