package com.example.studentscheduler.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentscheduler.R;
import com.example.studentscheduler.ScheduledDataActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyScheduleViewAdapter extends RecyclerView.Adapter<MyScheduleViewAdapter.ViewHolder> {

    Context context;
    List<Date> dateList = new ArrayList<>();

    public MyScheduleViewAdapter(Context context, List<Date> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public MyScheduleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.my_schedule_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyScheduleViewAdapter.ViewHolder holder, int position) {
            Date date = dateList.get(position);
            String day          = (String) DateFormat.format("dd",   date);
            String monthString  = (String) DateFormat.format("MMM",  date);
            String year         = (String) DateFormat.format("yyyy", date);
            holder.textView.setText(day+" "+monthString+" "+year);
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.my_schedules_date);
            imageView = itemView.findViewById(R.id.my_schedules_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
                Intent intent  = new Intent(context, ScheduledDataActivity.class);
                intent.putExtra("Date",dateList.get(getAdapterPosition()));
                context.startActivity(intent);
        }
    }
}
