package com.example.studentscheduler;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.studentscheduler.todolist.AddNewTask;
import com.example.studentscheduler.todolist.OnDialogCloseListener;
import com.example.studentscheduler.todolist.RecyclerViewTouchHelper;
import com.example.studentscheduler.todolist.ToDoAdapter;
import com.example.studentscheduler.todolist.ToDoDataBaseHelper;
import com.example.studentscheduler.todolist.ToDoWork;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class To_do_list extends AppCompatActivity implements OnDialogCloseListener {

    BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private ToDoDataBaseHelper myDB;
    private List<ToDoWork> aList;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingButton);
        myDB = new ToDoDataBaseHelper(To_do_list.this);
        aList = new ArrayList<>();
        adapter = new ToDoAdapter(myDB, To_do_list.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        aList = myDB.getAllTasks();
        Collections.reverse(aList);
        adapter.setTasks(aList);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);


//        @Override
//        public void OnDialogClose(DialogInterface dialogInterface){
//            aList = myDB.getAllTasks();
//            Collections.reverse(aList);
//            adapter.setTasks(aList);
//            adapter.notifyDataSetChanged();
//        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.todo_list);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home1:
                        startActivity(new Intent(getApplicationContext(), Calendar.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.my_schedules:
                        startActivity(new Intent(getApplicationContext(), my_schedule.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.todo_list:

                        return true;

                }

                return false;
            }
        });
    }
    @Override
    public void OnDialogClose(DialogInterface dialogInterface){
        aList = myDB.getAllTasks();
        Collections.reverse(aList);
        adapter.setTasks(aList);
        adapter.notifyDataSetChanged();
    }
}
