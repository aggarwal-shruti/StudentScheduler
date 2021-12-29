package com.example.studentscheduler.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentscheduler.Data;
import com.example.studentscheduler.R;

import org.w3c.dom.Text;

import java.util.List;

public class ScheduledDataAdapter extends RecyclerView.Adapter<ScheduledDataAdapter.ViewHolder> {

    Context context;
    List<Data> list;

    public ScheduledDataAdapter(Context context, List<Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ScheduledDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.scheduled_data_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduledDataAdapter.ViewHolder holder, int position) {

        Data data = list.get(position);
        holder.time.setText(data.getTime());
        holder.data.setText(data.getData());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView imageView;
        TextView time;
        TextView data;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.scheduledData_image);
            time = itemView.findViewById(R.id.scheduledData_time);
            data = itemView.findViewById(R.id.scheduledData_data);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
