package com.example.studentscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email1, password;
    Button login, signUp;
    //  DataBaseHelper db;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email1 = findViewById(R.id.emaillogin);
        password = findViewById(R.id.passwordlogin);
        login = findViewById(R.id.update);
        signUp = findViewById(R.id.signup);
        // DataBaseHelper db = new DataBaseHelper(this);

        firebaseAuth = FirebaseAuth.getInstance();




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailValue1 = email1.getText().toString().trim();
                String passwordValue1 = password.getText().toString().trim();

                if (TextUtils.isEmpty(emailValue1)) {
                    email1.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(passwordValue1)) {
                    password.setError("Password is required");
                    return;
                }

                if (passwordValue1.length() < 6) {
                    password.setError("Password must be >= 6 characters");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(emailValue1, passwordValue1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Calendar.class));
                            finish();
                        }
                        else {
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
                finish();

            }
        });
    }
}