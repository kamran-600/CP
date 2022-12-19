package com.example.collegeproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.collegeproject.databinding.ActivityUserPasswordBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class UserPasswordActivity extends AppCompatActivity {

    private ActivityUserPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.continueBtnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPasswordActivity.this,HomeActivity.class));


            }
        });

    }
}