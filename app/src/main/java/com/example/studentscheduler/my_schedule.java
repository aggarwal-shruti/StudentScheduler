package com.example.studentscheduler;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import com.example.studentscheduler.Adapters.MyScheduleViewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class my_schedule extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView myscheduleView;


    List<SchedulingData> list;
    List<Date> dateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);

        dateList = new ArrayList<>();

        myscheduleView = findViewById(R.id.my_schedules_view);
        myscheduleView.setHasFixedSize(true);
        myscheduleView.setLayoutManager(new LinearLayoutManager(this));



        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Scheduling Data","");
        Type type = new TypeToken<List<SchedulingData>>(){}.getType();
        list = gson.fromJson(json,type);
        if(list!=null) {
            if(!list.isEmpty())
            {
            for (SchedulingData d : list) {
                Log.d("MYSCHEDULE LIST", "onCreate:Schedule Date " + d.getDate());
                dateList.add(d.getDate());

                for (Data d1 : d.getData()) {
                    Log.d("MY SCHEDULE LIST DATA", "onCreate: SCHEDULE DATA " + d1.getTime() + " " + d1.getData());
                }
            }

            }
        }


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.my_schedules);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home1:
                        startActivity(new Intent(getApplicationContext(),Calendar.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.my_schedules:

                        return true;

                    case R.id.todo_list:
                        startActivity (new Intent(getApplicationContext(),To_do_list.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                }

                return false;
            }
        });


        if(!dateList.isEmpty())
        {
            MyScheduleViewAdapter adapter = new MyScheduleViewAdapter(this,dateList);
            myscheduleView.setAdapter(adapter);
        }



    }
}