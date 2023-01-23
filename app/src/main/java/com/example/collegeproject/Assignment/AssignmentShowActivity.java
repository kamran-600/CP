package com.example.collegeproject.Assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.collegeproject.databinding.ActivityAssignmentShowBinding;

public class AssignmentShowActivity extends AppCompatActivity {

    ActivityAssignmentShowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignmentShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}