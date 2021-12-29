package com.example.studentscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 4000;


    //variables for animation
    Animation topAnim, bottomAnim;
    ImageView Image;
    TextView logoText, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //To remove status bar from above. To get fullscreen.
        setContentView(R.layout.activity_main);

        //Animations working
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Adding image and text of splash screen
        Image = findViewById(R.id.logo);
        logoText = findViewById(R.id.text1);
        slogan = findViewById(R.id.text3);

        //setting animations to image and texts
        Image.setAnimation(topAnim);
        logoText.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        //Making screen as splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_SCREEN);
    }
}