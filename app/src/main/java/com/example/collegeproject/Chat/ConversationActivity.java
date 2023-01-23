package com.example.collegeproject.Chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityConversationBinding;

public class ConversationActivity extends AppCompatActivity {

    private ActivityConversationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConversationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}