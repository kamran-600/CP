package com.example.collegeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class TeacherRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // For department
        Spinner department = (Spinner) findViewById(R.id.teacherrole);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> tRAdapter = ArrayAdapter.createFromResource(this,
                R.array.Teacher_Role_Array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        tRAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        department.setAdapter(tRAdapter);
    }
}