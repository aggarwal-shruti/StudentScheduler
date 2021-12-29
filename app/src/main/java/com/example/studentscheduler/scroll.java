package com.example.studentscheduler;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.studentscheduler.Adapters.CustomAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class scroll extends AppCompatActivity {

    RecyclerView recyclerView;
    String[] arr = {"0:00","1:00","2:00", "3:00", "4:00", "5:00", "6:00", "7:00" , "8:00" , "9:00" , "10:00" , "11:00" , "12:00" , "13:00" , "14:00" , "15:00" , "16:00" , "17:00" , "18:00" , "19:00" ,  "20:00" , "21:00" , "22:00" , "23:00" };
    String[] arr2= {"sleep","sleep","sleep", "sleep", "sleep", "sleep", "Wakeup, Freshen up and workout", "Meditate for 10 minutes and take a cold shower" , "Have Breakfast and schedule your day" , "Complete your any overdue Assignment" , "Academic Work" , "Take 30 minutes break and stretch out" , "Academic Work" , "Take break and have lunch" , "Personal Project work" , "Personal Project Work" , "Take some Rest and Do something related to your hobby" , "Meditate for 10 min and do some yoga" , "Talk to your family/friends" , "Assignment while listening to happy music " ,  "Have dinner and walk for 30 minutes" , "Analyze your day and introspect and prepare for sleep" , "Room lights dim/off and sleep" , "Sleep " };

    Button save;

    List<SchedulingData> schedulingDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        Date date = (Date) getIntent().getSerializableExtra("Date");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter c = new CustomAdapter(arr);
        recyclerView.setAdapter(c);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                List<Data> list = c.getList();
                if(!list.isEmpty()) {
                    for (int i=0;i<arr.length;i++)
                    {
                        int count=0;
                        for(Data d:list)
                        {
                            if(arr[i].equals(d.getTime()))
                                count++;
                        }
                        if(count==0)
                        {
                            Data d = new Data(arr[i],arr2[i]);
                            list.add(d);
                            list.sort((o1, o2) -> {
                                try {
                                    return new SimpleDateFormat("HH:mm").parse(o1.getTime()).compareTo(new SimpleDateFormat("HH:mm").parse(o2.getTime()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    return 0;
                                }
                            });
                        }
                    }
                    setupPreferenceData(list,date);
                }
                else
                {
                    Toast.makeText(scroll.this, "Nothing to Save here", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void setupPreferenceData(List<Data> list,Date date)
    {
        SchedulingData data = new SchedulingData();
        data.setDate(date);
        data.setData(list);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Scheduling Data","");
        Type type = new TypeToken<List<SchedulingData>>(){}.getType();
        schedulingDataList = gson.fromJson(json,type);
        if(schedulingDataList!=null) {
            schedulingDataList.add(data);
        }
        else{
            schedulingDataList = new ArrayList<>();
            schedulingDataList.add(data);
        }
        String jsonString = gson.toJson(schedulingDataList);
        editor.putString("Scheduling Data",jsonString);
        editor.commit();

        Toast.makeText(scroll.this, "Data is Saved", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(scroll.this,my_schedule.class);
        startActivity(intent);


    }


}