package com.example.collegeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.collegeproject.databinding.ActivityUserPasswordBinding;

public class UserPasswordActivity extends AppCompatActivity {

    private ActivityUserPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}