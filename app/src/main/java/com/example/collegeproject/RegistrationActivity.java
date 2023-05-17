package com.example.collegeproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeproject.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finishAffinity();
            }
        });

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemSelectId = binding.rgroup.getCheckedRadioButtonId();
                if (itemSelectId == R.id.student) {

                    startActivity(new Intent(getApplicationContext(), StudentRegistrationActivity.class));
                } else if (itemSelectId == R.id.teacher) {

                    startActivity(new Intent(getApplicationContext(), TeacherRegistrationActivity.class));
                } else
                    Toast.makeText(RegistrationActivity.this, "Please select any one", Toast.LENGTH_SHORT).show();
            }
        });

    }


}