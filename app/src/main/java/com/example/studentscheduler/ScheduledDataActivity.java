package com.example.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.studentscheduler.Adapters.ScheduledDataAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class ScheduledDataActivity extends AppCompatActivity {

    List<SchedulingData> list;
    List<Data> dataList;
    RecyclerView scheduledDataView;
    ScheduledDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_data);


        Date date = (Date) getIntent().getSerializableExtra("Date");


        scheduledDataView = findViewById(R.id.scheduledDataView);
        scheduledDataView.setHasFixedSize(true);
        scheduledDataView.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Scheduling Data","");
        Type type = new TypeToken<List<SchedulingData>>(){}.getType();
        list = gson.fromJson(json,type);
        if(list!=null)
        {
            if(!list.isEmpty())
            {
                for(SchedulingData d:list)
                {
                    if(d.getDate().equals(date))
                    {
                        dataList = d.getData();
                    }
                }


                adapter = new ScheduledDataAdapter(this,dataList);
                scheduledDataView.setAdapter(adapter);



            }
        }
    }
}