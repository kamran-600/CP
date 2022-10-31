package com.example.collegeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextView allReadyRegister = findViewById(R.id.login);
        RadioGroup rg = findViewById(R.id.rgroup);
        ImageView next = findViewById(R.id.next);

        allReadyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemSelectId = rg.getCheckedRadioButtonId();
                if (itemSelectId == R.id.student){
                    startActivity(new Intent(getApplicationContext(), StudentRegistrationActivity.class));
                }
                else if(itemSelectId == R.id.teacher) {
                    startActivity(new Intent(getApplicationContext(), TeacherRegistrationActivity.class));
                }
                else
                    Toast.makeText(RegistrationActivity.this, "Please select any one", Toast.LENGTH_SHORT).show();
            }
        });

    }
}