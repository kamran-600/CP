package com.example.collegeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.collegeproject.databinding.ActivityTeacherTeachingClassBinding;

public class TeacherTeachingClassActivity extends AppCompatActivity {

    private ActivityTeacherTeachingClassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherTeachingClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}