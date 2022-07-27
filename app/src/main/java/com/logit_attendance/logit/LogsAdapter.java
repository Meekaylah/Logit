package com.logit_attendance.logit;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.ViewHolder> {

    private List<LogsModelClass> logsList;
    private final OnItemClickListener listener;

    public LogsAdapter(List<LogsModelClass>logsList, OnItemClickListener listener) {
        this.logsList = logsList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.logs_item_design, parent,false);
        return new ViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(LogsModelClass logsList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String class_name = logsList.get(position).getClass_name();
        String date = logsList.get(position).getDate();
        Drawable background = logsList.get(position).getBackground();
        holder.setData(class_name, date, background);
        holder.bind(logsList.get(position), listener);
    }


    @Override
    public int getItemCount() {
        return logsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView class_name;
        private TextView date;
        private ConstraintLayout background;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            class_name = itemView.findViewById(R.id.class_name);
            date = itemView.findViewById(R.id.date);
            background = itemView.findViewById(R.id.rectangle_1);
        }


        public void setData(String name, String dates, Drawable drawable) {
            class_name.setText(name);
            date.setText(dates);
            background.setBackground(drawable);
        }

        public void bind(final LogsModelClass logsList, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(logsList);
                }
            });
        }
    }
}
