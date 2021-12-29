package com.example.studentscheduler;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class Calendar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    CalendarView myCalendar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        myCalendar = findViewById(R.id.calendarView);
        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

                Date date = new Date(year-1900,month,dayOfMonth);
                Log.d("Date:-", "onSelectedDayChange: " +date+"YEar "+year);
                Intent intent = new Intent(Calendar.this, scroll.class);
                intent.putExtra("Date",date);
                startActivity(intent);
            }
        });

        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();




        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home1);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home1:
                        startActivity (new Intent(getApplicationContext(),Calendar.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.my_schedules:
                        startActivity(new Intent(getApplicationContext(),my_schedule.class));

                        overridePendingTransition(0,0);
                        finish();
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

    }


    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home1:
                startActivity (new Intent(getApplicationContext(),Calendar.class));
                overridePendingTransition(0,0);
                drawerLayout.closeDrawer(GravityCompat.START);
                finish();
                return true;

            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame_layout, new ProfileFragmentnew()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                myCalendar.setVisibility(View.GONE);
                return true;

            case R.id.Account:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame_layout, new AccountFragmentnew()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                myCalendar.setVisibility(View.GONE);
                return true;

            case R.id.logout:
                startActivity (new Intent(getApplicationContext(),Login.class));
                overridePendingTransition(0,0);
                drawerLayout.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}