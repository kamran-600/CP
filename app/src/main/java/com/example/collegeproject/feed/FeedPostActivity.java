package com.example.collegeproject.feed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityFeedPostBinding;
import com.example.collegeproject.databinding.ActivityProfileBinding;
import com.example.collegeproject.feed.models.ImageFeedModel;
import com.example.collegeproject.feed.models.Item;

import java.util.ArrayList;
import java.util.List;

public class FeedPostActivity extends AppCompatActivity {

    ActivityFeedPostBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}